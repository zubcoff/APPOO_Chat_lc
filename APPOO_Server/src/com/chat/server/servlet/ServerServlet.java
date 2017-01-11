package com.chat.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.server.db.MySQLConnection;
import com.logger.Logger;

public class ServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MySQLConnection mySQLConnection;
	private Properties properties;

	public void init(ServletConfig config) throws ServletException {
		try {
			mySQLConnection = new MySQLConnection();
			loadPropertiesFile("config/server.properties");
			Logger.init(properties);
			Logger.debug(this, "Properties file has been loaded", new String[] {}, null);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			mySQLConnection.MySQLConnect(properties);
			Logger.debug(this, "MySQL Conected", new String[] {}, null);
			super.service(req, resp);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void destroy() {
		mySQLConnection.close();
	}

	public MySQLConnection getMySQLConnection() {
		return mySQLConnection;
	}

	public void setMySQLConnection(MySQLConnection mySQLConnection) {
		this.mySQLConnection = mySQLConnection;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void loadPropertiesFile(String propertiesFile) throws IOException {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
			properties = new Properties();
			properties.load(input);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
