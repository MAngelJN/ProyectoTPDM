package mx.edu.ittepic.gameofarrows;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

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

        imgR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nombre.getText().toString())){
                    nombre.requestFocus();
                    nombre.setError("El nombre no puede ser vacio");
                }
                if (TextUtils.isEmpty(alias.getText().toString())){
                    alias.requestFocus();
                    alias.setError("El username no puede ser vacio");
                }
                if (TextUtils.isEmpty(cel.getText().toString())){
                    cel.requestFocus();
                    cel.setError("El Numero telefonico no puede ser vacio");
                }
                if (TextUtils.isEmpty(cel2.getText().toString())){
                    cel2.requestFocus();
                    cel2.setError("El Numero telefonico no puede ser vacio");
                }
                if(!cel.equals(cel2)){
                    cel.requestFocus();
                    cel.setError("Los numeros telefonicos no coinciden");
                    cel2.setError("Los numeros telefonicos no coinciden");
                }

                /*if(cel.equals(cel2)){
                        ConexionWeb conexionWeb = new ConexionWeb(Login.this);
                        conexionWeb.agregarVariables("usuario",alias.getText().toString());
                        conexionWeb.agregarVariables("contra",contra.getText().toString());
                        try {
                            URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/login.php");
                            conexionWeb.execute(url);
                        }catch (MalformedURLException e){
                            new AlertDialog.Builder(PantallaRegistro.this).setMessage(e.getMessage()).setTitle("Error").show();
                        }
                    }*/
            }
        });
    }
}
