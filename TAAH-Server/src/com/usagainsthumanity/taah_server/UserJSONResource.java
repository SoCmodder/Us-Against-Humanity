package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.UserInfo;

public class UserJSONResource extends ServerResource{

	@Get("json")
	public String getEmail() {
		try{
			//TODO All of the things
			String email = getQuery().getValues("email");
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select u from UserInfo u where e.email = :email");
			q.setParameter("email", email);
			List<UserInfo> users = q.getResultList();
			if(users.size() == 1){
				JSONObject json = new JSONObject();
				json.put("UserID", users.get(0).getUserID()); 
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

}

