package com.usagainsthumanity.taah_model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.codec.binary.Base64;





@Entity
public class UserKey {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userID;
	private byte[] salt;
	private String Token;
	private String APIKey;

	public UserKey(String token){
		Token = token;
		SecureRandom sr = new SecureRandom();
		byte[] tokenbytes;
		try {
			tokenbytes = token.getBytes("UTF-8");

			salt = new byte[tokenbytes.length];
			sr.nextBytes(salt);
			byte[] hashbytes = new byte[tokenbytes.length + salt.length];

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(hashbytes);
			byte[] digest = md.digest();
			APIKey = Base64.encodeBase64URLSafeString(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	public UserKey() {
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public Long getUserID() {
		return userID;
	}

	public String getAPIKey() {
		return APIKey;
	}


}
