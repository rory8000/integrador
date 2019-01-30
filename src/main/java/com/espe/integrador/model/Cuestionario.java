package com.espe.integrador.model;

import java.util.ArrayList;
import java.util.List;

public class Cuestionario {

	private static List<Pregunta> preguntas;
	private static List<Integer> tiempos;

	static {
		inicializarPreguntas();
		inicializarTiempos();
	}

	private static void inicializarPreguntas() {
		preguntas = new ArrayList<Pregunta>();

		Pregunta pregunta = null;
		List<String> respuestasPregunta = null;

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Arqueólogo.");
		respuestasPregunta.add("B. Actor.");
		respuestasPregunta.add("C. Albañil.");
		respuestasPregunta.add("D. Arquitecto.");
		pregunta = new Pregunta(0,
				"Cómo se conoce a la persona que interpreta un papel en el teatro, cine o televisión?",
				respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Bambam.");
		respuestasPregunta.add("B. Pablo.");
		respuestasPregunta.add("C. Piedradura.");
		respuestasPregunta.add("D. Rocapeña.");
		pregunta = new Pregunta(1,
				"En la serie animada \"Los Picapiedra\", �Cu�l es el nombre del mejor amigo de Pedro?",
				respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Guayaquil.");
		respuestasPregunta.add("B. Quito.");
		respuestasPregunta.add("C. Riobamba.");
		respuestasPregunta.add("D. Cuenca.");
		pregunta = new Pregunta(2, "La capital de la provincia ecuatoriana de Chimborazo es:", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Un molusco.");
		respuestasPregunta.add("B. Un instrumento musical.");
		respuestasPregunta.add("C. Una prenda de vestir.");
		respuestasPregunta.add("D. Un g�nero literario.");
		pregunta = new Pregunta(3, "El calamar es:", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Italia");
		respuestasPregunta.add("B. Brasil.");
		respuestasPregunta.add("C. Alemania.");
		respuestasPregunta.add("D. Inglaterra.");
		pregunta = new Pregunta(4, "�Qu� pa�s gan� el Mundial de F�tbol 2002?", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Julieta.");
		respuestasPregunta.add("B. Dulcinea del Toboso.");
		respuestasPregunta.add("C. Ant�gona.");
		respuestasPregunta.add("D. Melibea.");
		pregunta = new Pregunta(5, "Don Quijote de la Mancha estaba enamorado de...", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Compuestos.");
		respuestasPregunta.add("B. Simples.");
		respuestasPregunta.add("C. Primos.");
		respuestasPregunta.add("D. Fraccionarios.");
		pregunta = new Pregunta(6, "�C�mo se llaman los n�meros que solo son divisibles para uno y para s� mismos?",
				respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Esmeraldas y Pichincha.");
		respuestasPregunta.add("B. Manab� y Esmeraldas.");
		respuestasPregunta.add("C. Imbabura y Pichincha.");
		respuestasPregunta.add("D. Manab� y Guayas.");
		pregunta = new Pregunta(7, "La poblaci�n de la Concordia es reclamada por las provincias de...",
				respuestasPregunta);
		preguntas.add(pregunta);
	}

	private static void inicializarTiempos() {
		tiempos = new ArrayList<Integer>();
		tiempos.add(0);
		tiempos.add(10000);
		tiempos.add(26000);
		tiempos.add(21000);
		tiempos.add(22500);
		tiempos.add(36000);
		tiempos.add(30000);
		tiempos.add(30000);

	}

	public static List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public static List<Integer> getTiempos() {
		return tiempos;
	}
	
}
