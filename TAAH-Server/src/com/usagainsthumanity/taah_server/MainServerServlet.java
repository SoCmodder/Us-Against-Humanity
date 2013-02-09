package com.usagainsthumanity.taah_server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class MainServerServlet extends Application{

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
	// Create a router Restlet that routes each call to a new Resource
	Router router = new Router(getContext());
	router.attach("/user", UserJSONResource.class);
	router.attach("/user/{userId}", UserDataJSONResource.class);
	router.attach("/game", GameJSONResource.class);
	router.attach("/game/{gameId}", GameDataJSONResource.class);
	router.attach("/game/{gameId}/last", GameLastDataJSONResource.class);
	router.attach("/game/{gameId}/user", GameUserJSONResource.class);
	router.attach("/game/{gameId}/user/{userId}", GameUserDataJSONResource.class);
	router.attach("/game/{gameId}/card", GameCardJSONResource.class);
	router.attach("/whitecard", WhiteCardJSONResource.class);
	router.attach("/blackcard", BlackCardJSONResource.class);
	return router;
	}
}
