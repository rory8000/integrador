package com.espe.integrador.logica;

import java.util.List;
import java.util.logging.Logger;

import com.espe.integrador.model.Cuestionario;
import com.espe.integrador.model.Estadistica;
import com.espe.integrador.model.Pregunta;
import com.espe.integrador.websockets.DeviceSessionHandler;

public class MyThread extends Thread {

	private final Logger logger = Logger.getLogger(MyThread.class.getName());

	private DeviceSessionHandler sessionHandler;
	private Acciones acciones;

	private volatile boolean exit;
	private volatile boolean play;


	public MyThread(DeviceSessionHandler sessionHandler, Acciones acciones) {
		this.sessionHandler = sessionHandler;
		this.acciones = acciones;
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

	private void reproducir() throws Exception {
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

	private void play() throws Exception {
		List<Pregunta> preguntas = Cuestionario.getPreguntas();
		List<Integer> tiempos = Cuestionario.getTiempos();
		for (int i = 0; i < preguntas.size(); i++) {
			Thread.sleep(tiempos.get(i));
			if (exit) {
				break;
			} else {
				sessionHandler.sendAnswers(preguntas.get(i));
			}
		}

		enviarResultados();

	}

	private void enviarResultados() throws Exception {
		List<Estadistica> lista = acciones.enviarEstadisticas();
		sessionHandler.sendEstadisticas(lista);

	}
}