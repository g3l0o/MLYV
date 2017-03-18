package mlyv.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Inicio extends Activity {
	
	EditText EdTxtUsuario;
	EditText EdTxtContrasena;
	String usuario;
	String pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);

		// preferencia para saber si es la primera vez que la usa

		// Inicializar los edit xText
		EdTxtUsuario = (EditText) findViewById(R.id.Usuario);
		EdTxtContrasena = (EditText) findViewById(R.id.ContrasenaPrincipal);
		// Inicializar botones

		// boton para registrarse
		TextView botonRegistro = (TextView) findViewById(R.id.BotonRegistro);
		botonRegistro.setOnClickListener(new OnClickListener() {

			// "intent" para que me lleve al layout de registro para usuarios
			// nuevos.
			public void onClick(View v) {
				Intent in = new Intent(Inicio.this, Registro.class);
				startActivity(in);
			}
		});

		// boton para entrar
		TextView botonEntrar = (TextView) findViewById(R.id.BotonEntrar);
		botonEntrar.setOnClickListener(new OnClickListener() {

			// "intent" para que me lleve al layout de config.
			public void onClick(View v) {

				usuario = EdTxtUsuario.getText().toString();
				pass = EdTxtContrasena.getText().toString();

				String respuesta = login();

				StringTokenizer tok = new StringTokenizer(respuesta, ";");
				String confirmacion = tok.nextToken();
				String cont = tok.nextToken();
				String id = tok.nextToken();
				String itinerario = tok.nextToken();
				boolean access = confirmacion.contains("acce");
				Log.d("log_acceso", access + "");
				if (access) {
					
					SharedPreferences prefUser = getSharedPreferences("User", Context.MODE_PRIVATE);
					SharedPreferences.Editor prefUserEditor = prefUser.edit();
					prefUserEditor.putString("id", id);
					prefUserEditor.putString("itinerario", itinerario);
					prefUserEditor.commit();
					
					Log.d("id", "id de user"+id);
					contador(cont);
					boolean contador = cont.equals("0");
					Log.d("log_contador", contador + "");
					if (!contador) {
						Intent in = new Intent(Inicio.this, Menu.class);
						startActivity(in);
					} else {
						Intent in = new Intent(Inicio.this, Configuracion.class);
						startActivity(in);
					}

				} else {
					Toast.makeText(getBaseContext(),
							"usuario y/o password incorrectos",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void contador(String contador) {
		String url = "http://mexlyv.net78.net/mexico/contador.php?";
		// u=vero&p=234888&c=1
		url += "u=" + usuario;
		url += "&p=" + pass;
		url += "&c=" + contador;
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
	}

	private String login() {
		String respuesta = "";
		String url = "http://mexlyv.net78.net/mexico/login.php?";
		// u=vero&p=234888
		url += "u=" + usuario;
		url += "&p=" + pass;

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
}
