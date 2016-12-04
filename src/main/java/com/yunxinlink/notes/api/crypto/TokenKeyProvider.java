package com.yunxinlink.notes.api.crypto;

import java.security.Key;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * token key的生成器
 * @author tiger
 * @date 2016年12月3日 下午4:46:49
 */
public class TokenKeyProvider extends MacProvider {

	protected TokenKeyProvider(SignatureAlgorithm alg, Key key) {
		super(alg, key);
	}
	
	/**
	 * 生成key，采用的是{@link SignatureAlgorithm#HS512}
	 * @param apiKey 指定的密钥，相当于盐 
	 * @return
	 */
	public static SecretKey generateKey(String apiKey) {

		//SignatureAlgorithm.HS512
        SignatureAlgorithm alg = SignatureAlgorithm.HS512;
        return generateKey(alg, apiKey);
    }
	
	/**
	 * 生成key
	 * @param apiKey 指定的密钥，相当于盐 
	 * @return
	 */
	public static SecretKey generateKey(SignatureAlgorithm alg, String apiKey) {
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey);
		return new SecretKeySpec(apiKeySecretBytes, alg.getJcaName());
	}

}
