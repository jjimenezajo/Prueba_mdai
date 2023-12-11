
let timer;
let totalSeconds = 0;
let remainingSeconds = 0;
var miMusica = new Audio("/music/alarm_clock.mp3")

function startTimer() {
	const inputTime = document.getElementById('timeInput').value;
	document.getElementById('start').disabled = true;
	document.getElementById('timeInput').disabled = true;
	totalSeconds = parseTimeToSeconds(inputTime);

	if (!isNaN(totalSeconds) && totalSeconds > 0) {
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
		reproducir();
		stopTimer();
		alert('¡Tiempo terminado!');
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
document.getElementById('openDialog').addEventListener('click', function() {
	document.getElementById('overlay').style.display = 'flex';
});

document.getElementById('closeDialog').addEventListener('click', function() {
	document.getElementById('overlay').style.display = 'none';
});

document.getElementById('overlay').addEventListener('click', function(event) {
	if (event.target.id === 'overlay') {
		document.getElementById('overlay').style.display = 'none';
	}
});
