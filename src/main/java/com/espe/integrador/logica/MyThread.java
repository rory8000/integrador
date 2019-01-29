package com.espe.integrador.logica;

import java.util.List;
import java.util.logging.Logger;

import com.espe.integrador.model.Cuestionario;
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
		List<Pregunta> preguntas = Cuestionario.getPreguntas();
		List<Integer> tiempos = Cuestionario.getTiempos();
		for (int i = 0; i < preguntas.size(); i++) {
			Thread.sleep(tiempos.get(0));
			if (exit) {
				break;
			} else {
				sessionHandler.sendAnswers(preguntas.get(i));
			}
		}

	}
}