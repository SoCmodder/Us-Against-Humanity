package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GameUsers {

	@Id
	private Long gameID;
	private Long userID;
	private Long score;
	/**
	 * @param gameID
	 * @param userID
	 * @param score
	 */
	public GameUsers(Long gameID, Long userID, Long score) {
		this.gameID = gameID;
		this.userID = userID;
		this.score = score;
	}

    public GameUsers() {
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
	public Long getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	/**
	 * @return the score
	 */
	public Long getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Long score) {
		this.score = score;
	}
}
