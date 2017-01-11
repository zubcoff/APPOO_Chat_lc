package com.chat.server.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.server.services.CommandService;

public class ChatAppServlet extends ServerServlet {
	private static final long serialVersionUID = 1L;
	private CommandService commandService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		commandService = new CommandService(getMySQLConnection());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		commandService.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
