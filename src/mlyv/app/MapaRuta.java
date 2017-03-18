package mlyv.app;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MapaRuta extends FragmentActivity {

	private GoogleMap map;
	private GMapV2Direction md;

	String lat = "19.4356";
	String lon = "-99.141161";
	String nombre;

	double latDestino;
	double lonDestino;

	LatLng posUsuario;
	LatLng posDestino;

	Polyline ruta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles);

		md = new GMapV2Direction();
		
		Intent i = getIntent();
		Bundle extras = i.getExtras();

		latDestino = extras.getDouble("lat");
		lonDestino = extras.getDouble("lon");
		nombre = extras.getString("nombre");

		Log.d("lat", "" + latDestino);
		Log.d("lon", "" + lonDestino);

		posDestino = new LatLng(latDestino, lonDestino);

		double latUser = Double.parseDouble(lat);
		double lonUser = Double.parseDouble(lon);

		posUsuario = new LatLng(latUser, lonUser);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.the_map)).getMap();

		map.addMarker(new MarkerOptions().position(posUsuario).title("Tú")
				.snippet("Usted esta aquí")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.hombre)));

		map.addMarker(new MarkerOptions().position(posDestino).title(nombre)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.lugares)));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(posUsuario, 15));

		Log.d("destino", posDestino.latitude+" ,"+posDestino.longitude);
		
		trazarRuta(posDestino);
	}

	private void trazarRuta(LatLng destino) {

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
}
