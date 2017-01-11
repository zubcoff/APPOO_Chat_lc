<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>simple_light</title>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="content-type"
	content="text/html; charset=windows-1252" />
<link rel="stylesheet" type="text/css" href="style/style.css" />
<script type="text/javascript" src="js/js.js"></script>
</head>

<body onload="initJs();getCountries();">
	<div id="main">
		<div id="header">
			<div id="logo">
				<!-- class="logo_colour", allows you to change the colour of the text -->
				<h1>
					<a href="index.html">simple<span class="logo_colour">_light</span></a>
				</h1>
				<h2>Simple. Contemporary. Website Template.</h2>
			</div>
			<div id="menubar">
				<ul id="menu">
					<!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
					<li class="selected"><a href="index.html">Home</a></li>
					<li><a href="examples.html">Examples</a></li>
					<li><a href="page.html">A Page</a></li>
					<li><a href="another_page.html">Another Page</a></li>
					<li><a href="contact.html">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<div id="site_content">
			<div class="sidebar">
				<h1>Latest News</h1>
				<h4>New Website Launched</h4>
				<h5>January 1st, 2010</h5>
				<p>
					2010 sees the redesign of our website. Take a look around and let
					us know what you think.<br /> <a href="#">Read more</a>
				</p>
				<h1>Useful Links</h1>
				<ul>
					<li><a href="#">link 1</a></li>
					<li><a href="#">link 2</a></li>
					<li><a href="#">link 3</a></li>
					<li><a href="#">link 4</a></li>
				</ul>
				<h1>Search</h1>
				<form method="post" action="#" id="search_form">
					<p>
						<label for="search_field">Search</label> <input id="search_field"
							class="search" type="text" name="search_field"
							value="Enter keywords....." /> <input name="search" type="image"
							style="border: 0; margin: 0 0 -9px 5px;" src="style/search.png"
							alt="Search" title="Search" />
					</p>
				</form>
			</div>
			<div id="content">
				<h1>Register form</h1>
				<form method="post" action="" id="registration_form" onsubmit="return registerNewUser()">
					<table>
						<tr>
							<td><label for="user_name">User name:</label></td>
							<td><input id="user_name" class="search required" type="text"
								name="user_name" onchange="existsUser()" /></td>
						</tr>

						<tr>
							<td><label for="password">Password:</label></td>
							<td><input id="password" class="search required" type="password"
								name="password" onchange="checkPass()"/></td>
						</tr>
						<tr>
							<td><label for="re_password">Re Password:</label></td>
							<td><input id="re_password" class="search required" type="password"
								name="re_password" onchange="checkRePass()"/></td>
						</tr>
						<tr>
							<td><label for="first_name">First name:</label></td>
							<td><input id="first_name" class="search" type="text"
								name="first_name" /></td>
						</tr>
						<tr>
							<td><label for="last_name">Last name:</label></td>
							<td><input id="last_name" class="search" type="text"
								name="last_name" /></td>
						</tr>
						<tr>
							<td><label for="email">Email:</label></td>
							<td><input id="email" class="search required" type="text"
								name="email" onchange="existsEmail()"/></td>
						</tr>
						<tr>
							<td><label for="country">Country:</label></td>
							<td><select id="country" class="search" name="country"
								onchange="getCities()">
							</select></td>
						</tr>
						<tr>
							<td><label for="city">City:</label></td>
							<td><select id="city" class="search" name="city">
							</select></td>
						</tr>
						<tr>
							<td><label for="phone" id="phone_label">Phone:</label></td>
							<td><input id="phone" type="text" class="search" name="phone" /></td>
						</tr>
						<tr>
							<td colspan="2"><input id="register" class="search"
								type="submit" name="register" value="Register" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				Copyright &copy; simple_light | <a
					href="http://validator.w3.org/check?uri=referer">HTML5</a> | <a
					href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a> | <a
					href="http://www.html5webtemplates.co.uk">design from
					HTML5webtemplates.co.uk</a>
			</p>
		</div>
	</div>
</body>
</html>