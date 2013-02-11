package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GamePlayers {

	@Id
	private Integer gameID;
	private Integer userID;
	/**
	 * @param gameID
	 * @param userID
	 */
	public GamePlayers(Integer gameID, Integer userID) {
		this.gameID = gameID;
		this.userID = userID;
	}
	
	/**
	 * @return the gameID
	 */
	public Integer getGameID() {
		return gameID;
	}
	
	/**
	 * @param gameID the gameID to set
	 */
	public void setGameID(Integer gameID) {
		this.gameID = gameID;
	}
	/**
	 * @return the userID
	 */
	public Integer getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	
}