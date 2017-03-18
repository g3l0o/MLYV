package mlyv.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registro extends Activity {
	EditText EdTxtNombre;
	EditText EdTxtApellPat;
	EditText EdTxtApellMat;
	EditText EdTxtUsuario;
	EditText EdTxtContraReg;
	Button DtPckFecha;
	TextView fechaNacimiento;
	TextView cadenaNacimiento;

	String nombre;
	String AP;
	String AM;
	String usuario;
	String pass;
	int dia;
	int mes;
	int year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		fechaNacimiento = (TextView) findViewById(R.id.textViewNacimientoModificada);
		cadenaNacimiento = new TextView(getBaseContext());

		// Inicializar los EditText
		EdTxtNombre = (EditText) findViewById(R.id.EditTextNombre);
		EdTxtApellPat = (EditText) findViewById(R.id.EditTextApellidoPaterno);
		EdTxtApellMat = (EditText) findViewById(R.id.EditTextApellidoMaterno);
		EdTxtUsuario = (EditText) findViewById(R.id.EditTextUsuario);
		EdTxtContraReg = (EditText) findViewById(R.id.Contrasena1);

		DtPckFecha = (Button) findViewById(R.id.BtnFechaNac);
		DtPckFecha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new DatePickerFragment(fechaNacimiento, cadenaNacimiento).show(getFragmentManager(), "datePicker");
			}
		});

		// Inicializar boton
		TextView botonAceptar = (TextView) findViewById(R.id.BotonAceptarReg);
		botonAceptar.setOnClickListener(new OnClickListener() {

			// FALTA verificar que los campos esten llenos correctamente

			public void onClick(View v) {
				nombre = EdTxtNombre.getText().toString();
				AP = EdTxtApellPat.getText().toString();
				AM = EdTxtApellMat.getText().toString();
				usuario = EdTxtUsuario.getText().toString();
				pass = EdTxtContraReg.getText().toString();
				setFecha();
				agregarUsuario();

				Intent in = new Intent(Registro.this, Inicio.class);
				startActivity(in);

			}
		});
	}

	public void setFecha(){
		String fecha = cadenaNacimiento.getText().toString();
		StringTokenizer tok = new StringTokenizer(fecha, "/");
		year = Integer.parseInt(tok.nextToken());
		mes = Integer.parseInt(tok.nextToken());
		dia = Integer.parseInt(tok.nextToken());
	}
	
	public void agregarUsuario() {
		String url = "http://mexlyv.net78.net/mexico/altausuario.php?";
		nombre = nombre.replace(" ", "%20").replace("&", "%26");
		// n=Rogelio&ap=Rivera&am=Melendez&d=04&m=06&y=1991&u=gelo&p=g4t0
		url += "n=" + nombre;
		url += "&ap=" + AP;
		url += "&am=" + AM;
		url += "&d=" + dia;
		url += "&m=" + mes;
		url += "&y=" + year;
		url += "&u=" + usuario;
		url += "&p=" + pass;
		
		Log.d("url agregar", url);

		
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
				StringTokenizer tok = new StringTokenizer(result, ";");
				String respuesta = tok.nextToken();
				if (respuesta.compareTo("Agregado") == 0) {
					Toast.makeText(getBaseContext(), respuesta,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "Hubo un error",
							Toast.LENGTH_SHORT).show();
					Toast.makeText(getBaseContext(), "Intentalo de nuevo",
							Toast.LENGTH_SHORT).show();
				}

			} catch (Exception e) {
				Log.d("log_tag", "Error converting result " + e.toString());
			}
		}
		
	}
}
