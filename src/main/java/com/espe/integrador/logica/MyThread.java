package com.espe.integrador.logica;

import java.util.logging.Logger;

import com.espe.integrador.model.Pregunta;
import com.espe.integrador.websockets.DeviceSessionHandler;
import com.espe.integrador.websockets.DeviceWebSocketServer;

public class MyThread extends Thread {

	private final Logger logger = Logger.getLogger(DeviceWebSocketServer.class.getName());

	private DeviceSessionHandler sessionHandler;

	private volatile boolean exit;
	private volatile boolean play;

	public MyThread(DeviceSessionHandler sessionHandler) {
		this.sessionHandler = sessionHandler;
	}

	public void run() {
		try {
			logger.info("HILO INICIADO");
			while (!exit) {
				reproducir();
			}
			logger.info("HILO DETENIDO");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reproducir() throws InterruptedException {
		if (play) {
			logger.info("***************EJECUTANDO PLAY");
			play();
		}

	}

	public void iniciarPlay() throws InterruptedException {
		play = true;
	}

	public void detenerPlay() {
		play = false;
	}

	public void detenerHilo() {
		exit = true;
	}

	private void play() throws InterruptedException {
		// Thread.sleep(128500);

		logger.info("ENVIANDO RESPUESTAS A PREGUNTA 1 ");
		sessionHandler.sendAnswers(Pregunta.getPregunta1());

		Thread.sleep(21000);

		logger.info("ENVIANDO RESPUESTAS A PREGUNTA 2");
		sessionHandler.sendAnswers(Pregunta.getPregunta2());

		Thread.sleep(26000);

		logger.info("ENVIANDO RESPUESTAS A PREGUNTA 3");
		sessionHandler.sendAnswers(Pregunta.getPregunta3());

		Thread.sleep(21000);

		logger.info("ENVIANDO RESPUESTAS A PREGUNTA 4");
		sessionHandler.sendAnswers(Pregunta.getPregunta4());

		Thread.sleep(22500);

		logger.info("ENVIANDO RESPUESTAS A PREGUNTA 5");
		sessionHandler.sendAnswers(Pregunta.getPregunta5());

		Thread.sleep(36000);

		logger.info("ENVIANDO RESPUESTAS A PREGUNTA 6");
		sessionHandler.sendAnswers(Pregunta.getPregunta6());
	}
}