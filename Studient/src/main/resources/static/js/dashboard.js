
let timer;
let totalSeconds = 0;
let remainingSeconds = 0;
var pattern = new RegExp("^[0-5][0-9]:[0-5][0-9]:[0-5][0-9]$");
var miMusica = new Audio("/music/alarm_clock.mp3");

function startTimer() {
	const inputTime = document.getElementById('timeInput').value;

	totalSeconds = parseTimeToSeconds(inputTime);

	if (!isNaN(totalSeconds) && totalSeconds > 0 && pattern.test(inputTime)) {
		document.getElementById('start').disabled = true;
		document.getElementById('timeInput').disabled = true;
		remainingSeconds = totalSeconds;
		updateTimerDisplay();
		timer = setInterval(updateTimer, 1000);
	} else {
		alert('Por favor, ingrese un tiempo válido en formato HH:MM:SS.');
	}
}

function stopTimer() {
	document.getElementById('start').disabled = false;
	document.getElementById('timeInput').disabled = false;
	clearInterval(timer);
}

function resetTimer() {
	stopTimer();
	totalSeconds = 0;
	remainingSeconds = 0;
	document.getElementById('timeInput').value = '';
	updateTimerDisplay();
}

function updateTimer() {
	if (remainingSeconds > 0) {
		remainingSeconds--;
		updateTimerDisplay();
	} else {

		stopTimer();
		reproducir();
		setTimeout(function() {
			alert('¡Tiempo terminado!');
		}, 4000);
	}
}

function updateTimerDisplay() {
	const hours = Math.floor(remainingSeconds / 3600);
	const minutes = Math.floor((remainingSeconds % 3600) / 60);
	const seconds = remainingSeconds % 60;

	const formattedTime = `${padNumber(hours)}:${padNumber(minutes)}:${padNumber(seconds)}`;
	document.getElementById('timer').innerText = formattedTime;
}

function padNumber(number) {
	return number.toString().padStart(2, '0');
}

function parseTimeToSeconds(time) {
	const timeArray = time.split(':');
	const hours = parseInt(timeArray[0]) || 0;
	const minutes = parseInt(timeArray[1]) || 0;
	const seconds = parseInt(timeArray[2]) || 0;

	return hours * 3600 + minutes * 60 + seconds;
}

// La música se reproduce al terminar la sesión de estudio
function reproducir() {
	miMusica.play();
}

//Funciones para la visualización y cierre del diálogo
document.getElementById('openDialogFolder').addEventListener('click', function() {
	var dialog_name = "folder";
	var close_name = "closeDialogFolder";
	document.getElementById(dialog_name).style.display = 'flex';
	dialogVisibility(dialog_name, close_name);

});

document.getElementById('openDialogTask').addEventListener('click', function() {
	var dialog_name = "task";
	var close_name = "closeDialogTask";
	document.getElementById(dialog_name).style.display = 'flex';
	dialogVisibility(dialog_name, close_name);
});

function dialogVisibility(d_name, c_name) {
	document.getElementById(c_name).addEventListener('click', function() {
		document.getElementById(d_name).style.display = 'none';
	});

	document.getElementById(d_name).addEventListener('click', function(event) {
		if (event.target.id === d_name) {
			document.getElementById(d_name).style.display = 'none';
		}
	});
}





