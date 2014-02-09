package com.gusycorp.recepan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gusycorp.recepan.R;
import com.gusycorp.recepan.model.RecetaEjecucion;
import com.parse.ParseImageView;

public class RecetaEjecucionAdapter extends ArrayAdapter<RecetaEjecucion> {

	public RecetaEjecucionAdapter(final Context context, final int resource,
			final List<RecetaEjecucion> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.filarecetaejecucion, null);
		}

		final RecetaEjecucion recetaEjecucion = getItem(position);
		final TextView codigoRecetaEjecucion = (TextView) convertView
				.findViewById(R.id.codigoRecetaEjecucion);
		codigoRecetaEjecucion.setText(recetaEjecucion
				.getCodigoRecetaEjecucion());
		final TextView descripcionRecetaEjecucion = (TextView) convertView
				.findViewById(R.id.descripcionRecetaEjecucion);
		descripcionRecetaEjecucion.setText(recetaEjecucion
				.getDescripcionRecetaEjecucion());
		final ParseImageView fotoRecetaEjecucion = (ParseImageView) convertView
				.findViewById(R.id.fotoRecetaEjecucion);
		fotoRecetaEjecucion.setParseFile(recetaEjecucion
				.getFotoRecetaEjecucion());
		return convertView;
	}
}