package com.chat.server.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.chat.server.beans.User;
import com.chat.server.db.MySQLConnection;
import com.string.StringUtils;

@SuppressWarnings("unchecked")
public class UserService extends Service {

	public UserService(MySQLConnection mySQLConnection) {
		super(mySQLConnection);
	}

	public User getUser(int id, boolean addFriends) throws Exception {
		List<User> users = getUsers(new int[] { id }, addFriends);
		return (users.isEmpty()) ? null : users.get(0);
	}

	public User getUser(String user_name, boolean addFriends) throws Exception {
		List<User> users = getUsers(new String[] { user_name }, addFriends);
		return (users.isEmpty()) ? null : users.get(0);
	}

	public JSONObject getUserAsJSONObject(int id, boolean addFriends) throws Exception {
		JSONArray users = getUsersAsJSONArray(new int[] { id }, addFriends);
		return (users.isEmpty()) ? null : (JSONObject) users.get(0);
	}

	public JSONObject getUserAsJSONObject(String user_name, boolean addFriends) throws Exception {
		JSONArray users = getUsersAsJSONArray(new String[] { user_name }, addFriends);
		return (users.isEmpty()) ? null : (JSONObject) users.get(0);
	}

	public JSONArray getUsersAsJSONArray(int[] ids, boolean addFriends) throws Exception {
		JSONArray users = new JSONArray();
		Connection connection = getMySQLConnection().getConnection();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {

			if (i == 0) {
				builder.append("?");
			} else {
				builder.append(",?");
			}
		}

		PreparedStatement prepareStatement = connection.prepareStatement(String.format("SELECT * FROM `chat_users` WHERE id IN(%s)", builder.toString()));
		for (int i = 0; i < ids.length; i++) {
			prepareStatement.setInt(i + 1, ids[i]);
		}
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				users.add(makeUserAsJSONObject(resultSet, addFriends));
			}
		} finally {
			resultSet.close();
		}
		return users;
	}

	public JSONArray getUsersAsJSONArray(String[] user_names, boolean addFriends) throws Exception {
		JSONArray users = new JSONArray();
		Connection connection = getMySQLConnection().getConnection();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < user_names.length; i++) {

			if (i == 0) {
				builder.append("?");
			} else {
				builder.append(",?");
			}
		}

		PreparedStatement prepareStatement = connection.prepareStatement(String.format("SELECT * FROM `chat_users` WHERE user_name IN(%s)", builder.toString()));
		for (int i = 0; i < user_names.length; i++) {
			prepareStatement.setString(i + 1, user_names[i]);
		}
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				users.add(makeUserAsJSONObject(resultSet, addFriends));
			}
		} finally {
			resultSet.close();
		}
		return users;
	}

	public List<User> getUsers(int[] ids, boolean addFriends) throws Exception {
		List<User> users = new ArrayList<User>();
		Connection connection = getMySQLConnection().getConnection();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {

			if (i == 0) {
				builder.append("?");
			} else {
				builder.append(",?");
			}
		}

		PreparedStatement prepareStatement = connection.prepareStatement(String.format("SELECT * FROM `chat_users` WHERE id IN(%s)", builder.toString()));
		for (int i = 0; i < ids.length; i++) {
			prepareStatement.setInt(i + 1, ids[i]);
		}
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				users.add(makeUser(resultSet, addFriends));
			}
		} finally {
			resultSet.close();
		}
		return users;
	}

	public List<User> getUsers(String[] user_names, boolean addFriends) throws Exception {
		List<User> users = new ArrayList<User>();
		Connection connection = getMySQLConnection().getConnection();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < user_names.length; i++) {

			if (i == 0) {
				builder.append("?");
			} else {
				builder.append(",?");
			}
		}

		PreparedStatement prepareStatement = connection.prepareStatement(String.format("SELECT * FROM `chat_users` WHERE user_name IN(%s)", builder.toString()));
		for (int i = 0; i < user_names.length; i++) {
			prepareStatement.setString(i + 1, user_names[i]);
		}
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				users.add(makeUser(resultSet, addFriends));
			}
		} finally {
			resultSet.close();
		}
		return users;
	}

	private User makeUser(ResultSet resultSet, boolean addFriends) throws Exception {
		int id = resultSet.getInt("id");
		String userName = resultSet.getString("user_name");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String email = resultSet.getString("email");
		int cityId = resultSet.getInt("city_id");
		int phone = resultSet.getInt("phone");
		String password = resultSet.getString("password");
		Date registrationDate = resultSet.getDate("registration_date");
		Date lastDate = resultSet.getDate("last_date");
		String image = resultSet.getString("image");
		return (new User(id, userName, firstName, lastName, email, cityId, phone, password, registrationDate, lastDate, image, (addFriends) ? getFriendsIds(id) : null));
	}

	public JSONArray getFriendsIdsAsJSONArray(int user_id) throws Exception {
		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_friends` WHERE user_id = ?");
		prepareStatement.setInt(1, user_id);
		ResultSet resultSet = prepareStatement.executeQuery();
		JSONArray friends = new JSONArray();
		try {
			while (resultSet.next()) {
				friends.add(resultSet.getInt("friend_id"));
			}
		} finally {
			resultSet.close();
		}
		return friends;
	}

	public JSONArray getFriendsAsJSONArray(int userId) throws Exception {
		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_users` WHERE id IN(SELECT friend_id FROM `chat_friends` WHERE user_id = ?)");
		prepareStatement.setInt(1, userId);
		ResultSet resultSet = prepareStatement.executeQuery();
		JSONArray friends = new JSONArray();
		try {
			while (resultSet.next()) {
				friends.add(makeUserAsJSONObject(resultSet, false));
			}
		} finally {
			resultSet.close();
		}
		return friends;
	}

	private JSONObject makeUserAsJSONObject(ResultSet resultSet, boolean addFriends) throws Exception {
		JSONObject user = new JSONObject();
		int id = resultSet.getInt("id");
		user.put("id", id);
		user.put("user_name", resultSet.getString("user_name"));
		user.put("first_name", resultSet.getString("first_name"));
		user.put("last_name", resultSet.getString("last_name"));
		user.put("email", resultSet.getString("email"));
		user.put("city_id", resultSet.getInt("city_id"));
		user.put("phone", resultSet.getInt("phone"));
		user.put("password", resultSet.getString("password"));
		user.put("registration_date", resultSet.getString("registration_date"));
		user.put("last_date", resultSet.getString("last_date"));
		user.put("image", resultSet.getString("image"));
		user.put("friends_ids", (addFriends) ? getFriendsIdsAsJSONArray(id) : null);
		return user;
	}

	public List<Integer> getFriendsIds(int user_id) throws Exception {
		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_friends` WHERE user_id = ?");
		prepareStatement.setInt(1, user_id);
		ResultSet resultSet = prepareStatement.executeQuery();
		List<Integer> friends = new ArrayList<Integer>();
		try {
			while (resultSet.next()) {
				friends.add(resultSet.getInt("friend_id"));
			}
		} finally {
			resultSet.close();
		}
		return friends;
	}

	public boolean existsUser(String userName) throws Exception {
		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_users` WHERE user_name = ?");
		prepareStatement.setString(1, userName);
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			return resultSet.next();
		} finally {
			resultSet.close();
		}
	}

	public boolean existsEmail(String email) throws Exception {
		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_users` WHERE email = ?");
		prepareStatement.setString(1, email);
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			return resultSet.next();
		} finally {
			resultSet.close();
		}
	}

	public int addNewUser(JSONObject jsonObject) throws Exception {
		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement(
				"INSERT INTO `appoo_db`.`chat_users` (`id`, `user_name`, `first_name`, `last_name`, `email`, `city_id`, `phone`, `password`, `registration_date`, `last_date`, `image`) "
						+ "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)");
		prepareStatement.setString(1, StringUtils.trimToEmpty((String) jsonObject.get("user_name")));
		prepareStatement.setString(2, StringUtils.trimToEmpty((String) jsonObject.get("first_name")));
		prepareStatement.setString(3, StringUtils.trimToEmpty((String) jsonObject.get("last_name")));
		prepareStatement.setString(4, StringUtils.trimToEmpty((String) jsonObject.get("email")));
		prepareStatement.setInt(5, Integer.parseInt(StringUtils.trimToEmpty((String) jsonObject.get("city_id"))));
		prepareStatement.setInt(6, Integer.parseInt(StringUtils.trimToEmpty((String) jsonObject.get("phone"))));
		prepareStatement.setString(7, StringUtils.trimToEmpty((String) jsonObject.get("password")));
		prepareStatement.setString(8, StringUtils.trimToEmpty((String) jsonObject.get("image")));
		int executeUpdate = prepareStatement.executeUpdate();
		try {
			return executeUpdate;
		} finally {
			prepareStatement.close();
		}
	}
}
