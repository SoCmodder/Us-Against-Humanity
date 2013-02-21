package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
//TODO Change the name to GameLastRound
public class GameLast {

	@Id
	private Long gameID;
	private Long WinnerUserID;
	private Long PlayedWhite;
	private Long WinningWhite;
	private Long WinningBlack;

	/**
	 * @param gameID
	 * @param WinnerUserID
	 */
	public GameLast(Long gameID, Long WinnerUserID, Long PlayedWhite, Long WinningWhite, Long WinningBlack) {
		this.gameID = gameID;
		this.WinnerUserID = WinnerUserID;
		this.PlayedWhite = PlayedWhite;
		this.WinningWhite = WinningWhite;
		this.WinningBlack = WinningBlack;
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
	 * @return the winnerUserID
	 */
	public Long getWinnerUserID() {
		return WinnerUserID;
	}

	/**
	 * @param winnerUserID the winnerUserID to set
	 */
	public void setWinnerUserID(Long winnerUserID) {
		WinnerUserID = winnerUserID;
	}

	/**
	 * @return the playedWhite
	 */
	public Long getPlayedWhite() {
		return PlayedWhite;
	}

	/**
	 * @param playedWhite the playedWhite to set
	 */
	public void setPlayedWhite(Long playedWhite) {
		PlayedWhite = playedWhite;
	}

	/**
	 * @return the winningWhite
	 */
	public Long getWinningWhite() {
		return WinningWhite;
	}

	/**
	 * @param winningWhite the winningWhite to set
	 */
	public void setWinningWhite(Long winningWhite) {
		WinningWhite = winningWhite;
	}

	/**
	 * @return the winningBlack
	 */
	public Long getWinningBlack() {
		return WinningBlack;
	}

	/**
	 * @param winningBlack the winningBlack to set
	 */
	public void setWinningBlack(Long winningBlack) {
		WinningBlack = winningBlack;
	}
}
