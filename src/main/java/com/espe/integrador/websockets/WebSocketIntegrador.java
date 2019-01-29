package com.espe.integrador.websockets;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

import com.espe.integrador.logica.MyThread;

@ServerEndpoint("/websocket")
public class WebSocketIntegrador {

	private final Logger logger = Logger.getLogger(DeviceWebSocketServer.class.getName());

	@Inject
	private DeviceSessionHandler sessionHandler;

	private MyThread myThread;

	private Connection connection;

	private String usuario;

	@OnMessage
	public void onMessage(String message, Session session) throws Exception {

		try (JsonReader reader = Json.createReader(new StringReader(message))) {
			JsonObject jsonMessage = reader.readObject();
			logger.info("Mensaje: " + jsonMessage);
			if ("play".equals(jsonMessage.getString("action"))) {
				this.usuario = jsonMessage.getString("usuario");
				play();
			} else if ("stop".equals(jsonMessage.getString("action"))) {
				stop();
			} else if ("answer".equals(jsonMessage.getString("action"))) {
				int codigoPregunta = jsonMessage.getInt("codigoPregunta");
				int codigoRespuesta = jsonMessage.getInt("codigoRespuesta");
				guardarRespuesta(codigoPregunta, codigoRespuesta);
			} else if ("stats".equals(jsonMessage.getString("action"))) {
				enviarEstadisticas();
			}
			logger.info("Mensaje procesado");
		}

	}

	private void guardarRespuesta(int codigoPregunta, int codigoRespuesta) {
		logger.info("USUARIO RESPONDIO EN PREGUNTA " + codigoPregunta + ", LA RESPUESTA " + codigoRespuesta);
		try {
			Class.forName("org.postgresql.Driver");
			PreparedStatement st = connection.prepareStatement(
					"INSERT INTO respuesta_usuario ( usuario, fecha, id_respuesta,id_pregunta) VALUES (?, ?, ?, ?)");
			st.setString(1, this.usuario);
			st.setDate(2, new java.sql.Date(new Date().getTime()));
			st.setInt(3, codigoRespuesta);
			st.setInt(4, codigoPregunta);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void enviarEstadisticas() throws Exception {
		Class.forName("org.postgresql.Driver");
		PreparedStatement st = connection
				.prepareStatement("SELECT id_pregunta, id_respuesta FROM respuesta_usuario where usuario = ?");
		st.setString(1, this.usuario);

		System.out.println("BUSCANDO " + this.usuario);
		ResultSet rs = st.executeQuery();
		Map<Integer, Integer> res = new HashMap<>();
		while (rs.next()) {
			int codigoPregunta = rs.getInt("id_pregunta");
			int codigoRespuesta = rs.getInt("id_respuesta");
			res.put(codigoPregunta, codigoRespuesta);
			System.out.println(codigoPregunta);
			System.out.println(codigoRespuesta);
		}

		calificar(res, res);

		st.close();
	}

	private void calificar(Map<Integer, Integer> respuestas, Map<Integer, Integer> correctas) {
		int bien = 0;
		for (Entry<Integer, Integer> correcta : respuestas.entrySet()) {
			if (respuestas.containsKey(correcta.getKey())) {
				int respuesta = respuestas.get(correcta.getKey());
				if (respuesta == correcta.getValue()) {
					bien++;
				}
			}
		}

	}

	private void stop() {
		logger.info("DETENIENDO VIDEO");
		myThread.detenerPlay();
	}

	private void play() throws Exception {
		logger.info("INICIANDO VIDEO");
		eliminar_datos();
		myThread.iniciarPlay();
	}

	private void eliminar_datos() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		PreparedStatement st = connection.prepareStatement("DELETE FROM respuesta_usuario where usuario = ?");
		st.setString(1, this.usuario);
		st.executeUpdate();
		st.close();
	}

	@OnOpen
	public void open(Session session) throws SQLException {
		logger.info("ABRIENDO SESSION");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/integrador", "postgres", "postgres");
		myThread = new MyThread(sessionHandler);
		myThread.start();
		sessionHandler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		logger.info("CERRANDO SESSION");
		myThread.detenerHilo();
		sessionHandler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
		logger.log(Level.SEVERE, null, error);
	}

}
