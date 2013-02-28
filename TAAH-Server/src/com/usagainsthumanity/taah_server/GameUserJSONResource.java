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
import com.usagainsthumanity.taah_model.Game;

public class GameUserJSONResource extends ServerResource {

	@Get("json")
	public String getUsers(){
		Long gameID = Long.parseLong((String)this.getRequestAttributes().get("gameID"));
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
		Long gameID = Long.parseLong((String)this.getRequestAttributes().get("gameID"));
		Long userID = Long.parseLong((String)this.getRequestAttributes().get("userID"));
		EntityManager em = EMFService.get().createEntityManager();
		Query q1 = em.createQuery("select g from Game g where g.gameID = :gameID");
		q1.setParameter("gameID",gameID);
		List<Game> game = new LinkedList<Game>();
		game = q1.getResultList();
		Query q2 = em.createQuery("select g from GameUsers g where g.gameID = :gameID");
		q2.setParameter("gameID",gameID);
		List<GameUsers> gameUsers = new LinkedList<GameUsers>();
		gameUsers = q2.getResultList();
		try{
			/* Not sure what these are for, Add them back if you know their significance.
			System.out.println(entity.getText()); 
			JsonRepresentation tempJSON = new JsonRepresentation(entity);
			JSONObject json = tempJSON.getJsonObject(); */
			if(game.get(0).getSlots() == gameUsers.size()) {
				throw new ResourceException(402, "Game is full", "Game is full", "www.google.com");
			}else if(game.get(0).getState() != 0) {
				throw new ResourceException(401);
			}
			Boolean inGame = Boolean.FALSE;
			for(GameUsers resultList : gameUsers) {
				if(resultList.getUserID() == userID)
					inGame = Boolean.TRUE;
			}
			if(inGame) {
				throw new ResourceException(409);
			}else {
				GameUsers newUser = new GameUsers(gameID, userID, new Long(0), Boolean.FALSE);
				em.persist(newUser);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
