package com.chat.server.beans;

import java.util.Date;
import java.util.List;

public class User {
	private int id;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private int cityId;
	private int phone;
	private String password;
	private Date registrationDate;
	private Date lastDate;
	private String image;
	private List<Integer> frindIds;

	public User() {
		id = 0;
		userName = null;
		firstName = null;
		lastName = null;
		email = null;
		cityId = 0;
		phone = 0;
		password = null;
		registrationDate = null;
		lastDate = null;
		frindIds = null;
	}

	public User(int id, String userName, String firstName, String lastName, String email, int cityId, int phone, String password, Date registrationDate,
			Date lastDate, String image, List<Integer> frindIds) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.cityId = cityId;
		this.phone = phone;
		this.password = password;
		this.registrationDate = registrationDate;
		this.lastDate = lastDate;
		this.image = image;
		this.frindIds = frindIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Integer> getFrindIds() {
		return frindIds;
	}

	public void setFrindIds(List<Integer> frindIds) {
		this.frindIds = frindIds;
	}
}
