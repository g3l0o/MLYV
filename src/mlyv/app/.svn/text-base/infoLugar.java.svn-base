package mlyv.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class infoLugar extends Activity {
	String nombre;
	int id;
	Bitmap mIcon_val;
	String mapa;
	double lat;
	double lon;

	private String costo;
	private String menores;
	private String horaApertura;
	private String horaCierre;
	private String calificacion;
	private String subcategoria;
	private String descripcion;
	private String tiempoAprox;
	private String dias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// obtenemos el nombre y el id del lugar
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		nombre = extras.getString("nombre");
		id = extras.getInt("id");
		lat = Double.parseDouble(extras.getString("lat"));
		lon = Double.parseDouble(extras.getString("lon"));
		mapa = extras.getString("mapa");
		

		
		// nombramos la pestaña
		setTitle(nombre);

		setContentView(R.layout.info_lugar);

		TextView txtDescripcion = (TextView) findViewById(R.id.textoDescripcion);
		TextView txtHorario = (TextView) findViewById(R.id.textoHorario);
		TextView txtDias = (TextView) findViewById(R.id.textoDias);
		TextView txtCosto = (TextView) findViewById(R.id.textoCosto);
		TextView txtMenores = (TextView) findViewById(R.id.textoMenores);
		RatingBar ratingCalificacion = (RatingBar) findViewById(R.id.ratingBarCalificacion);
		ImageButton btnRuta = (ImageButton) findViewById(R.id.imageButtonRuta);
		
		//deshabilitamos la seleccion por parte del cliente
		ratingCalificacion.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		//obtenemos la info de los lugares
		getInfoLugar();
		
		//funcionalidad del imageButton de ruta
		btnRuta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (mapa.equals("si")) {
					Intent in = new Intent(infoLugar.this, MapaRuta.class);
					in.putExtra("lat", lat);
					in.putExtra("lon", lon);
					in.putExtra("nombre", nombre);
					startActivity(in);
				} else {
					getIntent().putExtra("idRuta", id);
					setResult(RESULT_OK, getIntent());
					finish();
				}
			}
		});
		
		// obtenemos la imagen conforme a la URL
		ImageView imagen = (ImageView) findViewById(R.id.imagenLugar);
		getImageByURL();
		imagen.setImageBitmap(mIcon_val);
		
		String textoMenores = "";
		if(menores.equals("0")){
			textoMenores = "No";
		}else{
			textoMenores = "Si";
		}
		textoMenores += " se aceptan menores";
	
		//le ponemos informacion a las etiquetas
		txtDescripcion.setText(descripcion);
		txtHorario.setText(horaApertura+":00 a "+ horaCierre+":00");
		txtDias.setText(dias());
		txtCosto.setText("Aprox:  $"+costo+".00  /persona");
		txtMenores.setText(textoMenores);
		ratingCalificacion.setNumStars(Integer.parseInt(calificacion));
		
		
	}

	private void getInfoLugar() {
		// Variables necesarias para el funcionamiento
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse webServerResponse = null;
		InputStream inputStream = null;
		String result = "";
		URI myURI = null;
		String url = "";

		// http://mexlyv.net78.net/mexico/infoLugar.php?i=24
		url = "http://mexlyv.net78.net/mexico/infoLugar.php?";
		url += "i=" + id;

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
				Log.d("MI TAG", result);
				StringTokenizer cat = new StringTokenizer(result, "///");
				cat.nextToken();
				costo = cat.nextToken();
				menores = cat.nextToken();
				horaApertura = cat.nextToken();
				horaCierre = cat.nextToken();
				calificacion = cat.nextToken();
				descripcion = cat.nextToken();
				tiempoAprox = cat.nextToken();
				dias = cat.nextToken();

			} catch (Exception e) {
				Log.d("log_tag", "Error converting result " + e.toString());
			}
		}
	}
	
	private String dias(){
		String cadena = "";
		boolean primero = false;
		StringTokenizer d = new StringTokenizer(dias, ",");
		Log.d("dias", dias);
		int num = d.countTokens();
		Log.d("tokens", ""+num);
		while (d.hasMoreElements()) {
			if (primero) {
				cadena += ", ";
			}
			int dia = Integer.parseInt(d.nextToken());
			switch (dia) {
			case 1:
				cadena += "Do";
				break;
			case 2:
				cadena += "Lu";
				break;
			case 3:
				cadena += "Ma";
				break;
			case 4:
				cadena += "Mi";
				break;
			case 5:
				cadena += "Ju";
				break;
			case 6:
				cadena += "Vi";
				break;
			case 7:
				cadena += "Sa";
				break;
			default:
				break;
			}
			primero = true;
		}
		Log.d("cadena", cadena);
		return cadena;
	}

	private void getImageByURL() {
		URL newurl;
		try {
			newurl = new URL(
					"http://maps.googleapis.com/maps/api/streetview?size=152x152&location="+lat+",%20"+lon+"&sensor=true");
			mIcon_val = BitmapFactory.decodeStream(newurl.openConnection()
					.getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
