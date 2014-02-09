package com.gusycorp.recepan.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gusycorp.recepan.R;
import com.gusycorp.recepan.adapter.RecetaAdapter;
import com.gusycorp.recepan.application.RecepanApplication;
import com.gusycorp.recepan.model.Receta;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ListaRecetasActivity extends ListActivity implements
		OnClickListener {

	private final String TAG = "ListaRecetasActivity";
	private RecetaAdapter recetaAdapter;
	List<Receta> recetas = new ArrayList<Receta>();

	private RecepanApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_recetas);

		Parse.initialize(this, "psxoBg3xpRdFZ0JFdm6WJQ9yanUvb3ckQlijpQIA",
				"aCQjpITwV3f6CWppcVKMa8EZacNHCePH7CDCEPwN");

		Log.d(TAG, "Empezando lista de recetas");

		app = (RecepanApplication) getApplication();
		if (app.getIngredienteList().size() == 0
				|| app.getUnidadList().size() == 0) {
			Log.d(TAG, "No hay lista de ingredientes o unidades");
			app.setLists();
		}

		Log.d(TAG, "Listas cargadas");

		final ImageButton nuevaReceta = (ImageButton) findViewById(R.id.boton_nueva);
		nuevaReceta.setOnClickListener(this);
		final ImageButton buscaReceta = (ImageButton) findViewById(R.id.boton_busqueda);
		// buscaReceta.setOnClickListener(this);

		recetaAdapter = new RecetaAdapter(this, R.layout.filareceta, recetas);
		setListAdapter(recetaAdapter);

		obtenerRecetas();

		recetaAdapter.notifyDataSetChanged();

		final ListView lista = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(lista);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_recetas, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.boton_nueva:
			startActivity(new Intent(this, RecetaActivity.class));
			finish();
			break;
		case R.id.boton_busqueda:
			startActivity(new Intent(this, BusquedaRecetasActivity.class));
			finish();
			break;
		}
	}

	public void obtenerRecetas() {
		Log.d(TAG, "Obteniendo recetas");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Receta");
		try {
			List<ParseObject> listaRecetas = query.find();
			for (ParseObject receta : listaRecetas) {
				recetas.add(new Receta(receta.getObjectId(), receta
						.getString("d_nombre"), receta
						.getString("d_descripcion")));
			}
		} catch (ParseException e) {
			Log.d(TAG, "Error al obtener recetas:" + e.getMessage());
		}
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		Receta receta = (Receta) l.getAdapter().getItem(position);
		Log.d(TAG, receta.getCodigoReceta() + "");
		Log.d(TAG, receta.getNombreReceta());
		Log.d(TAG, receta.getDescripcionReceta());
		Intent intent = new Intent(this, RecetaActivity.class);
		intent.putExtra("codigoReceta", receta.getCodigoReceta());
		intent.putExtra("nombreReceta", receta.getNombreReceta());
		intent.putExtra("descripcionReceta", receta.getDescripcionReceta());
		startActivity(intent);
		finish();
	}
}
