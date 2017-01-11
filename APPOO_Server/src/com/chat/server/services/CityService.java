package com.chat.server.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.chat.server.beans.City;
import com.chat.server.db.MySQLConnection;

@SuppressWarnings("unchecked")
public class CityService extends Service {

	public CityService(MySQLConnection mySQLConnection) {
		super(mySQLConnection);
	}

	public List<City> getCities(int countryId) throws Exception {
		List<City> cities = new ArrayList<City>();

		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_cities` WHERE country_id = ? ORDER BY name ASC");
		prepareStatement.setInt(1, countryId);
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double utc = resultSet.getDouble("utc");
				cities.add(new City(id, name, utc, countryId));
			}
		} finally {
			resultSet.close();
		}
		return cities;
	}

	public JSONArray getCitiesAsJSONArray(int countryId) throws Exception {
		JSONArray cities = new JSONArray();

		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_cities` WHERE country_id = ? ORDER BY name ASC");
		prepareStatement.setInt(1, countryId);
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				JSONObject city = new JSONObject();
				city.put("id", resultSet.getInt("id"));
				city.put("name", resultSet.getString("name"));
				city.put("utc", resultSet.getDouble("utc"));
				city.put("country_id", resultSet.getInt("country_id"));
				cities.add(city);
			}
		} finally {
			resultSet.close();
		}
		return cities;
	}
}
