package com.espe.integrador.model;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {

	public int id;
	
	public String pregunta;
	
	public List<String> respuestas;

	public Pregunta(int id, String pregunta, List<String> respuestas) {
		super();
		this.id= id;
		this.pregunta = pregunta;
		this.respuestas = respuestas;
	}
	
	public static Pregunta getPregunta1() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Arque�logo.");
        respuestasPregunta1.add("B. Actor.");
        respuestasPregunta1.add("C. Alba�il.");
        respuestasPregunta1.add("D. Arquitecto.");
        return new Pregunta(0, "C�mo se conoce a la persona que interpreta un papel en el teatro, cine o televisi�n?", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta2() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Bambam.");
        respuestasPregunta1.add("B. Pablo.");
        respuestasPregunta1.add("C. Piedradura.");
        respuestasPregunta1.add("D. Rocape�a.");
        return new Pregunta(1, "En la serie animada \"Los Picapiedra\", �Cu�l es el nombre del mejor amigo de Pedro?", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta3() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Guayaquil.");
        respuestasPregunta1.add("B. Quito.");
        respuestasPregunta1.add("C. Riobamba.");
        respuestasPregunta1.add("D. Cuenca.");
        return new Pregunta(2, "La capital de la provincia ecuatoriana de Chimborazo es:", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta4() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Un molusco.");
        respuestasPregunta1.add("B. Un instrumento musical.");
        respuestasPregunta1.add("C. Una prenda de vestir.");
        respuestasPregunta1.add("D. Un g�nero literario.");
        return new Pregunta(3, "El calamar es:", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta5() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Italia");
        respuestasPregunta1.add("B. Brasil.");
        respuestasPregunta1.add("C. Alemania.");
        respuestasPregunta1.add("D. Inglaterra.");
        return new Pregunta(4, "�Qu� pa�s gan� el Mundial de F�tbol 2002?", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta6() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Julieta.");
        respuestasPregunta1.add("B. Dulcinea del Toboso.");
        respuestasPregunta1.add("C. Ant�gona.");
        respuestasPregunta1.add("D. Melibea.");
        return new Pregunta(5, "Don Quijote de la Mancha estaba enamorado de...", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta7() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Compuestos.");
        respuestasPregunta1.add("B. Simples.");
        respuestasPregunta1.add("C. Primos.");
        respuestasPregunta1.add("D. Fraccionarios.");
        return new Pregunta(6, "�C�mo se llaman los n�meros que solo son divisibles para uno y para s� mismos?", respuestasPregunta1);
	}
	
	public static Pregunta getPregunta8() {
		List<String> respuestasPregunta1 = new ArrayList<>();
        respuestasPregunta1.add("A. Esmeraldas y Pichincha.");
        respuestasPregunta1.add("B. Manab� y Esmeraldas.");
        respuestasPregunta1.add("C. Imbabura y Pichincha.");
        respuestasPregunta1.add("D. Manab� y Guayas.");
        return new Pregunta(7, "La poblaci�n de la Concordia es reclamada por las provincias de...", respuestasPregunta1);
	}

	public String getPregunta() {
		return pregunta;
	}

	public List<String> getRespuestas() {
		return respuestas;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Pregunta [id=" + id + ", pregunta=" + pregunta + "]";
	}
	
	
	
}
