package com.chat.server.beans;

import java.util.Date;

public class Message {
	private int id;
	private int fromId;
	private int toId;
	private Date sendDate;
	private String message;

	public Message() {
	}

	public Message(int id, int fromId, int toId, Date sendDate, String message) {
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.sendDate = sendDate;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
