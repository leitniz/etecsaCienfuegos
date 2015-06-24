package cu.leitniz.etecsa_cfg;

import android.database.Cursor;

public class Singlenton {

	public Cursor cursor;
	private static Singlenton mInstance = null;
	public static Singlenton getInstance(){
		if(mInstance == null)
			mInstance = new Singlenton();
		return mInstance;
	}
	public Singlenton(){
		cursor = null;
	}
	public Cursor getCursor(){
		return this.cursor;
	}
	public void setCursor(Cursor c){
		cursor = c;
	}
	
}
