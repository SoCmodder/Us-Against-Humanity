package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameID;
	private Long hostID;
	private Long Slots;
	private Long PointsToWin;
	private Boolean Private;
	private Long State;
	private Long UserTurn;
	private Long Winner;
	private Long CurrentBlack;

    public Game(){

    }

	public Game(Long hostID, Long slots, Long pointsToWin,
			Boolean private1) {
		this.hostID = hostID;
		Slots = slots;
		PointsToWin = pointsToWin;
		Private = private1;
	}
	/**
	 * @return the hostID
	 */
	public Long getHostID() {
		return hostID;
	}
	/**
	 * @param hostID the hostID to set
	 */
	public void setHostID(Long hostID) {
		this.hostID = hostID;
	}
	/**
	 * @return the slots
	 */
	public Long getSlots() {
		return Slots;
	}
	/**
	 * @param slots the slots to set
	 */
	public void setSlots(Long slots) {
		Slots = slots;
	}
	/**
	 * @return the pointsToWin
	 */
	public Long getPointsToWin() {
		return PointsToWin;
	}
	/**
	 * @param pointsToWin the pointsToWin to set
	 */
	public void setPointsToWin(Long pointsToWin) {
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
	public Long getState() {
		return State;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		State = state;
	}
	/**
	 * @return the userTurn
	 */
	public Long getUserTurn() {
		return UserTurn;
	}
	/**
	 * @param userTurn the userTurn to set
	 */
	public void setUserTurn(Long userTurn) {
		UserTurn = userTurn;
	}
	/**
	 * @return the winner
	 */
	public Long getWinner() {
		return Winner;
	}
	/**
	 * @param winner the winner to set
	 */
	public void setWinner(Long winner) {
		Winner = winner;
	}
	/**
	 * @return the currentBlack
	 */
	public Long getCurrentBlack() {
		return CurrentBlack;
	}
	/**
	 * @param currentBlack the currentBlack to set
	 */
	public void setCurrentBlack(Long currentBlack) {
		CurrentBlack = currentBlack;
	}
	/**
	 * @return the gameID
	 */
	public Long getGameID() {
		return gameID;
	}
	
	
	
}
