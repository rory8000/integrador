package com.espe.integrador.websockets;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.espe.integrador.logica.Acciones;
import com.espe.integrador.logica.MyThread;
import com.espe.integrador.model.Estadistica;

@ServerEndpoint("/websocket")
public class WebSocketIntegrador {

	private final Logger logger = Logger.getLogger(WebSocketIntegrador.class.getName());

	@Inject
	private DeviceSessionHandler sessionHandler;

	@Inject
	private Acciones acciones;

	private MyThread myThread;

	@OnMessage
	public void onMessage(String message, Session session) throws Exception {

		try (JsonReader reader = Json.createReader(new StringReader(message))) {
			JsonObject jsonMessage = reader.readObject();
			logger.info("Mensaje: " + jsonMessage);
			String action = jsonMessage.getString("action");

			if ("register".equals(action)) {
				registerUser(session, jsonMessage.getString("usuario"));
			} else if ("play".equals(action)) {
				play(session);
			} else if ("stop".equals(action)) {
				stop();
			} else if ("answer".equals(action)) {
				guardarRespuesta(session, jsonMessage);
			} else if ("stats".equals(action)) {
				enviarEstadisticas();
			}
			logger.info("Mensaje procesado");
		}

	}

	private void enviarEstadisticas() throws Exception {
		List<Estadistica> lista = acciones.enviarEstadisticas();
		sessionHandler.sendEstadisticas(lista);
	}

	private void guardarRespuesta(Session session, JsonObject jsonMessage) {
		int codigoPregunta = jsonMessage.getInt("codigoPregunta");
		int codigoRespuesta = jsonMessage.getInt("codigoRespuesta");
		String nombreUsuario = getNombreUsuario(session);
		acciones.guardarRespuesta(nombreUsuario, codigoPregunta, codigoRespuesta);
	}

	private String getNombreUsuario(Session session) {
		return sessionHandler.getUsuarios().get(session.getId()).getName();
	}

	private void registerUser(Session session, String usuario) {
		sessionHandler.agregarUsuario(session.getId(), usuario);
	}

	private void stop() {
		logger.info("DETENIENDO VIDEO");
		myThread.detenerPlay();
	}

	private void play(Session session) throws Exception {
		logger.info("INICIANDO VIDEO");
		acciones.eliminarDatos();
		myThread.iniciarPlay();
	}

	@OnOpen
	public void open(Session session) throws SQLException {
		logger.info("ABRIENDO SESSION");
		myThread = new MyThread(sessionHandler);
		myThread.start();
		sessionHandler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		logger.info("CERRANDO SESSION");
		myThread.detenerHilo();
		acciones.cerrarConexion();
		sessionHandler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
		logger.log(Level.SEVERE, null, error);
	}

}
