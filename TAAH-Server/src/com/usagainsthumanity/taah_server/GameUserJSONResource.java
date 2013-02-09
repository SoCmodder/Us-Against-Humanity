package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class GameUserJSONResource extends ServerResource {

	@Get("json")
	public String getUsers(){
		Integer gameID = Integer.parseInt((String)this.getRequestAttributes().get("gameId"));
		try{
			JSONObject json = new JSONObject();
			List<Integer> Users = new LinkedList<Integer>();
			Users.add(1234);
			Users.add(5678);
			Users.add(9101112);
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
