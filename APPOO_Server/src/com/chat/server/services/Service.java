package com.chat.server.services;

import com.chat.server.db.MySQLConnection;

public class Service {
	private MySQLConnection mySQLConnection;

	public Service(MySQLConnection mySQLConnection) {
		this.mySQLConnection = mySQLConnection;
	}

	public MySQLConnection getMySQLConnection() {
		return mySQLConnection;
	}

	public void setMySQLConnection(MySQLConnection mySQLConnection) {
		this.mySQLConnection = mySQLConnection;
	}
}
