package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardsInPlayWhite {
	
	@Id
	private Integer gameID;
	private Integer userID;
	private Integer cardID;
	/**
	 * @param gameID
	 * @param userID
	 * @param cardID
	 */
	public CardsInPlayWhite(Integer gameID, Integer userID, Integer cardID) {
		this.gameID = gameID;
		this.userID = userID;
		this.cardID = cardID;
	}

    public CardsInPlayWhite() {
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
	/**
	 * @return the cardID
	 */
	public Integer getCardID() {
		return cardID;
	}
	/**
	 * @param cardID the cardID to set
	 */
	public void setCardID(Integer cardID) {
		this.cardID = cardID;
	}
	
	

}
