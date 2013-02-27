package com.usagainsthumanity.taah_server;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.Game;
import com.usagainsthumanity.taah_model.GameUsers;

public class GameDataJSONResource extends ServerResource {
	
	@Get("json")
	public String getStats(){
		//TODO Actual lookup
		try{
			String gameID = (String)this.getRequestAttributes().get("gameID");
			EntityManager em = EMFService.get().createEntityManager();
			// Get all the gameUser objects with the gameID
			Query q = em.createQuery("select g from GameUsers g where g.gameID = :gameID");
			q.setParameter("gameID", gameID);
			JSONObject json = new JSONObject();
			List<GameUsers> gameUsers = q.getResultList();
			List<Long> Scores = new LinkedList<Long>();
			List<Long> IDS = new LinkedList<Long>();
			for(GameUsers resultList : gameUsers) {
				// Loops through all gameUsers and gets their userIDs and scores
				IDS.add(resultList.getUserID());
				Scores.add(resultList.getScore());
			}
			// Not sure what this is supposed to be
			List<Long> HasPlayed = new LinkedList<Long>();
			//HasPlayed.add(1);
			json.put("Scores", Scores);
			json.put("IDs", IDS);
			q = em.createQuery("select g from Game g where g.gameID = :gameID");
			q.setParameter("gameID", gameID);
			List<Game> game = new LinkedList<Game>();
			game = q.getResultList();
			json.put("CardsToWin", game.get(0).getPointsToWin());
			json.put("PlayerTurn", game.get(0).getUserTurn());
			json.put("HasPlayed", HasPlayed);
			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Delete
	public void deleteGame(){
		Integer gameID = Integer.parseInt((String)this.getRequestAttributes().get("gameId"));
		if(gameID == 1337){
			//Example of game already started 
			throw new ResourceException(409);
		}else if(gameID == 80085){
			//Example of user is not the host
			throw new ResourceException(401);
		}
	}
}
