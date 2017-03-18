package mlyv.app;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class Menu extends Activity {

	int itinerario;
	Button btnRevisar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		// Inicializar boton de itinerario
		btnRevisar = (Button) findViewById(R.id.btnRevisa);
		btnRevisar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intencion = new Intent(Menu.this, Horario.class);
				startActivity(intencion);
			}
		});

		// Inicializar y onClick boton Aceptar
		Button botonPersonalizar = (Button) findViewById(R.id.botonPersonalizar);
		botonPersonalizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intencion = new Intent(Menu.this, Atributos.class);
				startActivity(intencion);
			}
		});

		// Inicializar boton Que Hago
		Button botonQueHago = (Button) findViewById(R.id.botonQueHago);
		botonQueHago.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intencion = new Intent(Menu.this, Plan.class);
				startActivity(intencion);
			}
		});

		// Inicializar boton de Configuración
		ImageButton ImgBtnConf = (ImageButton) findViewById(R.id.imageButtonConf);
		ImgBtnConf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(Menu.this, Configuracion.class);
				startActivity(in);
			}
		});

		// Inicializar boton de info
		ImageButton ImgBtnInfo = (ImageButton) findViewById(R.id.imageButtonAbout);
		ImgBtnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(Menu.this, AcercaDe.class);
				startActivity(in);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		SharedPreferences prefUser = getSharedPreferences("User",
				Context.MODE_PRIVATE);

		itinerario = Integer.parseInt(prefUser.getString("itinerario", "0"));
		if (itinerario == 0) {
			btnRevisar.setVisibility(View.INVISIBLE);
		}
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Quieres salir y cerrar la sesión?")
				.setTitle("Salir")
				.setCancelable(false)
				.setPositiveButton("Salir",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
								dialog.dismiss();
								dialog.cancel();
							}
						});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
}
