package mx.edu.ittepic.gameofarrows;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    //algo mas bebe te amo :*

    ImageView entrar,logo;
    EditText alias, contra;
    TextView registro, con;
    String respuesta;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entrar = (ImageView) findViewById(R.id.imageView4);
        alias = (EditText) findViewById(R.id.editText);
        contra = (EditText) findViewById(R.id.editText2);
        registro = (TextView) findViewById(R.id.textView);
        con = (TextView) findViewById(R.id.textView2);
        mp = MediaPlayer.create(this, R.raw.musica);
        mp.start();

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(alias.getText().toString())) {
                    alias.requestFocus();
                    alias.setError("Usuario no puede ser vacio");
                    return;
                }
                if (TextUtils.isEmpty(contra.getText().toString())) {
                    contra.requestFocus();
                    contra.setError("Contraseña no puede ser vacio");
                    return;
                }
                if (!(Pattern.compile("^[a-zA-Z0-9_]+$").matcher(alias.getText().toString()).matches())) {
                    alias.requestFocus();
                    alias.setError("Usuario no puedo contener espacios o caracteres especiales.");
                } else
                 {
                    ConexionWeb conexionWeb = new ConexionWeb(Login.this);
                    conexionWeb.agregarVariables("usuario", alias.getText().toString().toLowerCase());
                    conexionWeb.agregarVariables("contra", contra.getText().toString());
                    try {
                        URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/login.php");
                        conexionWeb.execute(url);
                    } catch (MalformedURLException e) {
                        new AlertDialog.Builder(Login.this).setMessage(e.getMessage()).setTitle("Error").show();
                    }
                }

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
        con.setTypeface(f);
    }

    public void abrirRegistro(){startActivity(new Intent(this,PantallaRegistro.class));}
    public void abrirPrincipal(){startActivity(new Intent(this,Principal.class));mp.pause();}

    public void resultado(String res){
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
        if(res.startsWith("1")){
            abrirPrincipal();
            alias.setText("");
            contra.setText("");
           //Toast.makeText(Login.this,"CORRECTO",Toast.LENGTH_LONG);
        }
        if(res.startsWith("0")){
            //alert.setTitle("Error de autenticacion").setMessage("Username y/o contraseña incorrectas").show();
            Toast.makeText(Login.this,"Username y/o contraseña incorrectas",Toast.LENGTH_LONG).show();
            alias.requestFocus();
        }


        /*alert.setTitle("Respuesta desde servidor")
                .setMessage(res)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();*/

    }

}
