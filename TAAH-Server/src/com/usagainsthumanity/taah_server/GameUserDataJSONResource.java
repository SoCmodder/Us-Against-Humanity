package com.usagainsthumanity.taah_server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.GameUsers;

public class GameUserDataJSONResource extends ServerResource {
	
	@Delete
	public void deleteUserFromGame(){
		String gameID = (String)this.getRequestAttributes().get("gameId");
		String userID = (String)this.getRequestAttributes().get("gameId");
		//TODO Everything else including error handling
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select u from GameUsers u where u.gameID = :gameID and u.userID = :userID");
		q.setParameter("gameID", gameID);
		q.setParameter("userID", userID);
		List<GameUsers> users = q.getResultList();
		if(users.size() == 1){
			em.remove(users.get(0));
		}
		em.close();
	}
}
