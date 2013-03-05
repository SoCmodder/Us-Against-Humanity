package com.usagainsthumanity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GameUsers {

	@Id
	private Long gameID;
	private String userID;
	/**
	 * @param id
	 * @param userID
	 */
	public GameUsers(Long id, String userID) {
		this.gameID = id;
		this.userID = userID;
	}
	
	/**
	 * @return the gameID
	 */
	public Long getGameID() {
		return gameID;
	}
	
	/**
	 * @param gameID the gameID to set
	 */
	public void setGameID(Long gameID) {
		this.gameID = gameID;
	}
	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
