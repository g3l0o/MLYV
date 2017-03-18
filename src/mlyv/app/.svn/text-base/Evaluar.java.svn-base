package mlyv.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Evaluar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluar);
		
		//Inicializar y onClick boton Omitir 
				TextView botonOmitir = (TextView) findViewById(R.id.botonOmitir);
				botonOmitir.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		                    Intent intencion = new Intent (Evaluar.this, Menu.class);
		                    startActivity(intencion);
		                    }
		            });
				
				//Inicializar boton Aceptar
				TextView botonAceptar = (TextView) findViewById(R.id.botonAceptarEv);
				botonAceptar.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		                    Intent intencion = new Intent (Evaluar.this, Menu.class);
		                    startActivity(intencion);
		            }
		       });
	}

}
