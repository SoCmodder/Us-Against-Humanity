package com.usagainsthumanity.taah_server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class WhiteCardJSONResource extends ServerResource {
	
	@Get("json")
	public String handleGet() {
		try{
			//TODO All of the things
			String cardId = getQuery().getValues("cardId");
			JSONObject json = new JSONObject();
			json.put("Text", "Sexual Tension"); 

			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
