package com.usagainsthumanity.taah_server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.DatastoreService;

public class UserJSONResource extends ServerResource{

	@Get("json")
	public String getEmail() {
		try{
			//TODO All of the things
			String email = getQuery().getValues("email");
			JSONObject json = new JSONObject();
			json.put("name", email); 

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

