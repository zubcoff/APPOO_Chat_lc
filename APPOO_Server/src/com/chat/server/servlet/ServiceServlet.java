package com.chat.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.chat.server.services.CityService;
import com.chat.server.services.CouuntryService;
import com.chat.server.services.UserService;
import com.string.StringUtils;

public class ServiceServlet extends ServerServlet {
	private static final String UTF_8 = "UTF-8";
	private static final long serialVersionUID = 1L;
	private CouuntryService couuntryService;
	private CityService cityService;
	private UserService userService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		couuntryService = new CouuntryService(getMySQLConnection());
		cityService = new CityService(getMySQLConnection());
		userService = new UserService(getMySQLConnection());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject jsonObject = getRequestJson(request, response);
			Object commandObject = jsonObject.get("command");
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding(UTF_8);
			response.setContentType("application/json");
			if (commandObject != null && commandObject instanceof String) {
				String command = StringUtils.trimToEmpty((String) commandObject);
				if (command.equalsIgnoreCase("get_countries")) {
					JSONArray countriesAsJSONArray = couuntryService.getCountriesAsJSONArray();
					out.write(getBites(countriesAsJSONArray.toJSONString()));
					out.flush();
				} else if (command.equalsIgnoreCase("get_cities")) {
					int countryId = Integer.parseInt(StringUtils.trimToEmpty((String) jsonObject.get("country_id")));
					JSONArray cities = cityService.getCitiesAsJSONArray(countryId);
					out.write(getBites(cities.toJSONString()));
					out.flush();
				} else if (command.equalsIgnoreCase("user_name_exists")) {
					String userName = StringUtils.trimToEmpty((String) jsonObject.get("user_name"));
					boolean existsUser = userService.existsUser(userName);
					out.write(getBites(String.valueOf(existsUser)));
					out.flush();
				} else if (command.equalsIgnoreCase("email_exists")) {
					String email = StringUtils.trimToEmpty((String) jsonObject.get("email"));
					boolean emailUser = userService.existsEmail(email);
					out.write(getBites(String.valueOf(emailUser)));
					out.flush();
				} else if (command.equalsIgnoreCase("get_friends")) {
					int userId = Integer.parseInt(StringUtils.trimToEmpty((String) jsonObject.get("user_id")));
					JSONArray friendsAsJSONArray = userService.getFriendsAsJSONArray(userId);
					out.write(getBites(friendsAsJSONArray.toJSONString()));
					out.flush();
				} else if (command.equalsIgnoreCase("get_user")) {
					String userName = StringUtils.trimToEmpty((String) jsonObject.get("user_name"));
					JSONObject userAsJSONObject = userService.getUserAsJSONObject(userName, false);
					out.write(getBites(userAsJSONObject.toJSONString()));
					out.flush();
				} else if (command.equalsIgnoreCase("add_user")) {
					int result = userService.addNewUser(jsonObject);
					out.write(getBites(String.valueOf(result)));
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] getBites(String string) throws Exception {
		return string.getBytes(UTF_8);
	}

	private JSONObject getRequestJson(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer jb = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}
		JSONParser jsonParser = new JSONParser();
		return (JSONObject) jsonParser.parse(jb.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
