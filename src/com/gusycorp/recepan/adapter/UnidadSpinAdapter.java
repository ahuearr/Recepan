package com.gusycorp.recepan.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gusycorp.recepan.model.Unidad;

public class UnidadSpinAdapter extends ArrayAdapter<Unidad> {

	private Context context;
	private List<Unidad> unidades;

	public UnidadSpinAdapter(Context context, int textViewResourceId,
			List<Unidad> unidades) {
		super(context, textViewResourceId, unidades);
		this.context = context;
		this.unidades = unidades;
	}

	public int getCount() {
		return unidades.size();
	}

	public Unidad getItem(int position) {
		return unidades.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(unidades.get(position).getNombreUnidad());
		return label;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		label.setPadding(0, 10, 0, 10);
		label.setTextSize(20);
		label.setTextColor(Color.BLACK);
		label.setText(unidades.get(position).getNombreUnidad());

		return label;
	}
}
