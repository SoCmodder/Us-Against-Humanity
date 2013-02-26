package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardsInDiscardWhite {
	
	@Id
	private Integer gameID;
	private Integer cardID;
	/**
	 * @param gameID
	 * @param cardID
	 */
	public CardsInDiscardWhite(Integer gameID, Integer cardID) {
		this.gameID = gameID;
		this.cardID = cardID;
	}

    public CardsInDiscardWhite() {
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
