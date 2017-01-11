package com.chat.server.beans;

public class Country {
	private int id;
	private String name;
	private String code;
	private String iso;
	private String flag;

	public Country() {
	}

	public Country(int id, String name, String code, String iso, String flag) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.iso = iso;
		this.flag = flag;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
