package mx.edu.ittepic.gameofarrows;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Records extends AppCompatActivity {

    ConexionBD bd;
    ListView lista;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    itemAdapter adaptador;

    int[] imagenes = {
            R.drawable.usu
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        lista = (ListView) findViewById(R.id.listView);

        bd = new ConexionBD(this,"GAMEOFARROWS",null,1);



        arrayList = new ArrayList<String>();


        buscar();
    }

    public void buscar(){
        try {
            SQLiteDatabase base = bd.getReadableDatabase();
            String sql = "SELECT NICKNAME,PUNTOS FROM JUGADORES ORDER BY PUNTOS DESC";
            Cursor cursor = base.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    arrayList.add(cursor.getString(0) + "     " + cursor.getString(1) + " Puntos");

                } while (cursor.moveToNext());
                //adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_layout, arrayList);
                //adapter = new ArrayAdapter<String>(this,R.layout.list_item_layout,arrayList);
                adaptador = new itemAdapter(this, arrayList, imagenes);

                //lista.setAdapter(adapter);
                lista.setAdapter(adaptador);
                base.close();
                }else{
                    Toast.makeText(Records.this, "No se encontró ranking de jugadores", Toast.LENGTH_SHORT).show();
                }

            base.close();
        }catch(SQLiteException sqle){
            new AlertDialog.Builder(this).setMessage("Error en la consulta: "+sqle.getMessage()).setTitle("ERROR").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

}
