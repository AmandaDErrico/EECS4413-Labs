/**
 * 
 */
function validate() {
	var ok = true;
	var p = document.getElementById("principal").value;
	if (isNaN(p) || p <= 0) {
		alert("Principal invalid!");
		document.getElementById("perror").style.display = "inline";
		ok = false;
	} else {
		document.getElementById("perror").style.display = "none";
	}
	if (!ok) {
		return false;
	}
	p = document.getElementById("interest").value;
	if (isNaN(p) || p <= 0 || p >= 100) {
		alert("Interest invalid. Must be in [0,100].");
		// document.getElementById("principalText").innerHTML = "Principal";
		// document.getElementById("periodText").innerHTML = "Period";
		document.getElementById("ierror").style.display = "inline";

		ok = false;
	} else {
		document.getElementById("ierror").style.display = "none";
	}
	if (!ok) {
		return false;
	}
	p = document.getElementById("payment").value;
	if (isNaN(p) || p <= 0 || p >= 100) {
		alert("Period invalid. Must be in [0,100].");

		// document.getElementById("interestText").innerHTML = "Interest";
		document.getElementById("p1error").style.display = "inline";
		ok = false;
	} else {
		document.getElementById("p1error").style.display = "none";
	}

	return ok;

	function doSimpleAjax(url) {
		var xhr = new XMLHttpRequest(); // prepare an HTTP request
		var data = '';// prepare the url,something like: /Hello?comm=ajax
		var prin = document.querySelector("#principal").value;// notice how I get the element 'name'
		var i = document.querySelector("#interest").value;
		var p = document.querySelector("#payment").value;
		data += "comm=ajax";

		if (prin == "") { // check if there is name in the input field/validation
			alert("Enter a valid principal");
			return false;
		}
		if (i == "") { // check if there is name in the input field/validation
			alert("Enter a valid interest");
			return false;
		}
		if (p == "") { // check if there is name in the input field/validation
			alert("Enter a valid period");
			return false;
		}
		// complete the url, something like /Hello?comm=ajax&name=John
		data += "&principal=";
		data += prin;
		data += "&interest=";
		data += i;
		data += "&payment=";
		data += p;
		console.log(data);// for debugging purposes in the browser
		xhr.open("GET", (url + "?" + data), true);// open the connection
		xhr.onreadystatechange = function() {// this is call back function,
			// check the lecture
			handler(xhr);
		};
		xhr.send();
	}

	function handler(xhr) {// this code is called for any change of the flag
		// onreadystatechange
		if ((xhr.readyState == 4) && (xhr.status == 200)) {// check the
			// response is
			// received
			var target = document.querySelector("#ajaxTarget");// change the
			// content of
			// the HTML
			target.innerHTML = xhr.responseText;
		}
	}

	// this does the same as helloAjax, but uses fetch function
	function helloFetch(url) {
		var data = '';
		var el = document.getElementById("name").value;
		data += "comm=fetch";

		if (el == "") {
			alert("Enter a string");
			return false;
		}
		data += "&name=";
		data += el;
		// fetch is simpler..the call back function is specified by "then"
		fetch(url + "?" + data).then(function(response) {// replaces
			// onreadystatechange
			return response.text()
		}).then(function(text) {// replaces "handler"
			var target = document.getElementById("fetchTarget");
			target.innerHTML = text;
		});

	}
}