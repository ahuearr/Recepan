package com.gusycorp.recepan.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gusycorp.recepan.R;
import com.gusycorp.recepan.adapter.RecetaEjecucionAdapter;
import com.gusycorp.recepan.model.RecetaEjecucion;
import com.parse.DeleteCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class RecetaActivity extends ListActivity implements OnClickListener {

	private final String TAG = "RecetaActivity";
	private RecetaEjecucionAdapter recetaEjecucionAdapter;
	List<RecetaEjecucion> recetaEjecuciones = new ArrayList<RecetaEjecucion>();

	private String codigoReceta = "";
	EditText nombreReceta;
	EditText descripcionReceta;
	Button borrarReceta;
	Button anadirEjecucion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receta);

		Parse.initialize(this, "psxoBg3xpRdFZ0JFdm6WJQ9yanUvb3ckQlijpQIA",
				"aCQjpITwV3f6CWppcVKMa8EZacNHCePH7CDCEPwN");

		final ImageButton nuevaRecetaEjecucion = (ImageButton) findViewById(R.id.boton_nueva);
		nuevaRecetaEjecucion.setOnClickListener(this);
		final ImageButton buscaReceta = (ImageButton) findViewById(R.id.boton_busqueda);
		// buscaReceta.setOnClickListener(this);
		nombreReceta = (EditText) findViewById(R.id.nombreReceta);
		descripcionReceta = (EditText) findViewById(R.id.descripcionReceta);
		final Button grabarReceta = (Button) findViewById(R.id.boton_grabar_receta);
		grabarReceta.setOnClickListener(this);
		borrarReceta = (Button) findViewById(R.id.boton_borrar_receta);
		borrarReceta.setOnClickListener(this);
		anadirEjecucion = (Button) findViewById(R.id.boton_anadir_ejecucion);
		anadirEjecucion.setOnClickListener(this);

		if (getIntent().getExtras() != null) {
			Log.d(TAG,
					"Receta existente:"
							+ getIntent().getExtras().getString("codigoReceta"));
			codigoReceta = getIntent().getExtras().getString("codigoReceta");
			nombreReceta.setText(getIntent().getExtras().getString(
					"nombreReceta"));
			descripcionReceta.setText(getIntent().getExtras().getString(
					"descripcionReceta"));

			recetaEjecucionAdapter = new RecetaEjecucionAdapter(this,
					R.layout.filarecetaejecucion, recetaEjecuciones);
			setListAdapter(recetaEjecucionAdapter);

			obtenerRecetaEjecuciones();

			recetaEjecucionAdapter.notifyDataSetChanged();

		} else {
			Log.d(TAG, "Nueva receta");
			borrarReceta.setVisibility(View.GONE);
			anadirEjecucion.setVisibility(View.GONE);
		}

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
		case R.id.boton_grabar_receta:
			final ProgressDialog progressDialogGrabar = ProgressDialog.show(
					this, "", "Grabando receta...");
			new Thread() {
				public void run() {
					try {
						grabarReceta();
					} catch (Exception e) {
						Log.e(TAG,
								"Error al grabar la receta:" + e.getMessage());
					}
					// dismiss the progress dialog
					progressDialogGrabar.dismiss();
				}
			}.start();
			break;
		case R.id.boton_borrar_receta:
			final ProgressDialog progressDialogBorrar = ProgressDialog.show(
					this, "", "Borrando receta...");
			new Thread() {
				public void run() {
					try {
						borrarReceta();
					} catch (Exception e) {
						Log.e(TAG,
								"Error al borrar la receta:" + e.getMessage());
					}
					// dismiss the progress dialog
					progressDialogBorrar.dismiss();
				}
			}.start();
			break;
		case R.id.boton_anadir_ejecucion:
			if (codigoReceta != "" && codigoReceta != null) {
				Intent intent = new Intent(this, RecetaEjecucionActivity.class);
				intent.putExtra("codigoReceta", codigoReceta.toString());
				intent.putExtra("nombreReceta", nombreReceta.getText()
						.toString());
				intent.putExtra("descripcionReceta", descripcionReceta
						.getText().toString());
				startActivity(intent);
				finish();
			}
			break;
		}
	}

	@Override
	public void onBackPressed() {
		final Intent intentDet = new Intent(getApplicationContext(),
				ListaRecetasActivity.class);
		startActivity(intentDet);
		finish();
	}

	protected void obtenerRecetaEjecuciones() {
		Log.d(TAG, "Obteniendo ejecuciones:" + codigoReceta);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Receta_Ejecucion");
		query.whereEqualTo("objectIdReceta", codigoReceta);
		try {
			List<ParseObject> listaRecetaEjecuciones = query.find();
			for (ParseObject recetaEjecucion : listaRecetaEjecuciones) {
				recetaEjecuciones.add(new RecetaEjecucion(recetaEjecucion
						.getObjectId(), recetaEjecucion
						.getString("objectIdReceta"), recetaEjecucion
						.getString("d_descripcion"), recetaEjecucion
						.getParseFile("f_foto")));
			}
		} catch (ParseException e) {
			Log.d(TAG, "Error al obtener recetas:" + e.getMessage());
		}
	}

	protected void grabarReceta() {
		Log.d(TAG, "Grabando Receta");
		if ("".equals(codigoReceta)) {
			Log.d(TAG, "Es un insert");
			final ParseObject recetaParseObject = new ParseObject("Receta");
			recetaParseObject
					.put("d_nombre", nombreReceta.getText().toString());
			recetaParseObject.put("d_descripcion", descripcionReceta.getText()
					.toString());
			recetaParseObject.saveEventually(new SaveCallback() {
				public void done(ParseException e) {
					Log.d(TAG,
							"El codigo grabado es:"
									+ recetaParseObject.getObjectId());
					codigoReceta = recetaParseObject.getObjectId();
					borrarReceta.setVisibility(View.VISIBLE);
					anadirEjecucion.setVisibility(View.VISIBLE);
				}
			});
		} else {
			Log.d(TAG, "Es un update");
			ParseObject recetaParseObject = new ParseObject("Receta");
			recetaParseObject.setObjectId(codigoReceta);
			recetaParseObject
					.put("d_nombre", nombreReceta.getText().toString());
			recetaParseObject.put("d_descripcion", descripcionReceta.getText()
					.toString());
			recetaParseObject.saveEventually();
		}

	}

	protected void borrarReceta() {
		// Se buscan todas las ejecuciones
		ParseQuery<ParseObject> queryEjecucion = ParseQuery
				.getQuery("Receta_Ejecucion");
		queryEjecucion.whereEqualTo("objectIdReceta", codigoReceta);
		try {
			List<ParseObject> listaRecetaEjecuciones = queryEjecucion.find();
			Log.d(TAG,
					"Recuperados ejecuciones:" + listaRecetaEjecuciones.size());
			for (ParseObject recetaEjecucion : listaRecetaEjecuciones) {
				Log.d(TAG,
						"Borrando ejecucion:" + recetaEjecucion.getObjectId());
				// Se borran todos los ingredientes de cada ejecucion
				ParseQuery<ParseObject> queryIngrediente = ParseQuery
						.getQuery("Receta_Ingrediente");
				queryIngrediente.whereEqualTo("objectIdEjecucion",
						recetaEjecucion.getObjectId());
				try {
					List<ParseObject> listaRecetaIngredientes = queryIngrediente
							.find();
					Log.d(TAG, "Recuperados ingredientes:"
							+ listaRecetaIngredientes.size());
					for (ParseObject recetaIngrediente : listaRecetaIngredientes) {
						Log.d(TAG,
								"Borrando ingrediente:"
										+ recetaIngrediente.getObjectId());
						// Se borran todos los ingredientes de cada ejecucion
						recetaIngrediente.deleteEventually();
					}
				} catch (ParseException e) {
					Log.d(TAG, "Error al obtener ejecuciones:" + e.getMessage());
				}
				// Se borra la ejecucion
				recetaEjecucion.deleteEventually();
			}
		} catch (ParseException e) {
			Log.d(TAG, "Error al obtener ejecuciones:" + e.getMessage());
		}
		// Se borra la receta tras todas las ejecuciones
		Log.d(TAG, "Borrando receta:" + codigoReceta);
		ParseObject.createWithoutData("Receta", codigoReceta).deleteEventually(
				new DeleteCallback() {
					public void done(ParseException e) {
						// Se vuelve a la pantalla de la receta
						onBackPressed();
					}
				});
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		RecetaEjecucion recetaEjecucion = (RecetaEjecucion) l.getAdapter()
				.getItem(position);
		Log.d(TAG, recetaEjecucion.getCodigoRecetaEjecucion() + "");
		Log.d(TAG, recetaEjecucion.getDescripcionRecetaEjecucion());
		Intent intent = new Intent(this, RecetaEjecucionActivity.class);
		intent.putExtra("codigoRecetaEjecucion",
				recetaEjecucion.getCodigoRecetaEjecucion());
		intent.putExtra("descripcionRecetaEjecucion",
				recetaEjecucion.getDescripcionRecetaEjecucion());
		intent.putExtra("codigoReceta", codigoReceta.toString());
		intent.putExtra("nombreReceta", nombreReceta.getText().toString());
		intent.putExtra("descripcionReceta", descripcionReceta.getText()
				.toString());
		startActivity(intent);
		finish();
	}
}
