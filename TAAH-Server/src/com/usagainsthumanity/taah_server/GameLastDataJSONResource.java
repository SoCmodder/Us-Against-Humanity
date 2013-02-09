package com.usagainsthumanity.taah_server;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GameLastDataJSONResource extends ServerResource {
	
	@Get("json")
	public String getLastMove(){
		Integer gameID = Integer.parseInt((String)this.getRequestAttributes().get("gameId"));
		//Query the DataStore for this information
		try{
			if(gameID == 80085){
				//Game Over
				return null;
			}else{
				//Last move
				JSONObject json = new JSONObject();
				List<Integer> CardsPlayed = new LinkedList<Integer>();
				CardsPlayed.add(21);
				CardsPlayed.add(156);
				CardsPlayed.add(6969);
				json.put("CardsPlayed", CardsPlayed);
				json.put("WinningCard", 2);
				json.put("BlackCard", 42);
				json.put("RoundWinner", 58468);			
				JsonRepresentation jsonRep = new JsonRepresentation(json);
				return jsonRep.getText();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
