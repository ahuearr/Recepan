package com.gusycorp.recepan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gusycorp.recepan.R;
import com.gusycorp.recepan.model.RecetaIngrediente;

public class RecetaIngredienteAdapter extends ArrayAdapter<RecetaIngrediente> {

	private String TAG = "RecetaIngredienteAdapter";

	public RecetaIngredienteAdapter(final Context context, final int resource,
			final List<RecetaIngrediente> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.filarecetaingrediente, null);
		}

		// En la lista se debe mostrar los nombres de ingredientes y unidades y
		// no sus codigos
		String ingrediente = "";
		final RecetaIngrediente recetaIngrediente = getItem(position);

		final TextView codigoRecetaIngrediente = (TextView) convertView
				.findViewById(R.id.codigoRecetaIngrediente);
		codigoRecetaIngrediente.setText(recetaIngrediente
				.getCodigoRecetaIngrediente());
		final TextView ingredienteText = (TextView) convertView
				.findViewById(R.id.ingrediente);
		ingredienteText.setText(recetaIngrediente
				.getDescripcionRecetaIngrediente());
		return convertView;
	}
}