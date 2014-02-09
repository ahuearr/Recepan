package com.gusycorp.recepan.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;

import com.gusycorp.recepan.model.Ingrediente;
import com.gusycorp.recepan.model.Unidad;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RecepanApplication extends Application {

	private final String TAG = "RecepanApplication";

	public List<Ingrediente> ingredienteList = new ArrayList<Ingrediente>();
	public List<Unidad> unidadList = new ArrayList<Unidad>();

	public List<Ingrediente> getIngredienteList() {
		return ingredienteList;
	}

	public void setIngredienteList(List<Ingrediente> ingredienteList) {
		this.ingredienteList = ingredienteList;
	}

	public List<Unidad> getUnidadList() {
		return unidadList;
	}

	public void setUnidadList(List<Unidad> unidadList) {
		this.unidadList = unidadList;
	}

	public void setLists() {

		Log.d(TAG, "Creando las listas");

		ParseQuery<ParseObject> queryIngrediente = ParseQuery
				.getQuery("Ingrediente");
		try {
			List<ParseObject> listaIngredientes = queryIngrediente.find();
			for (ParseObject ingrediente : listaIngredientes) {
				ingredienteList.add(new Ingrediente(ingrediente.getObjectId(),
						ingrediente.getString("d_ingrediente")));
			}
		} catch (ParseException e) {
			Log.d(TAG,
					"Error al obtener la lista de los ingredientes:"
							+ e.getMessage());
		}

		ParseQuery<ParseObject> queryUnidad = ParseQuery.getQuery("Unidad");
		try {
			List<ParseObject> listaUnidades = queryUnidad.find();
			for (ParseObject unidad : listaUnidades) {
				unidadList.add(new Unidad(unidad.getObjectId(), unidad
						.getString("d_unidad")));
			}
		} catch (ParseException e) {
			Log.d(TAG,
					"Error al obtener la lista de las unidades:"
							+ e.getMessage());
		}
	}

}
