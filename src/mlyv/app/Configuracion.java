package mlyv.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Configuracion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);

		//Inicializar checkbox
		//check box Historia
	CheckBox chBoxHistoria = (CheckBox) findViewById(R.id.checkBoxHistoria);
	chBoxHistoria.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				//lo que debe hacer cuando es marcado
			} else {
				//lo que debe de hacer cuando no esta marcado
			}
		}
	});

		//check box Arte
	CheckBox chBoxArte = (CheckBox) findViewById(R.id.checkBoxArte);
	chBoxArte.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				//lo que debe hacer cuando es marcado
			} else {
				//lo que debe de hacer cuando no esta marcado
			}
		}
	});
	
		//check box Pintura
	CheckBox chBoxPintura = (CheckBox) findViewById(R.id.checkBoxPintura);
	chBoxPintura.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				//lo que debe hacer cuando es marcado
			} else {
				//lo que debe de hacer cuando no esta marcado
			}
		}
	});
		
		//check box Musica
	CheckBox chBoxMusica = (CheckBox) findViewById(R.id.checkBoxMusica);
	chBoxMusica.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				//lo que debe hacer cuando es marcado
			} else {
				//lo que debe de hacer cuando no esta marcado
			}
		}
	});
	
		//check box Moda
	CheckBox chBoxModa = (CheckBox) findViewById(R.id.checkBoxModa);
	chBoxModa.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				//lo que debe hacer cuando es marcado
			} else {
				//lo que debe de hacer cuando no esta marcado
			}
		}
	});
	
		//check box Plazas
	CheckBox chBoxPlazas = (CheckBox) findViewById(R.id.checkBoxPlazas);
	chBoxPlazas.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				//lo que debe hacer cuando es marcado
			} else {
				//lo que debe de hacer cuando no esta marcado
			}
		}
	});
	
	//Inicializar boton de aceptar 
	TextView botonAceptar = (TextView) findViewById(R.id.BotonAceptarConf);
	botonAceptar.setOnClickListener(new OnClickListener() {

		//FALTA verificar que los campos esten llenos correctamente
		public void onClick(View v) {
			Intent in = new Intent (Configuracion.this, Menu.class);
			startActivity(in);
		}
	});
	}

}
