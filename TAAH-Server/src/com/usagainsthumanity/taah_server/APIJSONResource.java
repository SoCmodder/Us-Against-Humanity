package com.usagainsthumanity.taah_server;

import java.io.IOException;
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

	private void createUserInfo(UserKey userKey) {
		// TODO Auto-generated method stub
		
	}
}
