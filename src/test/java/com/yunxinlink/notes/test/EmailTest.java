package com.yunxinlink.notes.test;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huanghui-iri
 * @date 2016年11月11日 下午5:02:13
 */
public class EmailTest {
	private static final Logger logger = LoggerFactory.getLogger(EmailTest.class);
	
	@Test
	public void testMail() {
		logger.info("send email begin");
		
		String sendAddress = "huanghui6579@163.com";
		String password = "hui9114795719";
		String receiverAddress = "huanghui6579@sina.com";
		String mailHost = "smtp.163.com";
		String protocal = "smtp";
		
		Transport transport = null;
		
		Properties props = new Properties();  
        // 开启debug调试  
        props.setProperty("mail.debug", "true");  
        // 发送服务器需要身份验证  
        props.setProperty("mail.smtp.auth", "true");  
        // 设置邮件服务器主机名  
        props.setProperty("mail.smtp.host", mailHost);  
        // 发送邮件协议名称  
        props.setProperty("mail.transport.protocol", protocal);  
        props.setProperty("mail.sender.username", sendAddress);  
        props.setProperty("mail.sender.password", password);  
        
        try {
			// 设置环境信息  
			Session session = Session.getInstance(props);  
			
			// 创建邮件对象  
			Message msg = new MimeMessage(session);  
			msg.setSubject("JavaMail测试的快递费");  
			
			// 设置邮件内容  
			String content = "这是一封由JavaMail发送的邮件！的解放大客户方";
			
			// 邮件内容,也可以使纯文本"text/plain"
			msg.setContent(content, "text/html;charset=UTF-8");
			
			// 下面这个是设置发送人的Nick name
			//msg.setFrom(new InternetAddress("huanghui6579@163.com"));
			InternetAddress from = new InternetAddress(MimeUtility.encodeWord("云信笔记")+" <"+sendAddress+">");
			msg.setFrom(from);
			
			// 收件人
			InternetAddress to = new InternetAddress(MimeUtility.encodeWord("笔记新浪用户")+" <"+receiverAddress+">");
			msg.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC
			
			// 保存邮件
			msg.saveChanges();
			
			
			transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, sendAddress, password);
			// 发送
			transport.sendMessage(msg, msg.getAllRecipients());
			// 关闭连接  
			transport.close();
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error("send mail error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
        logger.info("send email end");
	}
	
}
