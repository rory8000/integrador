package com.espe.integrador.logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import com.espe.integrador.model.Estadistica;

@ApplicationScoped
public class Acciones {

	private final Logger logger = Logger.getLogger(Acciones.class.getName());

	private Connection conexion;

	public void guardarRespuesta(String nombreUsuario, int codigoPregunta, int codigoRespuesta) {
		logger.info("USUARIO RESPONDIO EN PREGUNTA " + codigoPregunta + ", LA RESPUESTA " + codigoRespuesta);
		try {
			PreparedStatement st = getConnection().prepareStatement(
					"INSERT INTO respuesta_usuario ( usuario, fecha, id_respuesta,id_pregunta) VALUES (?, ?, ?, ?)");
			st.setString(1, nombreUsuario);
			st.setDate(2, new java.sql.Date(new Date().getTime()));
			st.setInt(3, codigoRespuesta);
			st.setInt(4, codigoPregunta);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarDatos() throws ClassNotFoundException, SQLException {
		PreparedStatement st = getConnection().prepareStatement("DELETE FROM respuesta_usuario");
		st.executeUpdate();
		st.close();
	}

	private Connection getConnection() throws SQLException {
		if (conexion == null || conexion.isClosed()) {
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/integrador", "postgres",
					"postgres");
		}
		return conexion;
	}

	public List<Estadistica> enviarEstadisticas() throws Exception {
		PreparedStatement st = getConnection()
				.prepareStatement("SELECT usuario, id_pregunta, id_respuesta FROM respuesta_usuario");
		ResultSet rs = st.executeQuery();
		Map<Integer, Integer> respuestas = new HashMap<>();
		Map<String, Map<Integer, Integer>> usuarios = new HashMap<>();
		while (rs.next()) {

			String nombreUsuario = rs.getString("usuario");
			int codigoPregunta = rs.getInt("id_pregunta");
			int codigoRespuesta = rs.getInt("id_respuesta");
			respuestas.put(codigoPregunta, codigoRespuesta);

			Map<Integer, Integer> mapa = usuarios.getOrDefault(nombreUsuario, new HashMap<Integer, Integer>());
			mapa.put(codigoPregunta, codigoRespuesta);
			usuarios.put(nombreUsuario, mapa);

		}

		PreparedStatement st2 = getConnection().prepareStatement("SELECT id, id_respuesta_correcta FROM catalogo_pregunta");
		ResultSet rs2 = st2.executeQuery();

		Map<Integer, Integer> mapaRespuestas = new HashMap<Integer, Integer>();
		while (rs2.next()) {
			int codigoPregunta = rs2.getInt("id");
			int codigoRespuesta = rs2.getInt("id_respuesta_correcta");
			mapaRespuestas.put(codigoPregunta, codigoRespuesta);
		}

		rs.close();
		rs2.close();
		st.close();
		st2.close();

		return calificar(mapaRespuestas, usuarios);
	}

	private List<Estadistica> calificar(Map<Integer, Integer> respuestas, Map<String, Map<Integer, Integer>> usuarios) {
		logger.info("respuestas: "+  respuestas);
		logger.info("usuarios: "+  usuarios);
		List<Estadistica> list = new ArrayList<>();
		for (Entry<String, Map<Integer, Integer>> usuario : usuarios.entrySet()) {
			int bien = 0;
			Estadistica estadistica = new Estadistica(usuario.getKey());
			for (Entry<Integer, Integer> respuestaUsuario : usuario.getValue().entrySet()) {
				int respuesta = respuestas.get(respuestaUsuario.getKey());
				if (respuesta == respuestaUsuario.getValue()) {
					bien++;
				}
				
			}
			estadistica.setPreguntasCorrectas(bien);
			list.add(estadistica);
		}
		return list;
	}

	public void cerrarConexion() {
		if (conexion != null) {
			try {
				getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
