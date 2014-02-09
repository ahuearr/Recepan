package com.gusycorp.recepan.activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gusycorp.recepan.R;
import com.gusycorp.recepan.adapter.IngredienteSpinAdapter;
import com.gusycorp.recepan.adapter.RecetaIngredienteAdapter;
import com.gusycorp.recepan.adapter.UnidadSpinAdapter;
import com.gusycorp.recepan.application.RecepanApplication;
import com.gusycorp.recepan.model.Ingrediente;
import com.gusycorp.recepan.model.RecetaIngrediente;
import com.gusycorp.recepan.model.Unidad;
import com.parse.DeleteCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class RecetaEjecucionActivity extends ListActivity implements
		OnClickListener {

	private final String TAG = "RecetaEjecucionActivity";
	private RecetaIngredienteAdapter recetaIngredienteAdapter;
	List<RecetaIngrediente> recetaIngredienteList = new ArrayList<RecetaIngrediente>();

	List<Ingrediente> ingredienteList = new ArrayList<Ingrediente>();
	List<Unidad> unidadList = new ArrayList<Unidad>();

	private IngredienteSpinAdapter ingredienteAdapter;
	private UnidadSpinAdapter unidadAdapter;

	private String codigoReceta = "";
	private String descripcionReceta = "";
	private String codigoRecetaEjecucion = "";
	TextView nombreReceta;
	EditText descripcionEjecucion;
	ParseImageView fotoEjecucion;
	Button grabarEjecucion;
	Button borrarEjecucion;
	Spinner ingredienteSpinner;
	EditText cantidad;
	Spinner unidadSpinner;
	Button anadirIngrediente;

	private RecepanApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receta_ejecucion);

		Parse.initialize(this, "psxoBg3xpRdFZ0JFdm6WJQ9yanUvb3ckQlijpQIA",
				"aCQjpITwV3f6CWppcVKMa8EZacNHCePH7CDCEPwN");

		app = (RecepanApplication) getApplication();
		ingredienteList = app.getIngredienteList();
		unidadList = app.getUnidadList();

		nombreReceta = (TextView) findViewById(R.id.nombreReceta);
		descripcionEjecucion = (EditText) findViewById(R.id.descripcionRecetaEjecucion);
		fotoEjecucion = (ParseImageView) findViewById(R.id.fotoRecetaEjecucion);
		grabarEjecucion = (Button) findViewById(R.id.boton_grabar_receta_ejecucion);
		grabarEjecucion.setOnClickListener(this);
		borrarEjecucion = (Button) findViewById(R.id.boton_borrar_receta_ejecucion);
		borrarEjecucion.setOnClickListener(this);
		ingredienteSpinner = (Spinner) findViewById(R.id.spinnerIngrediente);
		cantidad = (EditText) findViewById(R.id.cantidadIngrediente);
		unidadSpinner = (Spinner) findViewById(R.id.spinnerUnidad);
		anadirIngrediente = (Button) findViewById(R.id.boton_anadir_ingrediente);
		anadirIngrediente.setOnClickListener(this);
		setAdapterSpinners();

		Bundle extras = getIntent().getExtras();
		codigoReceta = getIntent().getExtras().getString("codigoReceta");
		nombreReceta.setText(getIntent().getExtras().getString("nombreReceta"));
		descripcionReceta = getIntent().getExtras().getString(
				"descripcionReceta");
		if (extras.getString("codigoRecetaEjecucion") != null
				&& extras.getString("codigoRecetaEjecucion") != "") {
			Log.d(TAG,
					"Existe ejecucion:"
							+ getIntent().getExtras().getString(
									"codigoRecetaEjecucion"));
			codigoRecetaEjecucion = getIntent().getExtras().getString(
					"codigoRecetaEjecucion");
			descripcionEjecucion.setText(getIntent().getExtras().getString(
					"descripcionRecetaEjecucion"));

			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("Receta_Ejecucion");
			query.whereEqualTo("objectIdReceta", codigoRecetaEjecucion);
			try {
				List<ParseObject> listaRecetaEjecuciones = query.find();
				for (ParseObject recetaEjecucion : listaRecetaEjecuciones) {
					fotoEjecucion.setParseFile(recetaEjecucion
							.getParseFile("f_foto"));
				}
			} catch (ParseException e) {
				Log.d(TAG,
						"Error al obtener la foto de la ejecucion:"
								+ e.getMessage());
			}

			recetaIngredienteAdapter = new RecetaIngredienteAdapter(this,
					R.layout.filarecetaingrediente, recetaIngredienteList);
			setListAdapter(recetaIngredienteAdapter);

			obtenerRecetaIngredientes();

			recetaIngredienteAdapter.notifyDataSetChanged();
		} else {
			Log.d(TAG, "Nueva ejecucion");
			borrarEjecucion.setVisibility(View.GONE);
			ingredienteSpinner.setVisibility(View.GONE);
			cantidad.setVisibility(View.GONE);
			unidadSpinner.setVisibility(View.GONE);
			anadirIngrediente.setVisibility(View.GONE);
		}

		final ListView lista = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(lista);
	}

	private void setAdapterSpinners() {
		ingredienteAdapter = new IngredienteSpinAdapter(this,
				android.R.layout.simple_spinner_item, ingredienteList);
		ingredienteAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ingredienteSpinner.setAdapter(ingredienteAdapter);
		unidadAdapter = new UnidadSpinAdapter(this,
				android.R.layout.simple_spinner_item, unidadList);
		unidadAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unidadSpinner.setAdapter(unidadAdapter);
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
		case R.id.boton_grabar_receta_ejecucion:
			final ProgressDialog progressDialogEjecucion = ProgressDialog.show(
					this, "", "Grabando ejecucion...");
			new Thread() {
				public void run() {
					try {
						grabarRecetaEjecucion();
					} catch (Exception e) {
						Log.e(TAG,
								"Error al grabar la ejecucion:"
										+ e.getMessage());
					}
					// dismiss the progress dialog
					progressDialogEjecucion.dismiss();
				}
			}.start();
			break;
		case R.id.boton_borrar_receta_ejecucion:
			final ProgressDialog progressDialogBorrar = ProgressDialog.show(
					this, "", "Borrando ejecucion...");
			new Thread() {
				public void run() {
					try {
						borrarRecetaEjecucion();
					} catch (Exception e) {
						Log.e(TAG,
								"Error al borrar la ejecucion:"
										+ e.getMessage());
					}
					// dismiss the progress dialog
					progressDialogBorrar.dismiss();
				}
			}.start();
			break;
		case R.id.boton_anadir_ingrediente:
			if (codigoRecetaEjecucion != "" && codigoRecetaEjecucion != null) {
				final ProgressDialog progressDialogIngrediente = ProgressDialog
						.show(this, "", "Grabando ingrediente...");
				new Thread() {
					public void run() {
						try {
							anadirIngrediente();
						} catch (Exception e) {
							Log.e(TAG,
									"Error al grabar el ingrediente:"
											+ e.getMessage());
						}
						// dismiss the progress dialog
						progressDialogIngrediente.dismiss();
					}
				}.start();
			}
			break;
		}
	}

	@Override
	public void onBackPressed() {
		final Intent intentDet = new Intent(getApplicationContext(),
				RecetaActivity.class);
		intentDet.putExtra("codigoReceta", codigoReceta);
		intentDet.putExtra("nombreReceta", nombreReceta.getText().toString());
		intentDet.putExtra("descripcionReceta", descripcionReceta);
		startActivity(intentDet);
		finish();
	}

	protected void obtenerRecetaIngredientes() {
		Log.d(TAG, "Obteniendo ingredientes");
		// Se limpia el arrayList para no repetir registros al añadir o
		// modificar un ingrediente
		recetaIngredienteList.clear();
		ParseQuery<ParseObject> query = ParseQuery
				.getQuery("Receta_Ingrediente");
		query.whereEqualTo("objectIdEjecucion", codigoRecetaEjecucion);
		try {
			List<ParseObject> listaRecetaIngredientes = query.find();
			Log.d(TAG,
					"Recuperados ingredientes:"
							+ listaRecetaIngredientes.size());
			for (ParseObject recetaIngrediente : listaRecetaIngredientes) {
				recetaIngredienteList.add(new RecetaIngrediente(
						recetaIngrediente.getObjectId(), recetaIngrediente
								.getString("d_descripcion")));
			}
		} catch (ParseException e) {
			Log.d(TAG, "Error al obtener ingredientes:" + e.getMessage());
		}
	}

	protected void grabarRecetaEjecucion() {
		Log.d(TAG, "Grabando RecetaEjecucion");
		if ("".equals(codigoRecetaEjecucion)) {
			Log.d(TAG, "Es un insert");
			final ParseObject recetaEjecucionParseObject = new ParseObject(
					"Receta_Ejecucion");
			recetaEjecucionParseObject.put("d_descripcion",
					descripcionEjecucion.getText().toString());
			recetaEjecucionParseObject.put("objectIdReceta", codigoReceta);
			// De momento lo de la foto no se hace
			// Bitmap bitmap = ((BitmapDrawable) fotoEjecucion.getDrawable())
			// .getBitmap();
			// ByteArrayOutputStream stream = new ByteArrayOutputStream();
			// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			// byte[] bitmapdata = stream.toByteArray();
			// recetaEjecucionParseObject.put("f_foto", bitmapdata);
			recetaEjecucionParseObject.saveEventually(new SaveCallback() {
				public void done(ParseException e) {
					Log.d(TAG, "El codigo grabado es:"
							+ recetaEjecucionParseObject.getObjectId());
					codigoRecetaEjecucion = recetaEjecucionParseObject
							.getObjectId();
					recetaIngredienteAdapter = new RecetaIngredienteAdapter(
							RecetaEjecucionActivity.this,
							R.layout.filarecetaingrediente,
							recetaIngredienteList);
					setListAdapter(recetaIngredienteAdapter);

					obtenerRecetaIngredientes();

					recetaIngredienteAdapter.notifyDataSetChanged();

					borrarEjecucion.setVisibility(View.VISIBLE);
					ingredienteSpinner.setVisibility(View.VISIBLE);
					cantidad.setVisibility(View.VISIBLE);
					unidadSpinner.setVisibility(View.VISIBLE);
					anadirIngrediente.setVisibility(View.VISIBLE);
				}
			});
		} else {
			Log.d(TAG, "Es un update");
			ParseObject recetaEjecucionParseObject = new ParseObject(
					"RecetaEjecucion");
			recetaEjecucionParseObject.setObjectId(codigoRecetaEjecucion);
			recetaEjecucionParseObject.put("d_descripcion",
					descripcionEjecucion.getText().toString());
			recetaEjecucionParseObject.put("objectIdReceta", codigoReceta);

			Bitmap bitmap = ((BitmapDrawable) fotoEjecucion.getDrawable())
					.getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] bitmapdata = stream.toByteArray();
			recetaEjecucionParseObject.put("f_foto", bitmapdata);
			recetaEjecucionParseObject.saveEventually();
		}

	}

	protected void anadirIngrediente() {
		// Se recoge el ingrediente del Spinner
		final Ingrediente ingrediente = (Ingrediente) ingredienteSpinner
				.getSelectedItem();
		final Unidad unidad = (Unidad) unidadSpinner.getSelectedItem();
		// Si el ingrediente esta ya grabado para esa ejecucion sera un update
		Log.d(TAG,
				"Esta el ingrediente en la ejecucion:"
						+ ingrediente.getNombreIngrediente());
		ParseQuery<ParseObject> query = ParseQuery
				.getQuery("Receta_Ingrediente");
		query.whereEqualTo("objectIdEjecucion", codigoRecetaEjecucion);
		query.whereEqualTo("objectIdIngrediente",
				ingrediente.getCodigoIngrediente());
		try {
			ParseObject recetaIngredienteParseObject;
			List<ParseObject> listaRecetaIngredientes = query.find();
			if (listaRecetaIngredientes.size() > 0) {
				// Es un update
				Log.d(TAG, "Es un update");
				// Si el ingrediente esta solo puede estar una vez, por eso se
				// coge la posicion 0
				recetaIngredienteParseObject = listaRecetaIngredientes.get(0);
				recetaIngredienteParseObject.put("objectIdIngrediente",
						ingrediente.getCodigoIngrediente());
				recetaIngredienteParseObject.put("x_cantidad",
						Integer.parseInt(cantidad.getText().toString()));
				recetaIngredienteParseObject.put("objectIdUnidad",
						unidad.getCodigoUnidad());
				recetaIngredienteParseObject.put(
						"d_descripcion",
						ingrediente.getNombreIngrediente() + " "
								+ cantidad.getText().toString() + " "
								+ unidad.getNombreUnidad());
			} else {
				// Es un insert
				Log.d(TAG, "Es un insert");
				recetaIngredienteParseObject = new ParseObject(
						"Receta_Ingrediente");
				recetaIngredienteParseObject.put("objectIdEjecucion",
						codigoRecetaEjecucion);
				recetaIngredienteParseObject.put("objectIdIngrediente",
						ingrediente.getCodigoIngrediente());
				recetaIngredienteParseObject.put("x_cantidad",
						Integer.parseInt(cantidad.getText().toString()));
				recetaIngredienteParseObject.put("objectIdUnidad",
						unidad.getCodigoUnidad());
				recetaIngredienteParseObject.put(
						"d_descripcion",
						ingrediente.getNombreIngrediente() + " "
								+ cantidad.getText().toString() + " "
								+ unidad.getNombreUnidad());
			}
			recetaIngredienteParseObject.saveEventually(new SaveCallback() {
				public void done(ParseException e) {
					obtenerRecetaIngredientes();
					recetaIngredienteAdapter.notifyDataSetChanged();
				}
			});
			// Tras añadir un ingrediente se recalcula la lista de ingredientes
		} catch (ParseException e) {
			Log.d(TAG,
					"Error al añadir/modificar ingredientes:" + e.getMessage());
		}

	}

	protected void borrarRecetaEjecucion() {
		// Se borran todos los ingredientes que esten grabados de la ejecucion
		ParseQuery<ParseObject> query = ParseQuery
				.getQuery("Receta_Ingrediente");
		query.whereEqualTo("objectIdEjecucion", codigoRecetaEjecucion);
		try {
			List<ParseObject> listaRecetaIngredientes = query.find();
			Log.d(TAG,
					"Recuperados ingredientes:"
							+ listaRecetaIngredientes.size());
			for (ParseObject recetaIngrediente : listaRecetaIngredientes) {
				Log.d(TAG,
						"Borrando ingrediente:"
								+ recetaIngrediente.getObjectId());
				recetaIngrediente.deleteEventually();
			}
		} catch (ParseException e) {
			Log.d(TAG, "Error al obtener ingredientes:" + e.getMessage());
		}
		// Se borra la ejecucion
		Log.d(TAG, "Borrando ejecucion:" + codigoRecetaEjecucion);
		ParseObject
				.createWithoutData("Receta_Ejecucion", codigoRecetaEjecucion)
				.deleteEventually(new DeleteCallback() {
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
		final RecetaIngrediente recetaIngrediente = (RecetaIngrediente) l
				.getAdapter().getItem(position);
		Log.d(TAG, recetaIngrediente.getCodigoRecetaIngrediente());
		// Mensaje para borrar el ingrediente
		final ProgressDialog progressDialogIngrediente = ProgressDialog.show(
				this, "", "Borrando ingrediente...");
		new Thread() {
			public void run() {
				try {
					ParseObject.createWithoutData("Receta_Ingrediente",
							recetaIngrediente.getCodigoRecetaIngrediente())
							.deleteEventually(new DeleteCallback() {
								public void done(ParseException e) {
									obtenerRecetaIngredientes();
									recetaIngredienteAdapter
											.notifyDataSetChanged();
								}
							});
				} catch (Exception e) {
					Log.e(TAG,
							"Error al borrar el ingrediente:" + e.getMessage());
				}
				// dismiss the progress dialog
				progressDialogIngrediente.dismiss();
			}
		}.start();
	}
}
