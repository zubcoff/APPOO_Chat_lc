package com.chat.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySQLConnection {

	private Connection connection = null;
	private Statement statement = null;

	public void close() {
		try {
			if (statement != null && !statement.isClosed()) {
				System.out.println("Closing SQL Statement");
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (connection != null && !connection.isClosed()) {
				System.out.println("Closing Database Connection");
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void MySQLConnect(String host, String port, String dbName, String dbUser, String dbPass)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
		Properties properties = new Properties();
		properties.setProperty("user", dbUser);
		properties.setProperty("password", dbPass);
		properties.setProperty("useUnicode", "true");
		properties.setProperty("characterEncoding", "UTF-8");

		connection = DriverManager.getConnection(url, properties);
	}

	public void MySQLConnect(Properties properties) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		String mysql_host = properties.getProperty("mysql_host");
		String mysql_port = properties.getProperty("mysql_port");
		String mysql_db_user = properties.getProperty("mysql_db_user");
		String mysql_db_pass = properties.getProperty("mysql_db_pass");
		String mysql_db_name = properties.getProperty("mysql_db_name");

		String url = String.format("jdbc:mysql://%s:%s/%s", mysql_host, mysql_port, mysql_db_name);
		connection = DriverManager.getConnection(url, mysql_db_user, mysql_db_pass);
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public ResultSet query(String selectSQL) throws SQLException {
		statement = connection.createStatement();
		return statement.executeQuery(selectSQL);
	}
}
