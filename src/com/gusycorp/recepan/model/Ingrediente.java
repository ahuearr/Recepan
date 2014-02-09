package com.gusycorp.recepan.model;

import java.io.Serializable;

public class Ingrediente implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigoIngrediente;
	private String nombreIngrediente;

	public Ingrediente(String codigoIngrediente, String nombreIngrediente) {
		setCodigoIngrediente(codigoIngrediente);
		setNombreIngrediente(nombreIngrediente);
	}

	public String getCodigoIngrediente() {
		return codigoIngrediente;
	}

	public void setCodigoIngrediente(String codigoIngrediente) {
		this.codigoIngrediente = codigoIngrediente;
	}

	public String getNombreIngrediente() {
		return nombreIngrediente;
	}

	public void setNombreIngrediente(String nombreIngrediente) {
		this.nombreIngrediente = nombreIngrediente;
	}

}
