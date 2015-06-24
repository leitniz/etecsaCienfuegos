package cu.leitniz.etecsa_cfg;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class AdaptadorBD {

	public static final String KEY_IDFILA = "_id";
	public static final String KEY_CEN_TEL = "Central_Telefonica";
	public static final String KEY_CABLE = "Cable";
	public static final String KEY_PAR = "Par";
	public static final String KEY_ESTADO = "Estado";
	public static final String KEY_OBSERVACIONES = "Observaciones";
	public static final String KEY_TERMINAL = "Terminal";
	public static final String KEY_INICIO = "Inicio";
	public static final String KEY_FIN = "Fin";
	public static final String KEY_DIR_TERM = "Direccion_Terminal";
	public static final String KEY_SERVICIO = "Servicio";
	public static final String KEY_EXT = "Ext";
	public static final String KEY_NOMBRE = "Nombre";
	public static final String KEY_DIRECCION = "Direccion";
	public static final String KEY_CIR_LIN = "Circuito_Linea";
			
	private static final String TAG = "AdaptadorBD";
	private static final String NOMBRE_BASEDATOS = "etecsa_cfg.db";
	private static final String TABLA_BASEDATOS = "etecsa";
	private static final int VERSION_BASEDATOS = 1;
	private static final String CREAR_BASEDATOS = "create table etecsa (_id integer primary key autoincrement, "
												+ "Central_Telefonica text not null, Cable	text not null, Par number not null, Estado text not null, Observaciones text, Terminal text not null, Inicio number not null, Fin number not null, Direccion_Terminal text, Servicio text not null, Ext text, Nombre text, Direccion text, Circuito_Linea text);";
	private static final String DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString()+ "/etecsa";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;

	public AdaptadorBD(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
				 
		@Override
		public SQLiteDatabase getReadableDatabase() {
			db = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH
		            + File.separator + NOMBRE_BASEDATOS, null,
		            SQLiteDatabase.OPEN_READONLY);
		    return db;			
		}
		@Override
		public SQLiteDatabase getWritableDatabase() {
			db = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH
		            + File.separator + NOMBRE_BASEDATOS, null,
		            SQLiteDatabase.OPEN_READWRITE);
		    return db;
		}

		DatabaseHelper(Context context) {
			super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREAR_BASEDATOS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Actualizando base de datos de versi�n " + oldVersion + " a "
					+ newVersion + ", lo que destruir� todos los viejos datos");
			db.execSQL("DROP TABLE IF EXISTS contactos");
			onCreate(db);
		}
	}
	// ---abrir la base de datos---
	public AdaptadorBD abrir() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---cerrar la base de datos---
	public void cerrar() {
		DBHelper.close();
	}

	// ---insertar un contacto en la base de datos---
	public long insertarContacto( String Central_Telefonica,String Cable,int Par, String Estado,
								  String Observaciones, String Terminal, int Inicio, int Fin,
								  String Direccion_Terminal, String Servicio, String Ext, 
								  String Nombre, String	Direccion, String Circuito_Linea) {
		ContentValues valoresIniciales = new ContentValues();
		valoresIniciales.put(KEY_CEN_TEL, Central_Telefonica);
		valoresIniciales.put(KEY_CABLE, Cable);
		valoresIniciales.put(KEY_PAR, Par);
		valoresIniciales.put(KEY_ESTADO, Estado);
		valoresIniciales.put(KEY_OBSERVACIONES, Observaciones);
		valoresIniciales.put(KEY_TERMINAL, Terminal);
		valoresIniciales.put(KEY_INICIO, Inicio);
		valoresIniciales.put(KEY_FIN, Fin);
		valoresIniciales.put(KEY_DIR_TERM, Direccion_Terminal);
		valoresIniciales.put(KEY_SERVICIO, Servicio);
		valoresIniciales.put(KEY_EXT, Ext);
		valoresIniciales.put(KEY_NOMBRE, Nombre);
		valoresIniciales.put(KEY_DIRECCION,Direccion);
		valoresIniciales.put(KEY_CIR_LIN, Circuito_Linea);
		
		return db.insert(TABLA_BASEDATOS, null, valoresIniciales);
	}

	// ---borrar un contacto en concreto---
	public boolean borrarContacto(long _id) {
		return db.delete(TABLA_BASEDATOS, KEY_IDFILA + "=" + _id, null) > 0;
	}
	// ---borrar todos los contacto ---
		public boolean borrarTodosContacto() {
			return db.delete(TABLA_BASEDATOS, null, null) > 0;
		}

	// ---recuperar todos los contactos---
	public Cursor obtenerTodosLosContactos() {
		return db.query(TABLA_BASEDATOS, new String[] { KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO, 
				KEY_OBSERVACIONES,KEY_TERMINAL,KEY_INICIO,KEY_FIN,KEY_DIR_TERM,KEY_SERVICIO,
				KEY_EXT, KEY_NOMBRE, KEY_DIRECCION, KEY_CIR_LIN}, null, null, null, null, null);
	}

	// ---recuperar un contacto en concreto---
	public Cursor obtenerContacto(long _id) throws SQLException {
		Cursor mCursor = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR,
				KEY_ESTADO, KEY_OBSERVACIONES, KEY_TERMINAL ,KEY_INICIO,KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT,
				KEY_NOMBRE, KEY_DIRECCION, KEY_CIR_LIN}, KEY_IDFILA + "=" + _id, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ---actualizar un contacto---
	public boolean actualizarContacto( long _id, String Central_Telefonica,String Cable,int Par, String Estado, 
									   String Observaciones, int Terminal, int Inicio, int Fin, 
									   String Direccion_Terminal, String Servicio, String Ext, 
									   String Nombre, String Direccion, String Circuito_Linea) {
		ContentValues args = new ContentValues();
		args.put(KEY_CEN_TEL, Central_Telefonica);
		args.put(KEY_CABLE, Cable);
		args.put(KEY_PAR, Par);
		args.put(KEY_ESTADO, Estado);
		args.put(KEY_OBSERVACIONES, Observaciones);
		args.put(KEY_TERMINAL, Terminal);
		args.put(KEY_INICIO, Inicio);
		args.put(KEY_FIN, Fin);
		args.put(KEY_DIR_TERM, Direccion_Terminal);
		args.put(KEY_SERVICIO, Servicio);
		args.put(KEY_EXT, Ext);
		args.put(KEY_NOMBRE, Nombre);
		args.put(KEY_DIRECCION,Direccion);
		args.put(KEY_CIR_LIN, Circuito_Linea);
		return db.update(TABLA_BASEDATOS, args, KEY_IDFILA + "=" + _id, null) > 0;
	}

	// ---buscar un contacto en concreto---
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Cursor buscarContactoServicio(String servicio) throws SQLException {
        Cursor mCursor = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR,
                        KEY_ESTADO, KEY_OBSERVACIONES, KEY_TERMINAL ,KEY_INICIO,KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT,
                        KEY_NOMBRE, KEY_DIRECCION, KEY_CIR_LIN}, KEY_SERVICIO + "=?",
                new String[]{servicio}, KEY_SERVICIO, null, null, null);
		if (mCursor != null)
            mCursor.moveToFirst();
            Log.d("EtecsaActivity", "Cursor: " + mCursor.getCount());


        return mCursor;
		
	}
	// ---busca todas las centrales telfonicas
	public Cursor buscarTodosCT(){		
		return db.query(TABLA_BASEDATOS, new String[] {KEY_CEN_TEL}, null, null, KEY_CEN_TEL, null, null, null);
	}
	// ---busca todos los cables dada una central telfonica
	public Cursor buscarTodosCable(String ct) throws SQLException {
		
		Cursor cable = db.query(true, TABLA_BASEDATOS, new String[] {KEY_CABLE}, KEY_CEN_TEL + "=?",
								new String[]{ct}, KEY_CABLE, null, null, null);
		if (cable  != null) 
			cable.moveToFirst();		
		return cable;
	}
	// ---busca todos los cables dada una central telfonica
	public Cursor buscarTodosTerminal(String ct,String cable) throws SQLException {
		Cursor terminal = db.query(true, TABLA_BASEDATOS, new String[] {KEY_TERMINAL},
								   KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=?", 
								   new String[] {ct, cable}, KEY_TERMINAL, null, null, null);
		if (terminal != null) 
			terminal.moveToFirst();		
		return terminal;
	}
	// ---busca todos los cables dada una central telfonica
	public Cursor buscarAvanzada(String ct, String cable, String par, String terminal, String circ_lin) throws SQLException {
		
		Cursor c;
		if(!ct.isEmpty() && cable.isEmpty() && par.isEmpty() && terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=?",
			  	    new String[] {ct}, null, null, null, null);
		}		
		else if(!ct.isEmpty() && !cable.isEmpty() && par.isEmpty() && terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=?",
			  	    new String[] {ct, cable}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && !par.isEmpty() && terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_PAR + "=?",
			  	    new String[] {ct, cable, par}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && !par.isEmpty() && !terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_PAR + "=? AND " + KEY_TERMINAL +"=?",
			  	    new String[] {ct, cable, par, terminal}, KEY_SERVICIO, null, null, null);
		}
		else if(ct.isEmpty() && cable.isEmpty() && !par.isEmpty() && terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_PAR + "=?",
			  	    new String[] {par}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && cable.isEmpty() && !par.isEmpty() && terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_PAR + "=?" ,
			  	    new String[] {ct, par}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && par.isEmpty() && !terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_TERMINAL + "=?",
			  	    new String[] {ct, cable, terminal}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && !par.isEmpty() && !terminal.isEmpty() && circ_lin.length() < 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_PAR + "=? AND " + KEY_TERMINAL + "=?",
			  	    new String[] {ct, cable, par, terminal}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && !par.isEmpty() && !terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_PAR + "=? AND " + KEY_TERMINAL + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {ct, cable, par, terminal, circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(ct.isEmpty() && cable.isEmpty() && par.isEmpty() && terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CIR_LIN + "=?",
			  	    new String[] {circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(ct.isEmpty() && cable.isEmpty() && !par.isEmpty() && terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_PAR + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {par, circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && par.isEmpty() && terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {ct, cable, circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && cable.isEmpty() && par.isEmpty() && terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {ct, circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && par.isEmpty() && !terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_TERMINAL + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {ct, cable, terminal, circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && !cable.isEmpty() && !par.isEmpty() && terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_CABLE + "=? AND " + KEY_PAR + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {ct, cable, par, circ_lin}, KEY_SERVICIO, null, null, null);
		}
		else if(!ct.isEmpty() && cable.isEmpty() && !par.isEmpty() && terminal.isEmpty() && circ_lin.length() > 12){
			c = db.query(true, TABLA_BASEDATOS, new String[] {KEY_IDFILA, KEY_CEN_TEL, KEY_CABLE, KEY_PAR, KEY_ESTADO,
				    KEY_OBSERVACIONES,KEY_TERMINAL, KEY_INICIO, KEY_FIN, KEY_DIR_TERM, KEY_SERVICIO, KEY_EXT},
			  	    KEY_CEN_TEL + "=? AND " + KEY_PAR + "=? AND " + KEY_CIR_LIN + "=?",
			  	    new String[] {ct, par, circ_lin}, KEY_SERVICIO, null, null, null);
		}				
		else{			
			c = null;
		}
		if (c != null) 
			c.moveToFirst();		
		return c;
	}
}