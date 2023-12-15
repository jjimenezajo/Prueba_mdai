
window.onload = function() {
	document.getElementById('correo').value = "alvaro@gmail.com"
	document.getElementById('contrasena').value = "alvaro"
}

function validacion() {
	var valido = true;
	if (validarCorreo() == false) {
		alert("No se ha introducido un correo válido");
		valido = false;
	} else if (validarContrasena() == false) {
		alert("Introduce mínimo 4 caracteres");
		valido = false;
	}
	return valido;
}


function validarCorreo() {
	var correo = document.getElementById("correo").value;
	if (!(/\w+[@]\w+[.]\w+$/.test(correo))) {
		return false;
	}
	return true;
}

function validarContrasena() {
	var contrasena = document.getElementById("contrasena").value;
	if (!(/^.....*$/.test(contrasena))) {//Introducir mínimo 4 caracteres cualesquiera
		return false;
	}
	return true;
}




document.getElementById("form").onsubmit = validacion;
