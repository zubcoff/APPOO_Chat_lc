package com.chat.app.beans;

import java.util.Date;

public class MessageSQL {
	private int id;
	private String fromUser;
	private String toUser;
	private String message;
	private boolean readed;
	private Date date;

	public MessageSQL(int id, String fromUser, String toUser, String message, boolean readed, Date date) {
		this.id = id;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.message = message;
		this.readed = readed;
		this.date = date;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}
}
