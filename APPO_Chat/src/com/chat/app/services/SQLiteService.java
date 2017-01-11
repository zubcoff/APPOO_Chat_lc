package com.chat.app.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chat.app.beans.MessageSQL;

public class SQLiteService {
	private String dbName;
	private Connection connection;
	private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY AUTOINCREMENT, from_user CHAR(128) NOT NULL, to_user CHAR(128) NOT NULL, message TEXT NOT NULL, readed BOOLEAN DEFAULT 0, date UNSIGNED BIG INT NOT NULL)";
	private static final String INSERT_SQL = "INSERT INTO messages (from_user, to_user, message, readed, date) VALUES (?,?,?,?,?)";
	private static final String SELECT_FROM_TO_SQL = "SELECT * FROM messages WHERE (from_user = ? AND to_user = ?) OR (to_user = ? AND from_user = ?) ORDER BY DATE ASC";
	private static final String SELECT_COUNT_FROM_TO_SQL = "SELECT count(*) FROM messages WHERE from_user = ? AND to_user = ? AND readed = 0";
	private static final String UPDATE_MESSAGES_FROM_TO_SQL = "UPDATE messages SET readed = 1 WHERE from_user = ? AND to_user = ?";

	public SQLiteService(String dbName) {
		this.dbName = dbName;
	}

	public void init() throws ClassNotFoundException, SQLException {
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			statement = connection.createStatement();
			statement.executeUpdate(CREATE_TABLE_SQL);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public void disconnect() throws SQLException {
		if (getConnection() != null && !getConnection().isClosed()) {
			getConnection().close();
		}
	}

	public void insertMessage(String fromUser, String toUser, String message, boolean readed, Date date) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(INSERT_SQL);
			preparedStatement.setString(1, fromUser);
			preparedStatement.setString(2, toUser);
			preparedStatement.setString(3, message);
			preparedStatement.setBoolean(4, readed);
			preparedStatement.setLong(5, date.getTime());
			preparedStatement.executeUpdate();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	public List<MessageSQL> getMessages(String fromUser, String toUser) throws SQLException {
		PreparedStatement preparedStatement = null;
		List<MessageSQL> messageSQL = null;
		try {
			preparedStatement = connection.prepareStatement(SELECT_FROM_TO_SQL);
			preparedStatement.setString(1, fromUser);
			preparedStatement.setString(2, toUser);
			preparedStatement.setString(3, fromUser);
			preparedStatement.setString(4, toUser);
			ResultSet executeQuery = preparedStatement.executeQuery();
			messageSQL = new ArrayList<MessageSQL>();
			while (executeQuery.next()) {
				int id = executeQuery.getInt("id");
				String from = executeQuery.getString("from_user");
				String to = executeQuery.getString("to_user");
				String message = executeQuery.getString("message");
				boolean readed = executeQuery.getBoolean("readed");
				Date date = new Date(executeQuery.getLong("date"));
				messageSQL.add(new MessageSQL(id, from, to, message, readed, date));
			}
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return messageSQL;
	}

	public int getNumUnreadMsg(String fromUser, String toUser) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SELECT_COUNT_FROM_TO_SQL);
			preparedStatement.setString(1, fromUser);
			preparedStatement.setString(2, toUser);
			ResultSet executeQuery = preparedStatement.executeQuery();
			if (executeQuery.next()) {
				int count = executeQuery.getInt(1);
				return count;
			}
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return 0;
	}

	public void makeAsReadUnreadMsg(String fromUser, String toUser) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(UPDATE_MESSAGES_FROM_TO_SQL);
			preparedStatement.setString(1, fromUser);
			preparedStatement.setString(2, toUser);
			preparedStatement.executeUpdate();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
