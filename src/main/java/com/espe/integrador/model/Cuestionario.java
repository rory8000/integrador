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
				"En la serie animada \"Los Picapiedra\", ¿Cuál es el nombre del mejor amigo de Pedro?",
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
		respuestasPregunta.add("D. Un género literario.");
		pregunta = new Pregunta(3, "El calamar es:", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Italia");
		respuestasPregunta.add("B. Brasil.");
		respuestasPregunta.add("C. Alemania.");
		respuestasPregunta.add("D. Inglaterra.");
		pregunta = new Pregunta(4, "¿Qué país ganó el Mundial de Fútbol 2002?", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Julieta.");
		respuestasPregunta.add("B. Dulcinea del Toboso.");
		respuestasPregunta.add("C. Antígona.");
		respuestasPregunta.add("D. Melibea.");
		pregunta = new Pregunta(5, "Don Quijote de la Mancha estaba enamorado de...", respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Compuestos.");
		respuestasPregunta.add("B. Simples.");
		respuestasPregunta.add("C. Primos.");
		respuestasPregunta.add("D. Fraccionarios.");
		pregunta = new Pregunta(6, "¿Cómo se llaman los números que solo son divisibles para uno y para sí mismos?",
				respuestasPregunta);
		preguntas.add(pregunta);

		respuestasPregunta = new ArrayList<>();
		respuestasPregunta.add("A. Esmeraldas y Pichincha.");
		respuestasPregunta.add("B. Manabí y Esmeraldas.");
		respuestasPregunta.add("C. Imbabura y Pichincha.");
		respuestasPregunta.add("D. Manabí y Guayas.");
		pregunta = new Pregunta(7, "La población de la Concordia es reclamada por las provincias de...",
				respuestasPregunta);
		preguntas.add(pregunta);
	}

	private static void inicializarTiempos() {
		tiempos.add(128500);
		tiempos.add(21000);
		tiempos.add(26000);
		tiempos.add(21000);
		tiempos.add(22500);
		tiempos.add(36000);
		tiempos.add(30000);
		tiempos.add(30000);

	}

}
