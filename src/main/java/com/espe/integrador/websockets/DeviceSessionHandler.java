package com.espe.integrador.websockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import com.espe.integrador.model.Estadistica;
import com.espe.integrador.model.Pregunta;
import com.espe.integrador.model.UsuarioSesion;

@ApplicationScoped
public class DeviceSessionHandler {

	private final Logger logger = Logger.getLogger(DeviceSessionHandler.class.getName());

	private final Set<Session> sessions = new HashSet<>();
	private final Map<String, UsuarioSesion> usuarios = new HashMap<>();

	public void addSession(Session session) {
		sessions.add(session);
	}

	public void removeSession(Session session) {
		sessions.remove(session);
		if (usuarios.containsKey(session.getId())) {
			usuarios.remove(session.getId());
		}
	}

	public Map<String, UsuarioSesion> getUsuarios() {
		return usuarios;
	}

	private void sendToAllConnectedSessions(JsonObject message) {
		for (Session session : sessions) {
			logger.info("ENVIANDO A " + session.getId());
			sendToSession(session, message);
		}
	}

	private void sendToSession(Session session, JsonObject message) {
		try {
			session.getBasicRemote().sendText(message.toString());
		} catch (IOException ex) {
			sessions.remove(session);
			Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendAnswers(Pregunta pregunta) {
		JsonProvider provider = JsonProvider.provider();

		final JsonArrayBuilder respuestas = Json.createArrayBuilder();
		for (final String respuesta : pregunta.getRespuestas()) {
			respuestas.add(respuesta);
		}

		JsonObject updateDevMessage = provider.createObjectBuilder()
				.add("action", "pregunta")
				.add("id", pregunta.getId())
				.add("pregunta", pregunta.getPregunta())
				.add("respuestas", respuestas)
				.build();
		sendToAllConnectedSessions(updateDevMessage);
	}

	public void agregarUsuario(String sessionId, String name) {
		UsuarioSesion usuario = new UsuarioSesion();
		usuario.setName(name);
		usuario.setId(sessionId);
		usuarios.put(sessionId, usuario);
	}

	public void sendEstadisticas(List<Estadistica> lista) {
		JsonProvider provider = JsonProvider.provider();

		final JsonArrayBuilder listaArrayBuilder = Json.createArrayBuilder();
		for (final Estadistica estadistica : lista) {
			JsonObject updateDevMessage = provider.createObjectBuilder()
					.add("usuario", estadistica.getNombreUsuario())
					.add("correctas", estadistica.getPreguntasCorrectas())
					.add("premio", estadistica.getPremio())
					.build();
			
			listaArrayBuilder.add(updateDevMessage);
		}

		JsonObject updateDevMessage = provider.createObjectBuilder()
				.add("action", "estadisticas")
				.add("datos", listaArrayBuilder)
				.build();
		sendToAllConnectedSessions(updateDevMessage);
	}

}
