function validacion() {
    var valido = true;
    if(validarNombre()==false){
        alert("No se ha introducido un nombre con caracteres válidos");
        valido=false;
    }else if(validarApellidos()==false){
        alert("No se han introducido mínimo dos apellidos o lleva caracteres inválidos");
        valido=false;
    } else if (validarCorreo() == false) {
        alert("No se ha introducido un correo válido");
        valido = false;
    } else if (validarContrasena() == false) {
        alert("Introduce mínimo 4 caracteres");
        valido = false;
    }else if(validarIgual()==false){
        alert("Las contraseñas no coinciden");
        valido=false;
    }
    return valido;
}

function validarNombre() {
    var nombre = document.getElementById("nombre").value;
    if (!(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$/.test(nombre))) {
        return false;
    }
    return true;
}

function validarApellidos() {
    var apellidos = document.getElementById("apellidos").value;
    if (!(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+[\s][a-zA-ZñÑáéíóúÁÉÍÓÚ\s]{1,}$/.test(apellidos))) {
        return false;
    }
    return true;
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

function validarIgual() {
    var contrasena = document.getElementById("contrasena").value;
    var rep_contrasena = document.getElementById("rep_contrasena").value;
    if (contrasena != rep_contrasena) {//Comparación de contraseñas
        return false;
    }
    return true;
}

document.getElementById("form").onsubmit = validacion;
