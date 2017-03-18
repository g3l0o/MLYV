package mlyv.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Plan extends Activity {

	ArrayList<String> comida = new ArrayList<String>();
	ArrayList<String> comidaID = new ArrayList<String>();
	ArrayList<String> interes = new ArrayList<String>();
	ArrayList<String> interesID = new ArrayList<String>();

	String lat = "19.4356";
	String lon = "-99.141161";
	String idComida;
	String idInteres;
	String idTranporte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);

		// Inicializar botones
		getComida();
		getIntereses();

		// spinner Alimento
		Spinner spinAlimento = (Spinner) findViewById(R.id.spinnerAlimento);
		ArrayAdapter<String> alimentoAdapter = new ArrayAdapter<String>(
				getBaseContext(), android.R.layout.simple_spinner_item, comida);
		alimentoAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinAlimento.setAdapter(alimentoAdapter);
		spinAlimento.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("id comida", comidaID.get(pos));
				idComida = comidaID.get(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// spinner Intereses
		Spinner spinIntereses = (Spinner) findViewById(R.id.spinnerIntereses);
		ArrayAdapter<String> interesAdapter = new ArrayAdapter<String>(
				getBaseContext(), android.R.layout.simple_spinner_item, interes);
		interesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinIntereses.setAdapter(interesAdapter);
		spinIntereses.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("id interes", interesID.get(pos));
				idInteres = interesID.get(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// Spiner transporte
		Spinner spinTransporte = (Spinner) findViewById(R.id.spinnerTransporte);
		ArrayAdapter<CharSequence> TransporteAdapter = ArrayAdapter
				.createFromResource(getBaseContext(), R.array.transporte,
						android.R.layout.simple_spinner_item);
		TransporteAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinTransporte.setAdapter(TransporteAdapter);
		spinTransporte.setSelection(0);
		spinTransporte.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Log.d("id transporte", "" + (pos + 1));
				idTranporte = "" + (pos + 1);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// Bot—n aceptar
		TextView botonAceptar = (TextView) findViewById(R.id.BotonAceptarPlan);
		botonAceptar.setOnClickListener(new OnClickListener() {

			// "intent" que lleva al layout de detalles
			public void onClick(View v) {
				
				Log.d("valores guardados", "hecho");
				SharedPreferences prefQueHago = getSharedPreferences("QueHago",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor prefQueHagoEditor = prefQueHago.edit();
				prefQueHagoEditor.putString("idComida", idComida);
				prefQueHagoEditor.putString("idInteres", idInteres);
				prefQueHagoEditor.putString("idTransporte", idTranporte);
				prefQueHagoEditor.putString("lat", lat);
				prefQueHagoEditor.putString("lon", lon);
				prefQueHagoEditor.commit();
				
				Log.d("lat plan", prefQueHago.getString("lat", ""));
				
				Intent in = new Intent(Plan.this, Detalles.class);
				startActivity(in);
			}
		});
	}

	protected void getComida() {
		// Variables necesarias para el funcionamiento
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse webServerResponse = null;
		InputStream inputStream = null;
		String result = "";
		URI myURI = null;
		String url = "";

		// Conexión con la página, IMPORTANTE mantener el protocolo "http://"
		url = "http://mexlyv.net78.net/mexico/Victor/getFoodSubcategories.php";
		Log.d("URL", url);
		try {
			myURI = new URI(url);
		} catch (URISyntaxException e) {
			Log.i("myURI", "myURI Causo un error");
		}

		// Obtener Respuesta por parte del servicio de internet
		HttpGet getMethod = new HttpGet(myURI);
		try {
			webServerResponse = httpClient.execute(getMethod);
		} catch (ClientProtocolException e) {
			Log.i("webServerResponse",
					"webServerResponse y ClientProtocolException Causo Error");
		} catch (IOException e) {
			Log.i("webServerResponse",
					"webServerResponse y IOException Causo Error");
		}

		// Obtener entidad para poder procesar el "Response"
		HttpEntity httpEntity = webServerResponse.getEntity();
		if (httpEntity != null) {
			try {
				inputStream = httpEntity.getContent();
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("inputStream", "inputStream Causo Error");
			}
			// Response procesada, obtener el texto
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "iso-8859-1"), 8);
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line + "\n");
				}
				inputStream.close();
				result = stringBuilder.toString();
				if (!(result.compareTo("0") == 0)) {
					Log.d("MI TAG", result);
					StringTokenizer cat = new StringTokenizer(result, ";");
					int numCat = cat.countTokens() - 1;
					while (cat.hasMoreElements()) {
						if (cat.countTokens() != 1) {
							String categoria = cat.nextToken();
							StringTokenizer com = new StringTokenizer(
									categoria, ",");
							String id = com.nextToken();
							String nombre = com.nextToken();
							comida.add(nombre);
							comidaID.add(id);
						} else {
							break;
						}
					}
					Log.d("categorias", "num de Categorias: " + numCat);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("No se encontró ninguna categoria")
							.setTitle("Categorias")
							.setCancelable(false)
							.setPositiveButton("Aceptar",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.dismiss();
											dialog.cancel();
										}
									});
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			} catch (Exception e) {
				Log.d("log_tag", "Error converting result " + e.toString());
			}
		}
	}

	protected void getIntereses() {
		// Variables necesarias para el funcionamiento
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse webServerResponse = null;
		InputStream inputStream = null;
		String result = "";
		URI myURI = null;
		String url = "";

		// Conexión con la página, IMPORTANTE mantener el protocolo "http://"
		url = "http://mexlyv.net78.net/mexico/Victor/getCategories.php";
		Log.d("URL", url);
		try {
			myURI = new URI(url);
		} catch (URISyntaxException e) {
			Log.i("myURI", "myURI Causo un error");
		}

		// Obtener Respuesta por parte del servicio de internet
		HttpGet getMethod = new HttpGet(myURI);
		try {
			webServerResponse = httpClient.execute(getMethod);
		} catch (ClientProtocolException e) {
			Log.i("webServerResponse",
					"webServerResponse y ClientProtocolException Causo Error");
		} catch (IOException e) {
			Log.i("webServerResponse",
					"webServerResponse y IOException Causo Error");
		}

		// Obtener entidad para poder procesar el "Response"
		HttpEntity httpEntity = webServerResponse.getEntity();
		if (httpEntity != null) {
			try {
				inputStream = httpEntity.getContent();
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("inputStream", "inputStream Causo Error");
			}
			// Response procesada, obtener el texto
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "iso-8859-1"), 8);
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line + "\n");
				}
				inputStream.close();
				result = stringBuilder.toString();
				if (!(result.compareTo("0") == 0)) {
					Log.d("MI TAG", result);
					StringTokenizer cat = new StringTokenizer(result, ";");
					int numCat = cat.countTokens() - 1;
					while (cat.hasMoreElements()) {
						if (cat.countTokens() != 1) {
							String categoria = cat.nextToken();
							StringTokenizer com = new StringTokenizer(
									categoria, ",");
							String id = com.nextToken();
							String nombre = com.nextToken();
							interes.add(nombre);
							interesID.add(id);
						} else {
							break;
						}
					}
					Log.d("categorias", "num de Categorias: " + numCat);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("No se encontró ninguna categoria")
							.setTitle("Categorias")
							.setCancelable(false)
							.setPositiveButton("Aceptar",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.dismiss();
											dialog.cancel();
										}
									});
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			} catch (Exception e) {
				Log.d("log_tag", "Error converting result " + e.toString());
			}
		}
	}
}