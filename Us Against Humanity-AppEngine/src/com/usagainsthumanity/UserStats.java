/**
 * 
 */
package com.usagainsthumanity;

import javax.persistence.Id;

/**
 * @author Stewart
 *
 */
public class UserStats {
	
	@Id
	private String userID;
	private Integer GamesPlayed;
	private Integer GamesWon;
	private Integer FavBlack;
	private String FavWhite;
	
	/**
	 * @param userID
	 * @param gamesPlayed
	 * @param gamesWon
	 * @param favBlack
	 * @param favWhite
	 */
	public UserStats(String userID, Integer gamesPlayed, Integer gamesWon,
			Integer favBlack, String favWhite) {
		this.userID = userID;
		GamesPlayed = gamesPlayed;
		GamesWon = gamesWon;
		FavBlack = favBlack;
		FavWhite = favWhite;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the gamesPlayed
	 */
	public Integer getGamesPlayed() {
		return GamesPlayed;
	}

	/**
	 * @param gamesPlayed the gamesPlayed to set
	 */
	public void setGamesPlayed(Integer gamesPlayed) {
		GamesPlayed = gamesPlayed;
	}

	/**
	 * @return the gamesWon
	 */
	public Integer getGamesWon() {
		return GamesWon;
	}

	/**
	 * @param gamesWon the gamesWon to set
	 */
	public void setGamesWon(Integer gamesWon) {
		GamesWon = gamesWon;
	}

	/**
	 * @return the favBlack
	 */
	public Integer getFavBlack() {
		return FavBlack;
	}

	/**
	 * @param favBlack the favBlack to set
	 */
	public void setFavBlack(Integer favBlack) {
		FavBlack = favBlack;
	}

	/**
	 * @return the favWhite
	 */
	public String getFavWhite() {
		return FavWhite;
	}

	/**
	 * @param favWhite the favWhite to set
	 */
	public void setFavWhite(String favWhite) {
		FavWhite = favWhite;
	}
}
