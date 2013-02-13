package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

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
		if(getQuery().getValues("userId") != null){
			try{
				JSONObject json = new JSONObject();
				List<Integer> gameIDs = new LinkedList<Integer>();
				gameIDs.add(1);
				gameIDs.add(2);
				gameIDs.add(3);
				List<Integer> Slots = new LinkedList<Integer>();
				Slots.add(2);
				Slots.add(5);
				Slots.add(9);
				json.put("GameIDs", gameIDs);
				json.put("Slots", Slots); 

				JsonRepresentation jsonRep = new JsonRepresentation(json);

				return jsonRep.getText();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(getQuery().getValues("gameId") != null){
			try{
				JSONObject json = new JSONObject();
				json.put("Host", 69691337);
				json.put("Slots", 1234);
				JsonRepresentation jsonRep = new JsonRepresentation(json);

				return jsonRep.getText();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			try{
				JSONObject json = new JSONObject();
				List<Integer> gameIDs = new LinkedList<Integer>();
				gameIDs.add(1);
				gameIDs.add(2);
				gameIDs.add(3);
				List<Integer> Slots = new LinkedList<Integer>();
				Slots.add(2);
				Slots.add(5);
				Slots.add(9);
				json.put("GameIDs", gameIDs);
				json.put("Slots", Slots); 

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
