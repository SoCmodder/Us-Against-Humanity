package com.usagainsthumanity.taah_server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.restlet.resource.Delete;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.Game;
import com.usagainsthumanity.taah_model.GameUsers;

public class GameUserDataJSONResource extends ServerResource {

	@Delete
	public void deleteUserFromGame(){
		try {
			String gameID = (String)this.getRequestAttributes().get("gameID");
			String userID = (String)this.getRequestAttributes().get("userID");
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select u from GameUsers u where u.gameID = :gameID and u.userID = :userID");
			q.setParameter("gameID", gameID);
			q.setParameter("userID", userID);
			List<GameUsers> users = q.getResultList();
			if(users.size() == 1){//If there is more than one element, run
				//Create a new query to get this games Game data
				Query eq = em.createQuery("select u from Game u where u.gameID = :gameID");
				eq.setParameter("gameID", gameID);
				List<Game> gameData = eq.getResultList();
				//Grab the hostID of this game, make it a string so we can compare host to the calling user
				String hostID = String.valueOf(gameData.get(0).getHostID());
				Long gameState = gameData.get(0).getState();
				if(gameState == 0 && hostID == userID){
					em.remove(users.get(0));
				} else if (gameState != 0) {//If the game is already started, it can't be deleted
					throw new ResourceException(409);
				} else if (hostID != userID) {//If the user is not the host, they can't delete the game
					throw new ResourceException(401);
				}
			}
			em.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
