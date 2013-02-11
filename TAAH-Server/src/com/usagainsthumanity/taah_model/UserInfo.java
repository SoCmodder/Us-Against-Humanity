package com.usagainsthumanity.taah_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserInfo {
	
	@Id
	private Integer userID;
	private String firstName;
	private String lastName;
	private String profilePicture;
	
	
	/**
	 * @param userID
	 * @param firstName
	 * @param lastName
	 * @param profilePicture
	 */
	public UserInfo(Integer userID, String firstName, String lastName,
			String profilePicture) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicture = profilePicture;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the profilePicture
	 */
	public String getProfilePicture() {
		return profilePicture;
	}


	/**
	 * @param profilePicture the profilePicture to set
	 */
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	

}
