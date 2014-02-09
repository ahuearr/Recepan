package com.gusycorp.recepan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gusycorp.recepan.R;
import com.gusycorp.recepan.model.Receta;

public class RecetaAdapter extends ArrayAdapter<Receta> {

	public RecetaAdapter(final Context context, final int resource,
			final List<Receta> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.filareceta, null);
		}

		final Receta receta = getItem(position);
		final TextView codigoReceta = (TextView) convertView
				.findViewById(R.id.codigoReceta);
		codigoReceta.setText(receta.getCodigoReceta());
		final TextView nombreReceta = (TextView) convertView
				.findViewById(R.id.nombreReceta);
		nombreReceta.setText(receta.getNombreReceta());
		final TextView descripcionReceta = (TextView) convertView
				.findViewById(R.id.descripcionReceta);
		descripcionReceta.setText(receta.getDescripcionReceta());

		return convertView;
	}

}
