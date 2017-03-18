package mlyv.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Horario extends Activity {

	private AtomPayListAdapter adapter;
	ArrayList<AtomPayment> lugares;
	String id;
	String fecha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horario);
		
		lugares = new ArrayList<AtomPayment>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		SimpleDateFormat dateFormatServicio = new SimpleDateFormat("yyyy-MM-dd");
		
		// get current date time with Date()
		Date date = new Date();
		fecha = dateFormatServicio.format(date);

		// inicializar día X
		TextView diaDinamico = (TextView) findViewById(R.id.DiaX);
		diaDinamico.setText(dateFormat.format(date));

		setupListViewAdapter();

		adapter.insert(new AtomPayment("8", "Museo de Memoria y Tolerancia", "17:00", "19.43426", "-99.144136", "2"), 0);
		adapter.insert(new AtomPayment("24", "Catedral de la Ciudad de México", "18:00", "19.434179", "-99.132943", "5"), 0);
		adapter.insert(new AtomPayment("25", "Museo de la Caricatura", "19:00", "19.435808", "-99.132718", "2"), 0);

		getLugares();
	}
	
	public void getLugares(){
		SharedPreferences prefUser = getSharedPreferences("User", Context.MODE_PRIVATE);
		id = prefUser.getString("id", "0");
		Log.d("id: ", id);
		Log.d("fecha", fecha);
		String url = "http://mexlyv.net78.net/mexico/Victor/calendarDetail.php?" +
				"usuario=" + id +
				"&fecha="+ fecha;
		Log.d("url", url);
	}

	public void removeAtomPayOnClickHandler(View v) {
		AtomPayment item = (AtomPayment) v.getTag();
		
		Intent in = new Intent(Horario.this, infoLugar.class);
		in.putExtra("nombre", item.getName());
		in.putExtra("id", item.getId());
		in.putExtra("lat", item.getLat());
		in.putExtra("lon", item.getLon());
		in.putExtra("mapa", "si");
		startActivity(in);
	}

	private void setupListViewAdapter() {
		adapter = new AtomPayListAdapter(Horario.this,
				R.layout.atom_pay_list_item, new ArrayList<AtomPayment>());
		ListView atomPaysListView = (ListView) findViewById(R.id.listViewItinerario);
		atomPaysListView.setAdapter(adapter);
	}
}
