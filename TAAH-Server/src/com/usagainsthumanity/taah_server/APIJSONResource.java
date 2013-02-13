package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.usagainsthumanity.taah_model.UserInfo;
import com.usagainsthumanity.taah_model.UserKey;

public class APIJSONResource extends ServerResource {

	@Post
	public String createUser(Representation entity) throws IOException, JSONException{
		JsonRepresentation tempJSON = new JsonRepresentation(entity);
		JSONObject json = tempJSON.getJsonObject();
		if(json.has("Access Token")){
			String token = json.getString("Access Token");
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select u from UserKey u where u.Token = :token");
			q.setParameter("token", token);
			List<UserKey> userKeys = q.getResultList();
			if(userKeys.size() == 0){
				UserKey userKey = new UserKey(token);
				em.persist(userKey);
				q.setParameter("token", token);
				em.close();
				Long userID = userKey.getUserID(); 
				JSONObject returnable = new JSONObject();
				returnable.put("userid", userID);
				returnable.put("API Key",userKey.getAPIKey());
				JsonRepresentation jsonRep = new JsonRepresentation(returnable);
				createUserInfo(userKey);
				return jsonRep.getText();
			}else{
				throw new ResourceException(401);
			}
		}
		
		return null;
	}

	private UserInfo createUserInfo(UserKey userKey) {
		URL url;
		try {
			url = new URL("https://www.googleapis.com/oauth2/v2/userinfo");
			HTTPRequest req = new HTTPRequest( url, HTTPMethod.GET);
			HTTPHeader head = new HTTPHeader("Authorization", "OAuth " + userKey.getToken());
			req.setHeader(head);
			 URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
			HTTPResponse hr = fetcher.fetch(req);
			if(hr.getResponseCode() == 200){
				String jsonString = new String(hr.getContent());
				System.out.println(jsonString);
				JSONObject json = new JSONObject(jsonString);
				return new UserInfo(userKey.getUserID(), json.getString("given_name"), json.getString("family_name"), json.getString("picture"), json.getString("email"));				
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}