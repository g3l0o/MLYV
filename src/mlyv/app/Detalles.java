package mlyv.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.os.Bundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v4.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

public class Detalles extends FragmentActivity {

	ArrayList<Lugar> Lugares = new ArrayList<Lugar>();

	int idRegreso;
	boolean existeRuta;
	Polyline ruta;

	private String lat;
	private String lon;
	private String idComida;
	private String idInteres;
	private String idTranporte;
	private String idUser;
	private GoogleMap map;
	private GMapV2Direction md;

	LatLng toPosition = new LatLng(19.412364, -99.173416);

	LatLng posUsuario;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles);

		md = new GMapV2Direction();
		existeRuta = false;

		setValues();
		getLugares();
		getInfo();

		double latUser = Double.parseDouble(lat);
		double lonUser = Double.parseDouble(lon);

		posUsuario = new LatLng(latUser, lonUser);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.the_map)).getMap();

		setMarcadores();

		map.addMarker(new MarkerOptions().position(posUsuario).title("Tú")
				.snippet("Usted esta aquí")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.persona)));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(posUsuario, 15));

		// obtener los detalles del lugar
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub

				if (!marker.getTitle().equals("Tú")) {

					// obtengo el id del lugar seleccionado
					int idLugar = getId(marker);

					Intent in = new Intent(Detalles.this, infoLugar.class);
					in.putExtra("nombre", marker.getTitle());
					in.putExtra("id", idLugar);
					in.putExtra("lat", marker.getPosition().latitude);
					in.putExtra("lon", marker.getPosition().longitude);
					in.putExtra("mapa", "no");
					startActivityForResult(in, 1);
				}
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detalles, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (existeRuta) {
			ruta.remove();
			existeRuta = false;
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (data.getExtras().containsKey("idRuta")) {
				idRegreso = data.getIntExtra("idRuta", 0);
				trazarRuta(idRegreso);
			}
		}
	}

	private void trazarRuta(int idDestino) {

		if (existeRuta) {
			ruta.remove();
		} else {
			existeRuta = true;
		}

		LatLng destino;
		double la = 0;
		double lo = 0;
		for (int i = 0; i < Lugares.size(); i++) {
			if (Integer.parseInt(Lugares.get(i).id) == idDestino) {
				la = Double.parseDouble(Lugares.get(i).lat);
				lo = Double.parseDouble(Lugares.get(i).lon);

				break;
			}
		}
		destino = new LatLng(la, lo);

		Document doc = md.getDocument(posUsuario, destino,
				GMapV2Direction.MODE_WALKING);

		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(
				Color.RED);

		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}

		ruta = map.addPolyline(rectLine);

	}

	private int getId(Marker marker) {
		Log.d("getId", "Entro");
		int id = 0;

		String nombre = marker.getTitle();

		for (int i = 0; i < Lugares.size(); i++) {
			Lugar lugMarcador = Lugares.get(i);
			String nom = lugMarcador.getNombre();

			boolean mismoNombre = nombre.compareTo(nom) == 0;

			if (mismoNombre) {
				Log.d("Positivo", "Positivo");
				id = Integer.parseInt(Lugares.get(i).getId());
				break;
			}
		}

		return id;
	}

	private void setMarcadores() {
		for (int i = 0; i < Lugares.size(); i++) {
			Lugar lugMarcador = Lugares.get(i);
			double latMarcador = Double.parseDouble(lugMarcador.getLat());
			double lonMarcador = Double.parseDouble(lugMarcador.getLon());
			LatLng pos = new LatLng(latMarcador, lonMarcador);
			String nombre = lugMarcador.getNombre();
			int tipo = Integer.parseInt(lugMarcador.getTipo());

			MarkerOptions opMarker = new MarkerOptions();
			opMarker.position(pos);
			opMarker.title(nombre);
			opMarker.snippet("Presiona para más info");

			switch (tipo) {
			// lugares
			case 1:
				opMarker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.lugares));
				break;
			// restaurantes
			case 2:
				opMarker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.restaurante));
				break;
			// metro
			case 3:
				opMarker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.metro));
				break;
			// metrobus
			case 4:
				opMarker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.metrobus));
				break;
			default:
				break;
			}
			map.addMarker(opMarker);
		}
	}

	private void getLugares() {
		// Variables necesarias para el funcionamiento
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse webServerResponse = null;
		InputStream inputStream = null;
		String result = "";
		URI myURI = null;
		String url = "";

		// http://mexlyv.net78.net/mexico/Victor/getNearPlaces.php?usuario=7&comida=69&intereses=2&transporte=Metro&latitud=19.4356&longitud=-99.141161
		url = "http://mexlyv.net78.net/mexico/Victor/getNearPlaces.php?";
		url += "usuario=" + idUser;
		url += "&comida=" + idComida;
		url += "&intereses=" + idInteres;
		url += "&transporte=" + idTranporte;
		url += "&latitud=" + lat;
		url += "&longitud=" + lon;

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
							String lat = com.nextToken();
							String lon = com.nextToken();
							String tipo = com.nextToken();

							Lugar lu = new Lugar(id, nombre, lat, lon, tipo);
							Lugares.add(lu);
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

	private void setValues() {
		// obtiene los datos de las preferencias
		SharedPreferences prefQueHago = getSharedPreferences("QueHago",
				Context.MODE_PRIVATE);
		SharedPreferences prefUser = getSharedPreferences("User",
				Context.MODE_PRIVATE);

		lat = prefQueHago.getString("lat", "");
		lon = prefQueHago.getString("lon", "");
		idComida = prefQueHago.getString("idComida", "");
		idInteres = prefQueHago.getString("idInteres", "");
		idTranporte = prefQueHago.getString("idTransporte", "");
		idUser = prefUser.getString("id", "");

		Log.d("lat Detalles", lat);
	}

	public void getInfo() {
		// Variables necesarias para el funcionamiento
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse webServerResponse = null;
		InputStream inputStream = null;
		String result = "";
		URI myURI = null;
		String url = "";

		// http://mexlyv.net78.net/mexico/Victor/getNearPlaces.php?usuario=7&comida=69&intereses=2&transporte=Metro&latitud=19.4356&longitud=-99.141161
		url = "http://mexlyv.net78.net/mexico/infoLugar.php?";
		url += "i=" + "91";

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
				StringTokenizer cat = new StringTokenizer(result, ";");
				cat.nextToken();
				/*
				 * costo = cat.nextToken(); menores = cat.nextToken();
				 * horaApertura = cat.nextToken(); horaCierre = cat.nextToken();
				 * calificacion = cat.nextToken(); descripcion =
				 * cat.nextToken(); tiempoAprox = cat.nextToken();
				 */

			} catch (Exception e) {
				Log.d("log_tag", "Error converting result " + e.toString());
			}
		}
	}
}