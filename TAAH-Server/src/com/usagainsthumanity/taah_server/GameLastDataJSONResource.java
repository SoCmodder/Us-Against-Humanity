package com.usagainsthumanity.taah_server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.Game;
import com.usagainsthumanity.taah_model.GameLast;

public class GameLastDataJSONResource extends ServerResource {
	@Get("json")
	//Considerations: If its the first round, there is no last round. Fail.
	public String getLastRound(){//Renamed from getLastMove
		try{
			//gameID being requested
			String gameID = (String)this.getRequestAttributes().get("gameID");
			EntityManager em = EMFService.get().createEntityManager();
			//Query from the GameLast table in datastore
			//TODO Rename this damned thing. FUCK.
			Query gameRoundQ = em.createNamedQuery("select u from GameLast u where u.gameID = :gameID");
			gameRoundQ.setParameter("gameID", gameID);
			//Put the gameData into a List
			List<GameLast> gameRoundData = gameRoundQ.getResultList();
			//Query from the Game stats so we can know if its been completed
			Query gameQ = em.createNamedQuery("select u from Game u where u.gameID = :gameID");
			gameQ.setParameter("gameID", gameID);
			List<Game> gameData =  gameQ.getResultList();
			//Go ahead and make a variable for the game state, since It'll be used twice
			Long gameState = gameData.get(0).getState();
			if (gameRoundData.size() == 1 && gameState != 2) {//If the gameData or Game is empty, then the game must not exist
				JSONObject json = new JSONObject();
				//The played white cards should be an array...little confused of how this is working.
				//Assuming it should be coming in from the datastore as an array,
				//but EVERYTHING is an int, bool, or string...so I followed suit
				json.put("CardsPlayed",gameRoundData.get(0).getPlayedWhite());
				json.put("WinningCard",gameRoundData.get(0).getWinningWhite());
				json.put("BlackCard", gameRoundData.get(0).getWinningBlack());
				json.put("RoundWinner", gameRoundData.get(0).getWinnerUserID());
				JsonRepresentation jsonRep = new JsonRepresentation(json);
				return jsonRep.getText();
			}
//			else if (gameRoundData.size() == 0) {
//				//Should be throwing some sort of error for gameDNE...
//			}
			else if (gameState == 2) {
				throw new ResourceException(204);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
