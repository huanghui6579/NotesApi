package com.yunxinlink.notes.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

public class TestUUID {
	private static final Logger logger = LoggerFactory.getLogger(TestUUID.class);

	@Test
	public void test() {
		IdGenerator idGenerator = new SimpleIdGenerator();
		String uuid = idGenerator.generateId().toString();
		System.out.println("uuid:" + uuid);
		
		idGenerator = new AlternativeJdkIdGenerator();
		uuid = idGenerator.generateId().toString();
		System.out.println("uuid:" + uuid);
		
		idGenerator = new AlternativeJdkIdGenerator();
		uuid = idGenerator.generateId().toString();
		System.out.println("uuid:" + uuid);
	}
	
	@Test
	public void testMd5() {
		String password = "123456";
		String encode = DigestUtils.md5Hex(password);
		logger.info("ecode:" + encode);
		String md5 = getMD5(password);
		logger.info("ecode:" + md5);
	}
	
	@Test
	public void testFilename() {
		String encodeFilename = "47447545778754.png";
		String contentDesc = "attachment;filename=" + encodeFilename + ";filename*=UTF-8''" + encodeFilename;
//		String contentDesc = "aaa2223bb";
		logger.info("contentDesc:" + contentDesc);
		String regex = "(filename=\\S+);";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(contentDesc);
		logger.info("matches:" + matcher.find());
		logger.info("matches:" + matcher.group(1));
	}

	/**
	 * 对字符串md5加密
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}

