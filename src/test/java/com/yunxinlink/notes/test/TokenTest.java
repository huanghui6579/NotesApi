package com.yunxinlink.notes.test;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunxinlink.notes.api.crypto.TokenKeyProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * 
 * @author tiger
 * @date 2016年12月3日 下午3:38:01
 */
public class TokenTest {
	private Logger logger = LoggerFactory.getLogger(TokenTest.class);
	
	@Test
	public void testToken() {
		// We need a signing key, so we'll create one just for this example. Usually
		// the key would be read from your application configuration instead.
		Key key = MacProvider.generateKey();

		String compactJws = Jwts.builder()
		  .setSubject("Joe")
//		  .compressWith(CompressionCodecs.DEFLATE)
		  .signWith(SignatureAlgorithm.HS512, key)
		  .compact();
		logger.info("token:" + compactJws);
		assert Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject().equals("Joe");
	}
	
	@Test
	public void testTokenParse() {
		try {
			Key key = MacProvider.generateKey();

			String compactJws = Jwts.builder()
			  .setSubject("Joe")
//			  .compressWith(CompressionCodecs.DEFLATE)
			  .signWith(SignatureAlgorithm.HS512, key)
			  .compact();
			logger.info("token:" + compactJws);
		    Jws<Claims> claims = Jwts.parser()
		        .requireSubject("Joe")
//		        .require("hasMotorcycle", false)
		        .setSigningKey(key)
		        .parseClaimsJws(compactJws);
		    if (claims != null) {
				logger.info("claims:" + claims.getSignature() + "---" + claims.getBody() + "----" + claims.getHeader());
			}
		} catch (MissingClaimException e) {

		    // we get here if the required claim is not present

		} catch (IncorrectClaimException e1) {

		    // we get here if ther required claim has the wrong value

		}
	}
	
	@Test
	public void testJwt() {
		String key = "djsrueirukjdkjd";
		String id = "abc";
		String issuer = "yunxinnotes";
		String subject = "123465789@163.com";
		//超时时间
		long time = 3600000;
		String jwt = createJWT(key, id, issuer, subject, time);
		logger.info("jwt:" + jwt);
		
		parseJWT(key, jwt);
	}
	
	//Sample method to construct a JWT
	private String createJWT(String key, String id, String issuer, String subject, long ttlMillis) {
	 
	    //The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	 
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    //We will sign our JWT with our ApiKey secret
	    Key signingKey = TokenKeyProvider.generateKey(signatureAlgorithm, key);
	 
	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .setIssuer(issuer)
	                                .signWith(signatureAlgorithm, signingKey);
	 
	    //if it has been specified, let's add the expiration
	    if (ttlMillis >= 0) {
	    long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }
	 
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}
	
	//Sample method to validate and read the JWT
	private void parseJWT(String key, String jwt) {
	 
	    //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(key))
	       .parseClaimsJws(jwt).getBody();
	    System.out.println("ID: " + claims.getId());
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Issuer: " + claims.getIssuer());
	    System.out.println("Expiration: " + claims.getExpiration());
	}
	
	@Test
	public void testRegex() {
		String filename = "Log_934220829.log";
		String regex = "^Log_[\\w]+.log$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(filename);
		if (matcher != null) {
			if (matcher.find()) {
				String group = matcher.group();
				logger.info("group:" + group);
			} else {
				logger.info("not found");
			}
		}
	}
	
	@Test
	public void testList() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			list.add(i);
		}
		logger.info("original list:" + list);
		int fromIndex = 1;
		int toIndex = 5;
		List<Integer> subList = list.subList(fromIndex, toIndex);
		logger.info("sub list fromIndex:" + fromIndex + ", toIndex:" + toIndex + ", sub list:" + subList);
	}
}
