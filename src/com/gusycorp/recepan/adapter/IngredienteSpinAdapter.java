package com.gusycorp.recepan.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gusycorp.recepan.model.Ingrediente;

public class IngredienteSpinAdapter extends ArrayAdapter<Ingrediente> {

	private Context context;
	private List<Ingrediente> ingredientes;

	public IngredienteSpinAdapter(Context context, int textViewResourceId,
			List<Ingrediente> ingredientes) {
		super(context, textViewResourceId, ingredientes);
		this.context = context;
		this.ingredientes = ingredientes;
	}

	public int getCount() {
		return ingredientes.size();
	}

	public Ingrediente getItem(int position) {
		return ingredientes.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(ingredientes.get(position).getNombreIngrediente());
		return label;
	}

	// @Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		label.setPadding(0, 10, 0, 10);
		label.setTextSize(20);
		label.setTextColor(Color.BLACK);
		label.setText(ingredientes.get(position).getNombreIngrediente());

		return label;
	}
}
