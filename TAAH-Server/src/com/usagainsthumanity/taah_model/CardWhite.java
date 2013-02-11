package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardWhite {

	@Id
	private Integer cardID;
	private String Description;
	
	/**
	 * @param cardID
	 * @param description
	 */
	public CardWhite(Integer cardID, String description) {
		this.cardID = cardID;
		Description = description;
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
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	
	
}
