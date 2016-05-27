package mx.edu.ittepic.gameofarrows;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Opciones extends AppCompatActivity {

    EditText ca,nc,nc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        ca = (EditText) findViewById(R.id.editText6);
        nc = (EditText) findViewById(R.id.editText7);
        nc2 = (EditText) findViewById(R.id.editText8);

        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        ca.setTypeface(f);
        nc.setTypeface(f);
        nc2.setTypeface(f);
    }
}
