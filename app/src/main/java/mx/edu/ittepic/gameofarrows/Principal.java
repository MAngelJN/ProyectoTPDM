package mx.edu.ittepic.gameofarrows;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;

public class Principal extends AppCompatActivity {

    ImageView logo,img6,img7,img8,img9;
    Switch sonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        logo = (ImageView) findViewById(R.id.imageView5);
        img6 = (ImageView) findViewById(R.id.imageView6);
        img7 = (ImageView) findViewById(R.id.imageView7);
        img8 = (ImageView) findViewById(R.id.imageView8);
        img9 = (ImageView) findViewById(R.id.imageView9);
        sonido = (Switch) findViewById(R.id.switch1);

        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        sonido.setTypeface(f);
    }
}
