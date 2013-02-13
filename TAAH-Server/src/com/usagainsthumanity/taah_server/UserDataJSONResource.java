package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.UserInfo;

public class UserDataJSONResource extends ServerResource {
	@Get("json")
	public String handleGet() {
		try{
			//TODO All of the things
			String userID = (String)this.getRequestAttributes().get("userId");
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select u from UserInfo u where e.userID = :userID");
			q.setParameter("userID", userID);
			List<UserInfo> users = q.getResultList();
			if(users.size() == 1){
				JSONObject json = new JSONObject();
				json.put("ID", users.get(0).getUserID());
				json.put("Name", users.get(0).getFirstName() + " " + users.get(0).getLastName());
				json.put("Email", users.get(0).getEmail());
				json.put("Profile Picture", users.get(0).getProfilePicture());				
				JsonRepresentation jsonRep = new JsonRepresentation(json);
				return jsonRep.getText();
			}else if(users.size() > 1){
				throw new ResourceException(406);
			}else{
				throw new ResourceException(404);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Put
	public String putAccessToken(Representation entity) throws IOException{
		return entity.getText();
	}
}
