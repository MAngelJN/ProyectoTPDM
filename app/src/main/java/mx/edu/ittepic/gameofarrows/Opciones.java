package mx.edu.ittepic.gameofarrows;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Opciones extends AppCompatActivity {

    EditText ca,nc,nc2;
    ImageView aceptar;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        ca = (EditText) findViewById(R.id.editText6);
        nc = (EditText) findViewById(R.id.editText7);
        nc2 = (EditText) findViewById(R.id.editText8);
        aceptar = (ImageView) findViewById(R.id.imageView3);
        Bundle b = getIntent().getExtras();
        username = b.getString("user");

        Toast.makeText(this,username,Toast.LENGTH_LONG).show();

        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        ca.setTypeface(f);
        nc.setTypeface(f);
        nc2.setTypeface(f);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ca.getText().toString())){
                    ca.requestFocus();
                    ca.setError("Contraseña no puede ser vacío");
                }
                if(TextUtils.isEmpty(nc.getText().toString())){
                    nc.requestFocus();
                    nc.setError("Contraseña no puede ser vacío");
                }
                if(TextUtils.isEmpty(nc2.getText().toString())){
                    nc2.requestFocus();
                    nc2.setError("Verifique la contraseña");
                }
                if(ca.getText().toString().equals(nc.getText().toString())){
                    nc.requestFocus();
                    nc.setError("La contraseña es igual a la anterior");
                }
                if(nc.getText().toString().equals(nc2.getText().toString())){
                    ConexionWeb conexionWeb = new ConexionWeb(Opciones.this);
                    conexionWeb.agregarVariables("usuario", username.toLowerCase());
                    conexionWeb.agregarVariables("newContra", nc.getText().toString());
                    conexionWeb.agregarVariables("contra", ca.getText().toString());
                    try {
                        URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/password.php");
                        conexionWeb.execute(url);
                    } catch (MalformedURLException e) {
                        new AlertDialog.Builder(Opciones.this).setMessage(e.getMessage()).setTitle("Error").show();
                    }
                }else{
                    nc.requestFocus();
                    nc.setError("Las contraseñas no coinciden");
                    nc2.setError("Las contraseñas no coinciden");
                }

            }
        });

    }
    public void resultado(String res){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if(res.startsWith("Contraseña incorrecta")){
            //abrirPrincipal();
            //alias.setText("");
            //contra.setText("");
            Toast.makeText(Opciones.this,"La contraseña no corresponde al usuario",Toast.LENGTH_LONG).show();
        }
        if(res.startsWith("Contraseña actualizada")){
            //alert.setTitle("Error de autenticacion").setMessage("Username y/o contraseña incorrectas").show();
            Toast.makeText(Opciones.this, "Constraseña actualizada", Toast.LENGTH_LONG).show();
            //alias.requestFocus();
        }
        if(res.startsWith("No se pudo")){
            Toast.makeText(Opciones.this, "No se pudo actualizar la contraseña. Intentalo mas tarde", Toast.LENGTH_LONG).show();
        }
        System.out.println("******************************************************"+res);

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
