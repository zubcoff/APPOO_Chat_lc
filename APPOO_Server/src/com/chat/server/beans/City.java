package com.chat.server.beans;

public class City {
	private int id;
	private String name;
	private double utc;
	private int countryId;

	public City() {
	}

	public City(int id, String name, double utc, int countryId) {
		this.id = id;
		this.name = name;
		this.utc = utc;
		this.countryId = countryId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUtc() {
		return utc;
	}

	public void setUtc(double utc) {
		this.utc = utc;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
}
