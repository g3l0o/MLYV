package mlyv.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread splashlogo =  new Thread (){
			@Override
			public void run() {
				try {
					sleep (3000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}finally{
					finish ();
					Intent in = new Intent(MainActivity.this, Inicio.class);
					startActivity(in);    
				}
			}
		};
				
		splashlogo.start();
	}

}