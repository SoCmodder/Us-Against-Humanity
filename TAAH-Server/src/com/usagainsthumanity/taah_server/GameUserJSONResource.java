package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.GameUsers;

public class GameUserJSONResource extends ServerResource {

	@Get("json")
	public String getUsers(){
		Integer gameID = Integer.parseInt((String)this.getRequestAttributes().get("gameID"));
		try{
			// Need to check if they game doesn't exist and return it still
			JSONObject json = new JSONObject();
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select g from GameUsers g where g.gameID = :gameID");
			q.setParameter("gameID", gameID);
			List<GameUsers> gameUsers = q.getResultList();
			List<Long> Users = new LinkedList<Long>();
			for(GameUsers gameUserList : gameUsers) {
				Users.add(gameUserList.getUserID());
			}
			json.put("Users", Users);
			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Put
	public void putUser(Representation entity) throws IOException{
		// Didn't implement this because I'm not sure how to add users to the game
		Integer gameID = Integer.parseInt((String)this.getRequestAttributes().get("gameId"));
		try{
			System.out.println(entity.getText());
			JsonRepresentation tempJSON = new JsonRepresentation(entity);
			JSONObject json = tempJSON.getJsonObject();
			if(json.getInt("UserID") == 1111){
				throw new ResourceException(402, "Game is full", "Game is full", "www.google.com");
			}else if(json.getInt("UserID") == 2222){
				throw new ResourceException(401);
			}else if(json.getInt("UserID") == 3333){
				throw new ResourceException(409);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
