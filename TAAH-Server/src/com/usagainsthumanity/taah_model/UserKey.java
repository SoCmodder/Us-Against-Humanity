package com.usagainsthumanity.taah_model;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.codec.binary.Base64;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;





@Entity
public class UserKey {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userID;
	private String Token;
	private String APIKey;
	
	public UserKey(String token) {
		Token = token;
		Random r = new Random(System.currentTimeMillis());
		byte[] data = new byte[27];
		for(int i = 0; i < 27; i++){
			Integer b = r.nextInt();
			data[i] = (byte) (b.byteValue() >> 8);
			i++;
			data[i] = (byte) (b.byteValue() >> 2);
			i++;
			data[i] = (byte) (b.byteValue() >> 4);
		}		
		APIKey = Base64.encodeBase64URLSafeString(data);
		
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
