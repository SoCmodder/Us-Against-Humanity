package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GameUsers {

	@Id
	private Long gameID;
	private Long userID;
	private Long score;
	private Boolean hasPlayed;
	/**
	 * @param gameID
	 * @param userID
	 * @param score
	 * @param hasPlayed tells whether the corresponding userID has played this round.
	 */
	public GameUsers(Long gameID, Long userID, Long score, Boolean hasPlayed) {
		this.gameID = gameID;
		this.userID = userID;
		this.score = score;
		this.hasPlayed = hasPlayed;
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
	/**
	 * @return hasPlayed tells whether the corresponding userID has played
	 */
	public Boolean getHasPlayed() {
		return this.hasPlayed;
	}
	/**
	 * @param hasPlayed value to set whether the corresponding userID has played this round
	 */
	public void setHasplayed(Boolean hasPlayed) {
		this.hasPlayed = hasPlayed;
	}
}
