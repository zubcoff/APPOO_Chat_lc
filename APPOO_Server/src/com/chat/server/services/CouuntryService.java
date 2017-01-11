package com.chat.server.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.chat.server.beans.Country;
import com.chat.server.db.MySQLConnection;

@SuppressWarnings("unchecked")
public class CouuntryService extends Service {

	public CouuntryService(MySQLConnection mySQLConnection) {
		super(mySQLConnection);
	}

	public List<Country> getCountries() throws Exception {
		List<Country> countries = new ArrayList<Country>();

		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_countries` ORDER BY name ASC");
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String code = resultSet.getString("code");
				String iso = resultSet.getString("iso");
				String flag = resultSet.getString("flag");
				countries.add(new Country(id, name, code, iso, flag));
			}
		} finally {
			resultSet.close();
		}
		return countries;
	}

	public JSONArray getCountriesAsJSONArray() throws Exception {
		JSONArray countries = new JSONArray();

		Connection connection = getMySQLConnection().getConnection();
		PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM `chat_countries` ORDER BY name ASC");
		ResultSet resultSet = prepareStatement.executeQuery();
		try {
			while (resultSet.next()) {
				JSONObject country = new JSONObject();
				country.put("id", resultSet.getInt("id"));
				country.put("name", resultSet.getString("name"));
				country.put("code", resultSet.getString("code"));
				country.put("iso", resultSet.getString("iso"));
				country.put("flag", resultSet.getString("flag"));
				countries.add(country);
			}
		} finally {
			resultSet.close();
		}
		return countries;
	}
}
