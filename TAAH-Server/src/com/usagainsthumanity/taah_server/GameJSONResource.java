package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.Game;

public class GameJSONResource extends ServerResource {
	
	@Get("json")
	public String getGames(){
		//Figure out what we are doing
		if(getQuery().getValues("userID") != null){
			try{
				JSONObject json = new JSONObject();
				Integer userID = Integer.parseInt(getQuery().getValues("userID"));
				EntityManager em = EMFService.get().createEntityManager();
				Query q = em.createQuery("select g from Game g where g.hostID = :userID and g.Private = :Private");
				q.setParameter("userID", userID);
				q.setParameter("Private", Boolean.FALSE); // Not sure if searching for host should find private games, if not, when can they search for private games?
				List<Game> games = q.getResultList();
				List<Long> gameIDs = new LinkedList<Long>();
				List<Long> Slots = new LinkedList<Long>(); // Using a long seems like overkill
				for(Game resultList : games){
					gameIDs.add(resultList.getGameID());
					Slots.add(resultList.getSlots());
				}
				json.put("GameIDs", gameIDs); // Is it possible to return a Game rather than splitting them up? (Seems more efficient if you could do this)
				json.put("Slots", Slots);

				JsonRepresentation jsonRep = new JsonRepresentation(json);

				return jsonRep.getText();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(getQuery().getValues("gameID") != null){
			try{
				JSONObject json = new JSONObject();
				Integer gameID = Integer.parseInt(getQuery().getValues("gameID"));
				EntityManager em = EMFService.get().createEntityManager();
				Query q = em.createQuery("select g from Game g where g.gameID = :gameID and g.Private = :Private");
				q.setParameter("gameID", gameID);
				q.setParameter("Private", Boolean.FALSE);
				Game game = (Game)q.getSingleResult();
				json.put("Host", game.getHostID());
				json.put("Slots", game.getSlots());
				JsonRepresentation jsonRep = new JsonRepresentation(json);

				return jsonRep.getText();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			try{
				JSONObject json = new JSONObject();
				EntityManager em = EMFService.get().createEntityManager();
				Query q = em.createQuery("select g from Game g where g.Private = :Private");
				q.setParameter("Private", Boolean.FALSE);
				List<Game> games = q.getResultList();
				List<Long> gameIDs = new LinkedList<Long>();
				List<Long> Slots = new LinkedList<Long>();
				List<Long> PointsToWin = new LinkedList<Long>(); // Using a long seems like overkill
				for(Game resultList : games){
					gameIDs.add(resultList.getGameID());
					Slots.add(resultList.getSlots());
					PointsToWin.add(resultList.getPointsToWin());
				}
				json.put("GameIDs", gameIDs); // Is it possible to put a Game rather than having the server split them up? (seems more efficient if it could do this)
				json.put("Slots", Slots);
				json.put("PointsToWin", PointsToWin);

				JsonRepresentation jsonRep = new JsonRepresentation(json);

				return jsonRep.getText();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Post
	public String createGame(Representation entity) throws IOException, JSONException{
		
		JsonRepresentation tempJSON = new JsonRepresentation(entity);
		JSONObject json = tempJSON.getJsonObject();
		if(json.has("userId") && json.has("slots") && json.has("private") && json.has("CardsToWin")){
			Game game = new Game(json.getLong("userId"), json.getLong("slots"), json.getLong("CardsToWin"), json.getBoolean("private"));
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(game);
			em.close();			
			JSONObject returnable = new JSONObject();
			json.put("GameId", game.getGameID());
			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
		}else{
			return null;
		}
		
		
		
	}

}
