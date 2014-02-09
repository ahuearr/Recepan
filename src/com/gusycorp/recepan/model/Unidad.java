package com.gusycorp.recepan.model;

import java.io.Serializable;

public class Unidad implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigoUnidad;
	private String nombreUnidad;

	public Unidad(String codigoUnidad, String nombreUnidad) {
		setCodigoUnidad(codigoUnidad);
		setNombreUnidad(nombreUnidad);
	}

	public String getCodigoUnidad() {
		return codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

}
