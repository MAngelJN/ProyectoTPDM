package mx.edu.ittepic.gameofarrows;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    //algo mas bebe te amo :*

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
                ConexionWeb conexionWeb = new ConexionWeb(Login.this);
                conexionWeb.agregarVariables("usuario",alias.getText().toString());
                conexionWeb.agregarVariables("contra",contra.getText().toString());
                try {
                    URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/login.php");
                    conexionWeb.execute(url);
                }catch (MalformedURLException e){
                    new AlertDialog.Builder(Login.this).setMessage(e.getMessage()).setTitle("Error").show();
                }

                //abrirPrincipal();
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

    public void mostrarResultado(String res){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if(res.startsWith("Error_404_2")){
            res = "Error al enviar o recibir informacion con el php";
        }
        if(res.startsWith("Error_404_1")){
            res = "Hosting no encontrado";
        }
        if(res.startsWith("Error_404")){
            res = "Al parecer no existe el php buscado";
        }


        alert.setTitle("Respuesta desde servidor")
                .setMessage(res)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}
