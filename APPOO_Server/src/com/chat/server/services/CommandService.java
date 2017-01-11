package com.chat.server.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.server.db.MySQLConnection;

public class CommandService extends Service {

	public CommandService(MySQLConnection mySQLConnection) {
		super(mySQLConnection);
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	}

}
