package cu.leitniz.etecsa_cfg;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class Resultado extends Activity{
	// more efficient than HashMap for mapping integers to objects
	  SparseArray<Group> groups = new SparseArray<Group>();

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    setContentView(R.layout.resultado);
	    createData();
	    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
	    MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
	    listView.setAdapter(adapter);
	  }

	  public void createData() {
		
		Cursor cursor = Singlenton.getInstance().getCursor();  
	        int index = 0;
	        
	        if (cursor != null)
			{
	        	cursor.moveToFirst();
				do {
					Group group = new Group("Servicio: " + cursor.getString(10));
			        group.children.add("CT: " + cursor.getString(1));
			        group.children.add("Cable: " + cursor.getString(2));
			        group.children.add("Par: " + cursor.getString(3));
			        group.children.add("Estado: " + cursor.getString(4));
			        group.children.add("Terminal: " +  cursor.getString(6));
			        group.children.add("Inicio: " +  cursor.getString(7));
			        group.children.add("Fin: " +  cursor.getString(8));
			        group.children.add("Dir_Term: " +  cursor.getString(9));
			        groups.append(index++, group);
				} while (cursor.moveToNext());
			}
	  }
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case android.R.id.home: // ID del boton
	          finish(); // con finish terminamos el activity actual, con lo que volvemos
	                    // al activity anterior (si el anterior no ha sido cerrado)
	          return true;
	      }
	      return super.onOptionsItemSelected(item);
	  }
}
