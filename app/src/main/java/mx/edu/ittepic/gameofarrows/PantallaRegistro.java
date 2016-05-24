package mx.edu.ittepic.gameofarrows;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class PantallaRegistro extends AppCompatActivity {

    EditText nombre,alias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);

        nombre = (EditText) findViewById(R.id.editText3);
        alias = (EditText) findViewById(R.id.fuente);
        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        alias.setTypeface(f);
    }
}
