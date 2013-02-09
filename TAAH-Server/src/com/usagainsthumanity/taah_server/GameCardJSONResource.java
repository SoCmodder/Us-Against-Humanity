package com.usagainsthumanity.taah_server;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class GameCardJSONResource extends ServerResource {
	
	
	@Get("json")
	public String getCard(){
		Boolean black = getQuery().getValues("black")!=null?Boolean.parseBoolean(getQuery().getValues("black")):null;
		if(black != null){
			return "Yes";
		}
		
		return null;
	}
	
	@Put
	public String playCard(Representation entity) throws IOException{
		return entity.getText();
	}
}
