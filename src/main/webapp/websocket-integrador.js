/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = init;
var socket = new WebSocket(
		"ws://localhost:8080/integrador-1.0-SNAPSHOT/websocket");
socket.onmessage = onMessage;

function onMessage(event) {
	var mensaje = JSON.parse(event.data);
	console.log('mensaje', mensaje)
	if (mensaje.action === "pregunta") {
		printPreguntaElement(mensaje);
	}
}

// {
// "action":"pregunta1",
// "pregunta":"",
// "respuestas":[
// "A. Arqueólogo.",
// "B. Actor.",
// "C. Albañil.",
// "D. Arquitecto."
// ]
// }

function printPreguntaElement(pregunta) {
	resetear_contenido();
	var content = document.getElementById("content");

	var tableVar = document.createElement("table");
	content.appendChild(tableVar);

	var trvar = document.createElement("tr");
	tableVar.appendChild(trvar);

	var thvar = document.createElement("th");
	thvar.innerHTML = pregunta.pregunta;
	trvar.appendChild(thvar);

	for (i = 0; i < pregunta.respuestas.length; i++) {
		var trvar = document.createElement("tr");
		tableVar.appendChild(trvar);

		var tdvar = document.createElement("td");
		trvar.appendChild(tdvar);

		var botonvar = document.createElement("button");
		// botonvar.addEventListener("click", enviarRespuesta);
		botonvar.setAttribute("onclick", "enviarRespuesta(" + pregunta.id
				+ ", " + i + ");");
		botonvar.innerHTML = pregunta.respuestas[i];
		tdvar.appendChild(botonvar);

	}
}

function enviarRespuesta(codigoPregunta, codigoRespuesta) {

	var action = {
		action : "answer",
		codigoPregunta : codigoPregunta,
		codigoRespuesta : codigoRespuesta
	};

	socket.send(JSON.stringify(action));
	resetear_contenido();
}

function stopSocket() {
	var action = {
		action : "stop"
	};

	socket.send(JSON.stringify(action));
}

function getStats() {
	var action = {
		action : "stats"
	};

	socket.send(JSON.stringify(action));
}

function playSocket() {
	var x = document.getElementById("inputNombre");
	var name = x.value

	var action = {
		action : "play",
		usuario : name
	};

	socket.send(JSON.stringify(action));
	labelStart()
}

// global variable for the player
var player;

// this function gets called when API is ready to use
function onYouTubePlayerAPIReady() {
	// create the global player from the specific iframe (#video)
	player = new YT.Player('video', {
		events : {
			// call this function when player is ready to use
			'onReady' : onPlayerReady
		}
	});
}

function onPlayerReady(event) {
	console.log('listo el video')
	// bind events
	var playButton = document.getElementById("play-button");
	playButton.addEventListener("click", function() {
		console.log('play video')
		player.playVideo();
		// Esto es para controlar el socket
		playSocket();
	});

	var pauseButton = document.getElementById("pause-button");
	pauseButton.addEventListener("click", function() {
		stopSocket();
		player.pauseVideo();

	});

}

// Inject YouTube API script
var tag = document.createElement('script');
tag.src = "https://www.youtube.com/player_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

function resetear_contenido() {
	var myNode = document.getElementById("content");
	while (myNode.firstChild) {
		myNode.removeChild(myNode.firstChild);
	}
}

function labelStart() {
	var x = document.getElementById("inputNombre");
	var name = x.value
	x.style.display = "none";

	var x = document.getElementById("labelNombre");
	x.innerHTML = 'Bienvenido ' + name
}

function labelsInit() {
	var x = document.getElementById("inputNombre");
	x.style.display = "block";

	var x = document.getElementById("labelNombre");
	x.innerHTML = 'Ingresa tu nombre:'
}

function init() {
	labelsInit()
}