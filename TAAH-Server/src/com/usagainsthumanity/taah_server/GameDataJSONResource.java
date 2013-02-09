package com.usagainsthumanity.taah_server;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class GameDataJSONResource extends ServerResource {
	
	@Get("json")
	public String getStats(){
		//TODO Actual lookup
		try{
			String gameID = (String)this.getRequestAttributes().get("gameId");
			JSONObject json = new JSONObject();
			List<Integer> Scores = new LinkedList<Integer>();
			Scores.add(1);
			Scores.add(0);
			Scores.add(0);
			List<Integer> IDS = new LinkedList<Integer>();
			IDS.add(6969);
			IDS.add(1337);
			IDS.add(80085);
			List<Integer> HasPlayed = new LinkedList<Integer>();
			HasPlayed.add(1);
			json.put("Scores", Scores);
			json.put("IDs", IDS);
			json.put("CardsToWin", 99);
			json.put("PlayerTurn", 1);
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
