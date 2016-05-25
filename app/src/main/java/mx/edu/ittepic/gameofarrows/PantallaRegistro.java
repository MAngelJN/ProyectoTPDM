package mx.edu.ittepic.gameofarrows;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class PantallaRegistro extends AppCompatActivity {

    EditText nombre,alias,cel,cel2;
    ImageView img2,imgR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);

        nombre = (EditText) findViewById(R.id.editText3);
        alias = (EditText) findViewById(R.id.fuente);
        cel = (EditText) findViewById(R.id.editText4);
        cel2 = (EditText) findViewById(R.id.editText5);
        img2 = (ImageView) findViewById(R.id.imageView);
        imgR = (ImageView) findViewById(R.id.imageView2);
        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        nombre.setTypeface(f);
        alias.setTypeface(f);
        cel.setTypeface(f);
        cel2.setTypeface(f);
    }
}
