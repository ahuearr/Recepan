package com.gusycorp.recepan.model;

import java.io.Serializable;

import com.parse.ParseFile;

public class RecetaEjecucion implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigoRecetaEjecucion;
	private String codigoReceta;
	private String descripcionRecetaEjecucion;
	private ParseFile fotoRecetaEjecucion;

	public RecetaEjecucion(String codigoRecetaEjecucion, String codigoReceta,
			String descripcionRecetaEjecucion, ParseFile fotoRecetaEjecucion) {
		setCodigoRecetaEjecucion(codigoRecetaEjecucion);
		setCodigoReceta(codigoReceta);
		setDescripcionRecetaEjecucion(descripcionRecetaEjecucion);
		setFotoRecetaEjecucion(fotoRecetaEjecucion);
	}

	public String getCodigoRecetaEjecucion() {
		return codigoRecetaEjecucion;
	}

	public void setCodigoRecetaEjecucion(String codigoRecetaEjecucion) {
		this.codigoRecetaEjecucion = codigoRecetaEjecucion;
	}

	public String getCodigoReceta() {
		return codigoReceta;
	}

	public void setCodigoReceta(String codigoReceta) {
		this.codigoReceta = codigoReceta;
	}

	public String getDescripcionRecetaEjecucion() {
		return descripcionRecetaEjecucion;
	}

	public void setDescripcionRecetaEjecucion(String descripcionRecetaEjecucion) {
		this.descripcionRecetaEjecucion = descripcionRecetaEjecucion;
	}

	public ParseFile getFotoRecetaEjecucion() {
		return fotoRecetaEjecucion;
	}

	public void setFotoRecetaEjecucion(ParseFile fotoRecetaEjecucion) {
		this.fotoRecetaEjecucion = fotoRecetaEjecucion;
	}

}
