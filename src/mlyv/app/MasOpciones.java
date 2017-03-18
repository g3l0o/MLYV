package mlyv.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MasOpciones extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mas_opciones);
		
		//inicializar día X
		TextView diaDinamico = (TextView) findViewById(R.id.textoDinamicoLugar);
		diaDinamico.setText("Lugar X");
		
		
	}

}
