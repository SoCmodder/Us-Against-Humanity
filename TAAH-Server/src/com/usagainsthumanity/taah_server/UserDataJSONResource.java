package com.usagainsthumanity.taah_server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class UserDataJSONResource extends ServerResource {
	@Get("json")
	public String handleGet() {
		try{
			//TODO All of the things
			String userID = (String)this.getRequestAttributes().get("userId");
			
			JSONObject json = new JSONObject();
			json.put("name", userID); 

			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Put
	public String putAccessToken(Representation entity) throws IOException{
		return entity.getText();
	}
}
