/**
 * 
 */
function validate() {
	var ok = true;
	var p = document.getElementById("prefix").value;
	if (isNaN(p)) {
		alert("Prefix invalid!");
		document.getElementById("perror").style.display = "inline";
		ok = false;
	} else {
		document.getElementById("perror").style.display = "none";
	}
	if (!ok) {
		return false;
	}
	p = document.getElementById("cTaken").value;
	if (isNaN(p) || p < 0 || p > 120) {
		alert("Min credits invalid!");
		document.getElementById("merror").style.display = "inline";

		ok = false;
	} else {
		document.getElementById("merror").style.display = "none";
	}

	return ok;
}