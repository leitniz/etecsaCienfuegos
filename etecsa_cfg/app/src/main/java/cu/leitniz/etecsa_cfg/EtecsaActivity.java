package cu.leitniz.etecsa_cfg;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

	
public class EtecsaActivity extends Activity implements OnClickListener, OnCheckedChangeListener, OnItemSelectedListener {

	   
	AdaptadorBD db;
	TextView servicio, terminalT, parT; 
	EditText central, telefono, par, ne, cl1, cl2, cl3;
	CheckBox checkbox;
	Spinner  central_telf, cable, terminal;
	Button buscar;
	LinearLayout avanzada1, avanzada2, avanzada3, avanzada4, avanzada5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		servicio		= (TextView)findViewById(R.id.servicio);
		central 		= (EditText)findViewById(R.id.central);		
		telefono 		= (EditText)findViewById(R.id.telefono);
		checkbox 		= (CheckBox)findViewById(R.id.check);		
		central_telf 	= (Spinner)findViewById(R.id.central_telf);
		cable 			= (Spinner)findViewById(R.id.cable);
		par 			= (EditText)findViewById(R.id.par);
		parT			= (TextView)findViewById(R.id.parT);
	 	terminal 		= (Spinner)findViewById(R.id.terminal);
	 	terminalT		= (TextView)findViewById(R.id.terminalT);
		ne				= (EditText)findViewById(R.id.ne);
	 	cl1 			= (EditText)findViewById(R.id.cl1);
		cl2				= (EditText)findViewById(R.id.cl2);
		cl3 			= (EditText)findViewById(R.id.cl3);
		buscar 			= (Button)findViewById(R.id.buscar);	
		avanzada1		= (LinearLayout)findViewById(R.id.avanzada1);
		avanzada2		= (LinearLayout)findViewById(R.id.avanzada2);
		avanzada3		= (LinearLayout)findViewById(R.id.avanzada3);
		avanzada4		= (LinearLayout)findViewById(R.id.avanzada4);
		avanzada5		= (LinearLayout)findViewById(R.id.avanzada5);
		buscar.setOnClickListener(this);
		checkbox.setOnCheckedChangeListener(this);
		central_telf.setOnItemSelectedListener(this);
		cable.setOnItemSelectedListener(this);
		terminal.setOnItemSelectedListener(this);
		
		for (int i = 0; i < avanzada1.getChildCount(); i++) 
			avanzada1.getChildAt(i).setEnabled(false);
		for (int i = 0; i < avanzada2.getChildCount(); i++) 
			avanzada2.getChildAt(i).setEnabled(false);
		for (int i = 0; i < avanzada3.getChildCount(); i++) 
			avanzada3.getChildAt(i).setEnabled(false);
		for (int i = 0; i < avanzada4.getChildCount(); i++) 
			avanzada4.getChildAt(i).setEnabled(false);
		for (int i = 0; i < avanzada5.getChildCount(); i++) 
			avanzada5.getChildAt(i).setEnabled(false);
		
		db = new AdaptadorBD(this);
				
		// BASE DE DATOS 
		//---a�adir contactos---
	/*	db.abrir();
			db.borrarTodosContacto();
			db.insertarContacto("CRUCES", "CC1", 1, "O", null, "1", 1, 3, "OFICINAS PROCUBA", "CC 572685", null, "GRANJA AGROPECUARIA RAMON BALBOA", "Carre Cfgos. Apto. Piso. e/.","NE 002-06-200");
			db.insertarContacto("CRUCES", "CC1", 2, "O", null, "1", 1, 3, "OFICINAS PROCUBA", "CC 573383", null, "GRANJA AGROPECUARIA RAMON BALBOA", "Carre Cfgos. Apto. Piso. e/.","NE 002-02-300");
			db.insertarContacto("CRUCES", "CC2", 3, "O", null, "1", 1, 3, "OFICINAS PROCUBA", "CC 573112", null, "GRANJA AGROPECUARIA RAMON BALBOA", "Carre Cfgos. Apto. Piso. e/.","NE 002-06-150");
			db.insertarContacto("CIENFUEGOS", "CF1", 11, "O", null, "A", 11, 14, "RADIO CUBA", "CF 513109", null, "GRANJA AGROPECUARIA RAMON BALBOA", "Carre Cfgos. Apto. Piso. e/.","NE 002-02-200");
			db.insertarContacto("CIENFUEGOS", "CF2", 101, "l", "CAMBIO DE PAR POR FN51", "10", 1, 6, "RADIO CUBA", "CF 512215", null, "GRANJA AGROPECUARIA RAMON BALBOA", "Carre Cfgos. Apto. Piso. e/.","NE 002-04-202");
			
	 	db.cerrar();	
	*/	
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean checkeado)
	{
		if(checkeado == true)
		{
			for (int i = 0; i < avanzada2.getChildCount(); i++) 
				avanzada2.getChildAt(i).setEnabled(true);
			for (int i = 0; i < avanzada3.getChildCount(); i++) 
				avanzada3.getChildAt(i).setEnabled(true);
			for (int i = 0; i < avanzada4.getChildCount(); i++) 
				avanzada4.getChildAt(i).setEnabled(true);
			for (int i = 0; i < avanzada5.getChildCount(); i++) 
				avanzada5.getChildAt(i).setEnabled(true);
			servicio.setEnabled(false);
			central.setEnabled(false);
			telefono.setEnabled(false);				
			loadSpinnerCT();
			cable.setEnabled(false);
			terminal.setEnabled(false);
		}
		else
		{
			/* deshabilitando las opciones avanzadas*/
			for (int i = 0; i < avanzada2.getChildCount(); i++) 
				avanzada2.getChildAt(i).setEnabled(false);
			for (int i = 0; i < avanzada3.getChildCount(); i++) 
				avanzada3.getChildAt(i).setEnabled(false);
			for (int i = 0; i < avanzada4.getChildCount(); i++) 
				avanzada4.getChildAt(i).setEnabled(false);
			for (int i = 0; i < avanzada5.getChildCount(); i++) 
				avanzada5.getChildAt(i).setEnabled(false);
			/* limpiando los editables*/
			par.setText("");
			cl1.setText("");
			cl2.setText("");
			cl3.setText("");
			/* limpiando spinners*/
			final ArrayAdapter<String> clear = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String [] {""});
			central_telf.setAdapter(clear);			
			cable.setAdapter(clear);
			terminal.setAdapter(clear);
			/* habilitando la busqueda basica*/
			servicio.setEnabled(true);
			central.setEnabled(true);
			telefono.setEnabled(true);			
		}
	}

	private void loadSpinnerCT() {
		db.abrir();
		final ArrayList<String> opcionesCt = new ArrayList<String>();
		opcionesCt.add("");
		Cursor cursor = db.buscarTodosCT();
		if (cursor != null && cursor.moveToFirst())
		{			
			do{
				opcionesCt.add(cursor.getString(0));					
			}while (cursor.moveToNext());
		}
		ArrayAdapter<String> adapterCT = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opcionesCt);
		central_telf.setAdapter(adapterCT);
		db.cerrar();
	}
	private void loadSpinnerCable(String ct) {
		db.abrir();
		final ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("");
		Cursor cursor = db.buscarTodosCable(ct);
		if (cursor != null){
			cursor.moveToFirst();
			do{
				opciones.add(cursor.getString(0));					
			}while (cursor.moveToNext());
		}
		ArrayAdapter<String> adapterCable = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
		cable.setAdapter(adapterCable);
		db.cerrar();
	}
	private void loadSpinnerTerminal(String ct, String cable) {
		db.abrir();
		final ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("");
		Cursor cursor = db.buscarTodosTerminal(ct, cable);
		if (cursor != null){
			cursor.moveToFirst();
			do{
				opciones.add(cursor.getString(0));					
			}while (cursor.moveToNext());
		}
		ArrayAdapter<String> adapterTerminal = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
		terminal.setAdapter(adapterTerminal);
		db.cerrar();
	}
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buscar:
				if(checkbox.isChecked() == false){
					String central1 = central.getText().toString();
					String telefono1 = telefono.getText().toString();
					if( !central1.isEmpty() && !telefono1.isEmpty())
					{
					    db.abrir();
					    String servicio = central1 + " " + telefono1;
						Cursor cursor = db.buscarContactoServicio(servicio);

						Singlenton.getInstance().setCursor(cursor);
						if (cursor.moveToFirst())
						{							
							db.cerrar();							
							Intent intent = new Intent("cu.leitniz.etecsa_cfg.RESULTADO");
							startActivity(intent);
						}
						else{
							Toast.makeText(this,"No se encuentra ningún resultado.", Toast.LENGTH_LONG).show();
							db.cerrar();
						}					
					}
					else{
						Toast.makeText(this,"No ha entrado todos los datos.", Toast.LENGTH_LONG).show();
						db.cerrar();
					}
				}
				else {
				    db.abrir();
		    		String circ_lin = ne.getText().toString() + " " +
	    							  cl1.getText().toString() + "-"+
	    							  cl2.getText().toString() + "-"+ 
	    							  cl3.getText().toString();
		    		Cursor cursor;
		    		if(cable.isEnabled() && terminal.isEnabled()){
						cursor = db.buscarAvanzada(central_telf.getSelectedItem().toString(),
								cable.getSelectedItem().toString(),
								par.getText().toString(),
								terminal.getSelectedItem().toString(),
								circ_lin);
		    		}
		    		else if(cable.isEnabled() && !terminal.isEnabled()){
		    			cursor = db.buscarAvanzada(central_telf.getSelectedItem().toString(),
		    					cable.getSelectedItem().toString(),
		    					par.getText().toString(),
	    						"",
	    						circ_lin);
		    		}
		    		else{
		    			cursor = db.buscarAvanzada(central_telf.getSelectedItem().toString(),
								"",
								par.getText().toString(),
								"",
								circ_lin);
		    		}
                    Log.d("EtecsaActivity", "Cursor: " + cursor.getCount());

                    Singlenton.getInstance().setCursor(cursor);
					if(cursor != null && cursor.moveToFirst()){						
						db.cerrar();							
						Intent intent = new Intent("cu.leitniz.etecsa_cfg.RESULTADO");
						startActivity(intent);						
					}
					else{
						db.cerrar();
						Toast.makeText(this,"No se encuentra ning�n resultado.", Toast.LENGTH_LONG).show();						
					}				
					db.cerrar();												
				}
				break;
			default:
				Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
				break;
		}		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		final ArrayAdapter<String> clear = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String [] {""});
		
		switch (arg0.getId()) {
		case R.id.central_telf:
			String ct1 = arg0.getSelectedItem().toString();
			/* limpiando spinners*/
			cable.setAdapter(clear);
			terminal.setAdapter(clear);
			
			cable.setEnabled(false);
			terminal.setEnabled(false);
			if (!ct1.isEmpty()){
				loadSpinnerCable(ct1);
				cable.setEnabled(true);				
			}
			else{
				cable.setAdapter(clear);			
				cable.setEnabled(false);
			}
			terminal.setEnabled(false);
			terminal.setAdapter(clear);
			
			
		break;
		case R.id.cable:
			String ct2 = central_telf.getSelectedItem().toString();
			String cable = arg0.getSelectedItem().toString();
			terminal.setAdapter(null);
			if(!ct2.isEmpty()&& !cable.isEmpty())
			{
				loadSpinnerTerminal(ct2,cable);
				terminal.setEnabled(true);
			}
			else{
				terminal.setEnabled(false);
				terminal.setAdapter(clear);
			}
			
			break;
		case R.id.terminal:
			break;			
	}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}

