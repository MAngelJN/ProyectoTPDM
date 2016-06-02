package mx.edu.ittepic.gameofarrows;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;


public class Principal extends AppCompatActivity {

    ImageView logo,img6,img7,img8,opciones;
    Switch sonido;
    MediaPlayer mp;
    String username;
    //Login pincheTapiaTonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        img6 = (ImageView) findViewById(R.id.imageView6);
        img7 = (ImageView) findViewById(R.id.imageView7);
        img8 = (ImageView) findViewById(R.id.imageView8);
        opciones = (ImageView) findViewById(R.id.imageView9);
        sonido = (Switch) findViewById(R.id.switch1);
        mp = MediaPlayer.create(this, R.raw.musica);
        Bundle b = getIntent().getExtras();
        username = b.getString("user");
        //mp.start();
        //pincheTapiaTonto = new Login();

        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        sonido.setTypeface(f);

        opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirOpciones();
            }
        });


        if(sonido.isChecked()){
            if(!mp.isPlaying()){
                //mp.start();
            }
        }
        else {
            if(mp.isPlaying()){
                mp.pause();
            }
        }

        sonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //mp.start();
                }else{
                    mp.pause();
                }
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirNormal();
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRecords();
            }
        });

    }

    private void abrirOpciones(){startActivity(new Intent(this,Opciones.class).putExtra("user",username));}
    private void abrirNormal(){startActivity(new Intent(this,Juego.class).putExtra("user",username));}
    private void abrirRecords(){startActivity(new Intent(this, Records.class));}


}
