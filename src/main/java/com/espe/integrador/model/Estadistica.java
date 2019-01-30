package com.espe.integrador.model;

public class Estadistica {
	
	private String nombreUsuario;
	private int preguntasCorrectas;
	private int premio;
	
	
	public Estadistica(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public int getPreguntasCorrectas() {
		return preguntasCorrectas;
	}

	public void setPreguntasCorrectas(int preguntasCorrectas) {
		this.preguntasCorrectas = preguntasCorrectas;
	}

	public int getPremio() {
		return premio;
	}

	public void setPremio(int premio) {
		this.premio = premio;
	}
}
