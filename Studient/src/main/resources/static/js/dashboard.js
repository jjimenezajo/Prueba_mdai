
let timer;
let totalSeconds = 0;
let remainingSeconds = 0;

function startTimer() {
	const inputTime = document.getElementById('timeInput').value;
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
