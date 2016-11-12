package com.yunxinlink.notes.api.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.yunxinlink.notes.api.service.IEmailService;
import com.yunxinlink.notes.api.util.Constant;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 邮件发送的具体服务层
 * @author tiger
 *
 */
public class EmailService implements IEmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 发件人的邮箱
	 */
	private String fromAddress;
	
	/**
	 * 邮件服务器的地址
	 */
	private String host;
	
	/**
	 * 邮件的协议，默认"smtp"
	 */
	private String protocol = "smtp";
	
	/**
	 * 邮件服务器默认的端口
	 */
	private int port = 25;
	
	/**
	 * 附件默认的编码
	 */
	private String defaultEncoding = "UTF-8";
	
	/**
	 * 是否打开调试信息
	 */
	private boolean debug = false;
	
	/**
	 * 是否需要校验
	 */
	private boolean auth = true;
	
	/**
	 * 发件人的邮箱名称，可以指定
	 */
	private String nickname;
	
	/**
	 * 邮件模板的文件名称
	 */
	private String templeteName;
	
	@Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTempleteName() {
		return templeteName;
	}

	public void setTempleteName(String templeteName) {
		this.templeteName = templeteName;
	}

	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	/*@Override
	public boolean sendEmail(String toAddress) {
		String htmlText = null;
		boolean success = false;
		try {
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			messageHelper.setTo(toAddress);
			messageHelper.setSubject(Constant.RESET_PWD_SUBJECT);	//设置邮件主题
			Map<String, Object> map = new HashMap<>();
			map.put("email", toAddress);
			htmlText = getMailText(map);
			
			messageHelper.setText(htmlText, true);
			
			//logoId，添加logo
			ClassPathResource image = new ClassPathResource("images/ic_logo_nav.gif");
			boolean exists = image.exists();
			if (exists) {
				messageHelper.addInline("logoId", image);
			}
			mailSender.send(mimeMessage);    //发送HTML邮件
			success = true;
		} catch (MessagingException | IOException | TemplateException e) {
			logger.error("send reset passowrd email error:" + e.getMessage());
		}
		return success;
	}*/
	
	/** 
     * 生成html模板字符串 
     * @param map 存储动态数据的map 
     * @return 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 * @throws TemplateException 
     */  
	private String getMailText(Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException  {
		String htmlText = "";
		// 通过指定模板名获取FreeMarker模板实例
		Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templeteName);
		htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		return htmlText;
	}

	@Override
	public boolean sendEmail(String toAddress) {
		Transport transport = null;
		try {
			Properties properties = getDefaultProperties();
			
			Session session = Session.getDefaultInstance(properties);
			
			MimeMessage message = new MimeMessage(session);
			
			message.setSubject(Constant.RESET_PWD_SUBJECT);  
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("email", toAddress);
			
			// 设置邮件内容  
			String content = getMailText(map);
			
			if (content == null) {
				logger.info("send mail but content is null:" + toAddress);
				return false;
			}
			
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(content, "text/html;charset=UTF-8");
			
			// 下面这个是设置发送人的Nick name
			//msg.setFrom(new InternetAddress("huanghui6579@163.com"));
			String encodeAddress = fromAddress;
			if (StringUtils.isNotBlank(nickname)) {
				encodeAddress = MimeUtility.encodeWord(nickname)+" <"+fromAddress+">";
			}
			InternetAddress from = new InternetAddress(encodeAddress);
			message.setFrom(from);
			
			// 收件人
			InternetAddress to = new InternetAddress(toAddress);
			message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC
			
			// 保存邮件
			message.saveChanges();
			
			transport = connectTransport(session);
			if (transport == null) {
				return false;
			}
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} catch (MessagingException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (transport != null) {
				// 关闭连接  
				try {
					transport.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取基本的配置信息
	 * @return
	 */
	private Properties getDefaultProperties() {
		
		Properties props = new Properties();  
        // 开启debug调试  
        props.setProperty("mail.debug", String.valueOf(debug));  
        // 发送服务器需要身份验证  
        props.setProperty("mail.smtp.auth", String.valueOf(auth));  
        // 设置邮件服务器主机名  
        props.setProperty("mail.smtp.host", host);  
        // 发送邮件协议名称  
        props.setProperty("mail.transport.protocol", protocol);  
        props.setProperty("mail.sender.username", fromAddress);  
        props.setProperty("mail.sender.password", password); 
        return props;
	}
	
	/**
	 * 连接邮件服务器
	 * @return
	 * @throws MessagingException 
	 */
	private Transport connectTransport(Session session) throws MessagingException {
		Transport transport = null;
		transport = session.getTransport(protocol);
		if (transport == null) {
			return null;
		}
		String username = getUsername();
		String password = getPassword();
		if ("".equals(username)) {  // probably from a placeholder
			username = null;
			if ("".equals(password)) {  // in conjunction with "" username, this means no password to use
				password = null;
			}
		}
		transport.connect(getHost(), getPort(), username, password);
		return transport;
	}

}
