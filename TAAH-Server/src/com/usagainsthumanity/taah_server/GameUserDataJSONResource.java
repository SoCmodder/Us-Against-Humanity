package com.usagainsthumanity.taah_server;

import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

public class GameUserDataJSONResource extends ServerResource {
	
	@Delete
	public void deleteUserFromGame(){
		Integer gameID = Integer.parseInt((String)this.getRequestAttributes().get("gameId"));
		Integer userID = Integer.parseInt((String)this.getRequestAttributes().get("gameId"));
		//TODO Everything else including error handling
	}

}
