package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardBlack {

	@Id
	private Integer cardID;
	private String cardText;
	/**
	 * @param cardID
	 * @param cardText
	 */
	public CardBlack(Integer cardID, String cardText) {
		this.cardID = cardID;
		this.cardText = cardText;
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
	/**
	 * @return the cardText
	 */
	public String getCardText() {
		return cardText;
	}
	/**
	 * @param cardText the cardText to set
	 */
	public void setCardText(String cardText) {
		this.cardText = cardText;
	}
	
	
}
