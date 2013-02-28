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
			List<Boolean> HasPlayed = new LinkedList<Boolean>();
			for(GameUsers resultList : gameUsers) {
				// Loops through all gameUsers and gets their userIDs, scores, and whether they have played
				IDS.add(resultList.getUserID());
				Scores.add(resultList.getScore());
				HasPlayed.add(resultList.getHasPlayed());
			}
			json.put("Scores", Scores);
			json.put("IDs", IDS);
			json.put("HasPlayed", HasPlayed);
			q = em.createQuery("select g from Game g where g.gameID = :gameID");
			q.setParameter("gameID", gameID);
			List<Game> game = new LinkedList<Game>();
			game = q.getResultList();
			json.put("CardsToWin", game.get(0).getPointsToWin());
			json.put("PlayerTurn", game.get(0).getUserTurn());
			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Delete
	public void deleteGame() {
		Long gameID = Long.parseLong((String)this.getRequestAttributes().get("gameID"));
		Long userID = Long.parseLong((String)this.getRequestAttributes().get("userID"));
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select g from Game g where g.gameID = :gameID");
		q.setParameter("gameID", gameID);
		List<Game> game = new LinkedList<Game>();
		game = q.getResultList(); // Should only be one of these
		if(game.get(0).getHostID() == userID) {
			if(game.get(0).getState() == 0) { // Delete game and gameusers
				q = em.createQuery("select g from GameUsers g where g.gameID = :gameID");
				q.setParameter("gameID",gameID);
				List<GameUsers> gameUsers = new LinkedList<GameUsers>();
				for(GameUsers resultList : gameUsers) { // Remove all users from the game
					em.remove(resultList);
				}
				em.remove(game.get(0)); // Remove the game
			}
			else {
				//Example of game already started
				throw new ResourceException(409);
			}
		}else {
			//Example of user is not the host
			throw new ResourceException(401);
		}
	}
}
