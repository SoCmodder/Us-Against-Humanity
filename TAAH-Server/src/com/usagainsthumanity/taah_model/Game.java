package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gameID;
	private Integer hostID;
	private Integer Slots;
	private Integer PointsToWin;
	private Boolean Private;
	private Integer State;
	private Integer UserTurn;
	private Integer Winner;
	private Integer CurrentBlack;
	/**
	 * @param hostID
	 * @param slots
	 * @param pointsToWin
	 * @param private1
	 * @param state
	 * @param userTurn
	 * @param winner
	 * @param currentBlack
	 */
	public Game(Integer hostID, Integer slots, Integer pointsToWin,
			Boolean private1, Integer state, Integer userTurn, Integer winner,
			Integer currentBlack) {
		this.hostID = hostID;
		Slots = slots;
		PointsToWin = pointsToWin;
		Private = private1;
		State = state;
		UserTurn = userTurn;
		Winner = winner;
		CurrentBlack = currentBlack;
	}
	/**
	 * @return the hostID
	 */
	public Integer getHostID() {
		return hostID;
	}
	/**
	 * @param hostID the hostID to set
	 */
	public void setHostID(Integer hostID) {
		this.hostID = hostID;
	}
	/**
	 * @return the slots
	 */
	public Integer getSlots() {
		return Slots;
	}
	/**
	 * @param slots the slots to set
	 */
	public void setSlots(Integer slots) {
		Slots = slots;
	}
	/**
	 * @return the pointsToWin
	 */
	public Integer getPointsToWin() {
		return PointsToWin;
	}
	/**
	 * @param pointsToWin the pointsToWin to set
	 */
	public void setPointsToWin(Integer pointsToWin) {
		PointsToWin = pointsToWin;
	}
	/**
	 * @return the private
	 */
	public Boolean getPrivate() {
		return Private;
	}
	/**
	 * @param private1 the private to set
	 */
	public void setPrivate(Boolean private1) {
		Private = private1;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return State;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		State = state;
	}
	/**
	 * @return the userTurn
	 */
	public Integer getUserTurn() {
		return UserTurn;
	}
	/**
	 * @param userTurn the userTurn to set
	 */
	public void setUserTurn(Integer userTurn) {
		UserTurn = userTurn;
	}
	/**
	 * @return the winner
	 */
	public Integer getWinner() {
		return Winner;
	}
	/**
	 * @param winner the winner to set
	 */
	public void setWinner(Integer winner) {
		Winner = winner;
	}
	/**
	 * @return the currentBlack
	 */
	public Integer getCurrentBlack() {
		return CurrentBlack;
	}
	/**
	 * @param currentBlack the currentBlack to set
	 */
	public void setCurrentBlack(Integer currentBlack) {
		CurrentBlack = currentBlack;
	}
	/**
	 * @return the gameID
	 */
	public Integer getGameID() {
		return gameID;
	}
	
	
	
}
