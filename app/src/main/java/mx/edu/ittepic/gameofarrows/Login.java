package mx.edu.ittepic.gameofarrows;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    ImageView entrar,logo;
    EditText alias, contra;
    TextView registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = (ImageView) findViewById(R.id.imageView3);
        entrar = (ImageView) findViewById(R.id.imageView4);
        alias = (EditText) findViewById(R.id.editText);
        contra = (EditText) findViewById(R.id.editText2);
        registro = (TextView) findViewById(R.id.textView);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPrincipal();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistro();
            }
        });

        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        alias.setTypeface(f);
        contra.setTypeface(f);
        registro.setTypeface(f);

    }

    public void abrirRegistro(){startActivity(new Intent(this,PantallaRegistro.class));}
    public void abrirPrincipal(){startActivity(new Intent(this,Principal.class));}

}