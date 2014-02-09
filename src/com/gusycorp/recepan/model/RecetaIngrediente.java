package com.gusycorp.recepan.model;

import java.io.Serializable;

public class RecetaIngrediente implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigoRecetaIngrediente;
	private String codigoEjecucion;
	private String codigoIngrediente;
	private int cantidad;
	private String codigoUnidad;
	private String descripcionRecetaIngrediente;

	public RecetaIngrediente(String codigoRecetaIngrediente,
			String codigoEjecucion, String codigoIngrediente, int cantidad,
			String codigoUnidad, String descripcionRecetaIngrediente) {
		setCodigoRecetaIngrediente(codigoRecetaIngrediente);
		setCodigoEjecucion(codigoEjecucion);
		setCodigoIngrediente(codigoIngrediente);
		setCantidad(cantidad);
		setCodigoUnidad(codigoUnidad);
		setDescripcionRecetaIngrediente(descripcionRecetaIngrediente);
	}

	public RecetaIngrediente(String codigoRecetaIngrediente,
			String descripcionRecetaIngrediente) {
		setCodigoRecetaIngrediente(codigoRecetaIngrediente);
		setDescripcionRecetaIngrediente(descripcionRecetaIngrediente);
	}

	public String getCodigoRecetaIngrediente() {
		return codigoRecetaIngrediente;
	}

	public void setCodigoRecetaIngrediente(String codigoRecetaIngrediente) {
		this.codigoRecetaIngrediente = codigoRecetaIngrediente;
	}

	public String getCodigoEjecucion() {
		return codigoEjecucion;
	}

	public void setCodigoEjecucion(String codigoEjecucion) {
		this.codigoEjecucion = codigoEjecucion;
	}

	public String getCodigoIngrediente() {
		return codigoIngrediente;
	}

	public void setCodigoIngrediente(String codigoIngrediente) {
		this.codigoIngrediente = codigoIngrediente;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigoUnidad() {
		return codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getDescripcionRecetaIngrediente() {
		return descripcionRecetaIngrediente;
	}

	public void setDescripcionRecetaIngrediente(
			String descripcionRecetaIngrediente) {
		this.descripcionRecetaIngrediente = descripcionRecetaIngrediente;
	}

}
