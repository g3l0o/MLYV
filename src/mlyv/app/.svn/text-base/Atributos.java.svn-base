package mlyv.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Atributos extends Activity {
	int numAdultos;
	int numMenores;
	int automovil = 0;
	int comidasDia;
	int barrios = 0;
	int museos = 0;
	int hoteles = 0;
	int sitios = 0;
	int parques = 0;
	int recorridos = 0;

	String presupuesto;
	String minDineroComida;

	CheckBox checkBoxAuto;
	CheckBox checkBoxBarrios;
	CheckBox checkBoxMuseos;
	CheckBox checkBoxHoteles;
	CheckBox checkBoxSitios;
	CheckBox checkBoxParques;
	CheckBox checkBoxRecorridos;

	EditText EdTxtPresupuesto;
	EditText EdTxtMinDineroComid;

	String idUser;

	TextView cadenaLlegada;
	TextView cadenaRegreso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atributos);

		// aqui va la fecha
		final TextView fechaLlegada = (TextView) findViewById(R.id.textViewFechaModificadaLlegada);
		final TextView fechaRegreso = (TextView) findViewById(R.id.textViewFechaModificadaRegreso);
		cadenaLlegada = new TextView(getBaseContext());
		cadenaRegreso = new TextView(getBaseContext());

		// inicializamos los spinners
		Spinner spinAdultos = (Spinner) findViewById(R.id.spinnerAdultos);
		Spinner spinMenores = (Spinner) findViewById(R.id.spinnerMenores);
		Spinner spinMinComida = (Spinner) findViewById(R.id.spinnerMinComida);

		// inicializamos los EditText
		EdTxtPresupuesto = (EditText) findViewById(R.id.editTextPresupuesto);
		EdTxtMinDineroComid = (EditText) findViewById(R.id.editTextMinDineroComida);

		// inicializamos los checkBox
		checkBoxAuto = (CheckBox) findViewById(R.id.checkBoxAutomovil);
		checkBoxBarrios = (CheckBox) findViewById(R.id.checkBoxBarrios);
		checkBoxMuseos = (CheckBox) findViewById(R.id.checkBoxMuseos);
		checkBoxHoteles = (CheckBox) findViewById(R.id.checkBoxHoteles);
		checkBoxSitios = (CheckBox) findViewById(R.id.checkBoxSitios);
		checkBoxParques = (CheckBox) findViewById(R.id.checkBoxParques);
		checkBoxRecorridos = (CheckBox) findViewById(R.id.checkBoxRecorridos);

		// inicializamos los Buttons
		Button btnFechaLlegada = (Button) findViewById(R.id.buttonFechaLlegada);
		Button btnFechaRegreso = (Button) findViewById(R.id.buttonFechaRegreso);
		Button btnGenerar = (Button) findViewById(R.id.buttonGenerar);

		// le damos los valores a los Spinners
		ArrayAdapter<CharSequence> adultosAdapter = ArrayAdapter
				.createFromResource(getBaseContext(), R.array.adultos,
						android.R.layout.simple_spinner_item);
		adultosAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinAdultos.setAdapter(adultosAdapter);

		ArrayAdapter<CharSequence> menoresAdapter = ArrayAdapter
				.createFromResource(getBaseContext(), R.array.menores,
						android.R.layout.simple_spinner_item);
		menoresAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinMenores.setAdapter(menoresAdapter);

		ArrayAdapter<CharSequence> comidaAdapter = ArrayAdapter
				.createFromResource(getBaseContext(), R.array.comidas,
						android.R.layout.simple_spinner_item);
		comidaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinMinComida.setAdapter(comidaAdapter);

		// configuracion de los seleccionadores de los spinners
		spinAdultos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				// TODO Auto-generated method stub
				numAdultos = pos + 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinMenores.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				numMenores = pos;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinMinComida.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				comidasDia = pos;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// configuracion de los botones
		btnFechaLlegada.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerFragment(fechaLlegada, cadenaLlegada).show(
						getFragmentManager(), "datePicker");
			}
		});
		btnFechaRegreso.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerFragment(fechaRegreso, cadenaRegreso).show(
						getFragmentManager(), "datePicker");
			}
		});
		btnGenerar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setValorCheck();
				setIdUser();
				presupuesto = EdTxtPresupuesto.getText().toString();
				minDineroComida = EdTxtMinDineroComid.getText().toString();

				if (validarFecha()) {
					String url = generarCadena();
					String respuesta = insertarItinerario(url);

					StringTokenizer tok = new StringTokenizer(respuesta, ";");
					String confirmacion = tok.nextToken();
					Log.d("confirmacion", confirmacion);
					

					finish();
					Toast.makeText(getBaseContext(), "Itinerario Creado",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getBaseContext(),
							"Favor de revisar las fechas", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	public String insertarItinerario(String url) {
		String respuesta = "";
		// Variables necesarias para el funcionamiento
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse webServerResponse = null;
		InputStream inputStream = null;
		String result = "";
		URI myURI = null;

		// Conexión con la página, IMPORTANTE mantener el protocolo "http://"
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
				respuesta = result;

			} catch (Exception e) {
				Log.d("log_tag", "Error converting result " + e.toString());
			}
		}
		return respuesta;
	}

	public void setIdUser() {
		SharedPreferences prefUser = getSharedPreferences("User",
				Context.MODE_PRIVATE);
		idUser = prefUser.getString("id", "0");
	}

	public boolean validarFecha() {

		String llegada = cadenaLlegada.getText().toString();
		String regreso = cadenaRegreso.getText().toString();

		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		Date fechaLlegada = null;
		Date fechaRegreso = null;
		try {
			fechaLlegada = formato.parse(llegada);
			fechaRegreso = formato.parse(regreso);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return fechaRegreso.after(fechaLlegada);
	}

	public String generarCadena() {
		/*
		 * http://mexlyv.net78.net/mexico/Victor/main_agents.php? idUsuario=1
		 * &numAdultos=1 &numNinos=1 &presupuestoInicial=50000 &carro=1
		 * &fechaLlegada=2013/06/19 &fechaSalida=2013/06/22 &barrios=1 &museos=1
		 * &recorridos=1 &hoteles=1 &sitiosInteres=1 &parques=1
		 * &minDineroComida=30 &minNumeroComidas=3
		 */
		String cadena = "http://mexlyv.net78.net/mexico/Victor/main_agents.php?";
		cadena += "idUsuario=" + idUser;
		cadena += "&numAdultos=" + numAdultos;
		cadena += "&numNinos=" + numMenores;
		cadena += "&presupuestoInicial=" + presupuesto;
		cadena += "&carro=" + automovil;
		cadena += "&fechaLlegada=" + cadenaLlegada.getText().toString();
		cadena += "&fechaSalida=" + cadenaRegreso.getText().toString();
		cadena += "&barrios=" + barrios;
		cadena += "&museos=" + museos;
		cadena += "&recorridos=" + recorridos;
		cadena += "&hoteles=" + hoteles;
		cadena += "&sitiosInteres=" + sitios;
		cadena += "&parques=" + parques;
		cadena += "&minDineroComida=" + minDineroComida;
		cadena += "&minNumeroComidas=" + comidasDia;

		return cadena;
	}

	public void setValorCheck() {
		if (checkBoxAuto.isChecked()) {
			automovil = 1;
		}
		if (checkBoxBarrios.isChecked()) {
			barrios = 1;
		}
		if (checkBoxHoteles.isChecked()) {
			hoteles = 1;
		}
		if (checkBoxMuseos.isChecked()) {
			museos = 1;
		}
		if (checkBoxParques.isChecked()) {
			parques = 1;
		}
		if (checkBoxSitios.isChecked()) {
			sitios = 1;
		}
		if (checkBoxRecorridos.isChecked()) {
			recorridos = 1;
		}
	}
}
