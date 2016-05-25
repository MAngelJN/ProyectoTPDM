package mx.edu.ittepic.gameofarrows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Login extends AppCompatActivity {
    ImageView entrar,logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = (ImageView) findViewById(R.id.imageView3);
        entrar = (ImageView) findViewById(R.id.imageView4);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistro();
            }
        });

    }

    public void abrirRegistro(){startActivity(new Intent(this,PantallaRegistro.class));}

}
