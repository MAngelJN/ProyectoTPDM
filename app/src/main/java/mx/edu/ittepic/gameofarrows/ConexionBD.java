package mx.edu.ittepic.gameofarrows;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan on 01/06/2016.
 */
public class ConexionBD extends SQLiteOpenHelper {

    public ConexionBD(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE JUGADORES (NICKNAME VARCHAR(50) PRIMARY KEY,NOMBRE VARCHAR(100),PUNTOS INTEGER)");
        db.execSQL("INSERT INTO JUGADORES VALUES('NOSOYMELON','MIGUEL',100)");
        db.execSQL("INSERT INTO JUGADORES VALUES('NOSOYTAPIA','JUAN',90)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
