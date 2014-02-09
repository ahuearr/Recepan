package com.gusycorp.recepan.model;

import java.io.Serializable;

public class Receta implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigoReceta;
	private String nombreReceta;
	private String descripcionReceta;

	public Receta(String codigoReceta, String nombreReceta,
			String descripcionReceta) {
		setCodigoReceta(codigoReceta);
		setNombreReceta(nombreReceta);
		setDescripcionReceta(descripcionReceta);
	}

	public String getCodigoReceta() {
		return codigoReceta;
	}

	public void setCodigoReceta(String codigoReceta) {
		this.codigoReceta = codigoReceta;
	}

	public String getNombreReceta() {
		return nombreReceta;
	}

	public void setNombreReceta(String nombreReceta) {
		this.nombreReceta = nombreReceta;
	}

	public String getDescripcionReceta() {
		return descripcionReceta;
	}

	public void setDescripcionReceta(String descripcionReceta) {
		this.descripcionReceta = descripcionReceta;
	}
}
