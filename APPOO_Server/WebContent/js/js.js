var xmlhttp;

function initJs() {
	if (window.XMLHttpRequest) {
		// for IE7+, Firefox, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		// for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Your browser does not support XMLHTTP!");
		return false;
	}
}

function getCountries() {
	var json = JSON.stringify({
		"command" : "get_countries"
	});

	xmlhttp.open("POST", "/APPOO_Server/service_servlet", true);
	xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState === 4) {
			var countries = JSON.parse(xmlhttp.responseText);
			setCountries(countries);
		}
	};
	xmlhttp.send(json);
	return false;
}

function setCountries(countries) {
	var i;
	var countrySelect = document.getElementById("country");
	crealSelect(countrySelect);
	countrySelect.options[countrySelect.options.length] = new Option('', '');
	for (i = 0; i < countries.length; i++) {
		countrySelect.options[countrySelect.options.length] = new Option(
				countries[i].name, countries[i].id);
	}
}

function getCities() {
	var countrySelect = document.getElementById("country");
	var json = JSON.stringify({
		"command" : "get_cities",
		"country_id" : countrySelect.options[countrySelect.selectedIndex].value
	});

	xmlhttp.open("POST", "/APPOO_Server/service_servlet", true);
	xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState === 4) {
			var cities = JSON.parse(xmlhttp.responseText);
			setCities(cities);
		}
	};
	xmlhttp.send(json);
	return false;
}

function setCities(cities) {
	var i;
	var citySelect = document.getElementById("city");
	crealSelect(citySelect);
	citySelect.options[citySelect.options.length] = new Option('', '');
	for (i = 0; i < cities.length; i++) {
		citySelect.options[citySelect.options.length] = new Option(
				cities[i].name, cities[i].id);
	}
}

function crealSelect(select) {
	select.innerHTML = "";
}

function existsUser() {
	var userName = document.getElementById("user_name");
	var json = JSON.stringify({
		"command" : "user_name_exists",
		"user_name" : userName.value
	});

	xmlhttp.open("POST", "/APPOO_Server/service_servlet", true);
	xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState === 4) {
			if (xmlhttp.responseText == 'true') {
				userName.style.backgroundColor = "red";
			} else {
				userName.style.backgroundColor = "white";
			}
		}
	};
	xmlhttp.send(json);
	return false;
}

function existsEmail() {
	var email = document.getElementById("email");
	var json = JSON.stringify({
		"command" : "email_exists",
		"email" : email.value
	});

	xmlhttp.open("POST", "/APPOO_Server/service_servlet", true);
	xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState === 4) {
			if (xmlhttp.responseText == 'true') {
				email.style.backgroundColor = "red";
			} else {
				email.style.backgroundColor = "white";
			}
		}
	};
	xmlhttp.send(json);
	return false;
}

function registerNewUser() {
	var user_name = document.getElementById("user_name");
	var password = document.getElementById("password");
	var first_name = document.getElementById("first_name");
	var last_name = document.getElementById("last_name");
	var city = document.getElementById("city");
	var email = document.getElementById("email");
	var phone = document.getElementById("phone");
	var json = JSON.stringify({
		"command" : "add_user",
		"user_name" : user_name.value,
		"password" : password.value,
		"first_name" : first_name.value,
		"last_name" : last_name.value,
		"city_id" : city.value,
		"email" : email.value,
		"phone" : phone.value
	});
	xmlhttp.open("POST", "/APPOO_Server/service_servlet", true);
	xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState === 4) {
			if (xmlhttp.responseText == 'true') {
				email.style.backgroundColor = "red";
			} else {
				email.style.backgroundColor = "white";
			}
		}
	};
	xmlhttp.send(json);
	return false;
}

function checkPass() {
	var password = document.getElementById("password");
	if (password.value.length < 6) {
		password.style.backgroundColor = "red";
		return false;
	} else {
		password.style.backgroundColor = "white";
		return true;
	}
}

function checkRePass() {
	var password = document.getElementById("password");
	var re_password = document.getElementById("re_password");
	if (password.value != re_password.value) {
		re_password.style.backgroundColor = "red";
		return false;
	} else {
		re_password.style.backgroundColor = "white";
		return true;
	}
}