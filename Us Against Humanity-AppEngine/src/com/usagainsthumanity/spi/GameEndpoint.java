package com.usagainsthumanity.spi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.usagainsthumanity.EMF;
import com.usagainsthumanity.Game;
import com.usagainsthumanity.GameLast;
import com.usagainsthumanity.GameUsers;

@Api(name = "usagainsthumanity")
public class GameEndpoint {


	@ApiMethod(name = "game.list.open", httpMethod="GET")
	@SuppressWarnings("unchecked")
	public List<Game> listOpenGames(User user) throws OAuthRequestException{
		Query q = null;
		if(user != null){
			EntityManager em = EMF.get().createEntityManager();
			q = em.createQuery("SELECT g FROM Game g WHERE g.State = 0");			
		}else{
			throw new OAuthRequestException("Invalid user.");
		}		
		return (List<Game>) q.getResultList();
	}


	@ApiMethod(name = "game.list.hosted", httpMethod="GET")
	@SuppressWarnings("unchecked")
	public List<Game> listHostedGames(User user) throws OAuthRequestException{
		Query q = null;
		if(user != null){
			EntityManager em = EMF.get().createEntityManager();
			q = em.createQuery("SELECT g FROM Game g WHERE g.hostID = :hostID");
			q.setParameter("hostID", user.getUserId());
		}else{
			throw new OAuthRequestException("Invalid user.");
		}		
		return (List<Game>) q.getResultList();		
	}



	@ApiMethod(name = "game.byIDs", httpMethod="GET")
	@SuppressWarnings("unchecked")
	public Game getGame(@Named("ID") String id, User user) throws OAuthRequestException{
		Query q = null;
		if(user != null){
			EntityManager em = EMF.get().createEntityManager();

			q = em.createQuery("SELECT g FROM Game g WHERE g.gameID = :gameID");
			q.setParameter("gameID", id);
		}else{
			throw new OAuthRequestException("Invalid user.");
		}		
		return ((List<Game>) q.getResultList()).get(0);		
	}

	@ApiMethod(name = "game.last", httpMethod="GET")
	@SuppressWarnings("unchecked")
	public GameLast getLast(@Named("ID") String id, User user) throws OAuthRequestException{
		Query q = null;
		if(user != null){
			EntityManager em = EMF.get().createEntityManager();
			q = em.createQuery("SELECT gl FROM GameLast gl WHERE gl.gameID = :gameID");
			q.setParameter("gameID", id);
		}else{
			throw new OAuthRequestException("Invalid user.");
		}
		return ((GameLast) q.getResultList().get(0));
	}

	@ApiMethod(name = "game.users", httpMethod="GET")
	@SuppressWarnings("unchecked")
	public List<String> getUsersInGame(@Named("gameID") String id, User user) throws OAuthRequestException, UnauthorizedException{
		Query q = null;
		if(user != null){			
			EntityManager em = EMF.get().createEntityManager();
			q = em.createQuery("SELECT gu FROM GameUsers gu WHERE gu.gameID = :gameID");
			q.setParameter("gameID", id);
			List<GameUsers> gameUsers = (List<GameUsers>)q.getResultList();
			q = null;
			boolean authorized = false;
			int i = 0;
			while(!authorized){
				if(gameUsers.get(i).getUserID().equals(user.getUserId())){
					authorized = true;
				}
				++i;
			}
			if(!authorized){
				throw new UnauthorizedException("User is unauthorized");
			}	
			List<String> Users = new ArrayList<String>();
			for(GameUsers gameUser: gameUsers){
				Users.add(gameUser.getUserID());
			}
			em.close();
			return Users;

		}else{
			throw new OAuthRequestException("Invalid user.");
		}
		
	}
	
	
	@ApiMethod(name="game.users", httpMethod="PUT")
	public void addUserToGame(@Named("gameID") Long id, User user){
		if(user != null){
			EntityManager em = EMF.get().createEntityManager();
			Query q = em.createQuery("SELECT g FROM Game g WHERE g.gameID = :gameID");
			q.setParameter("gameID", id);
			Game game = (Game)(q.getResultList().get(0));			
			if(game != null && game.getState() == 0){
				q = null;
				q = em.createQuery("SELECT gu FROM GameUsers gu WHERE gu.gameID = :gameID");
				q.setParameter("gameID", id);
				List<GameUsers> gameUsers = (List<GameUsers>) q.getResultList();
				if(gameUsers != null && gameUsers.size() < game.getSlots() - 1){
					em.persist(new GameUsers(id, user.getUserId()));
					if(gameUsers.size()+1 == game.getSlots()-1){
						game.setState(new Long(1));
						//TODO send GCM to all devices announcing game start
						em.persist(game);
					}
				}
			}
			em.close();
		}
	}


}
