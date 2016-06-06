package mx.edu.ittepic.gameofarrows;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Pattern;

public class PantallaRegistro extends AppCompatActivity {

    EditText nombre,alias,cel,cel2;
    ImageView img2,imgR;
    String pW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);

        nombre = (EditText) findViewById(R.id.editText3);
        alias = (EditText) findViewById(R.id.fuente);
        cel = (EditText) findViewById(R.id.editText4);
        cel2 = (EditText) findViewById(R.id.editText5);
        //img2 = (ImageView) findViewById(R.id.imageView);
        imgR = (ImageView) findViewById(R.id.imageView2);
        Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
        nombre.setTypeface(f);
        alias.setTypeface(f);
        cel.setTypeface(f);
        cel2.setTypeface(f);
        pW = "";

        imgR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nombre.getText().toString())){
                    nombre.requestFocus();
                    nombre.setError("El nombre no puede ser vacío");
                    return;
                }
                if (TextUtils.isEmpty(alias.getText().toString())){
                    alias.requestFocus();
                    alias.setError("El usuario no puede ser vacío");
                    return;
                }
                if (TextUtils.isEmpty(cel.getText().toString())){
                    cel.requestFocus();
                    cel.setError("El número telefónico no puede ser vacío");
                    return;
                }
                if (TextUtils.isEmpty(cel2.getText().toString())){
                    cel2.requestFocus();
                    cel2.setError("El número telefónico no puede ser vacío");
                    return;
                }
                if (!(Pattern.compile("^[a-zA-Z0-9_]+$").matcher(alias.getText().toString()).matches())) {
                    alias.requestFocus();
                    alias.setError("Usuario no puede contener espacios o caracteres especiales.");
                    return;
                }
                if(cel.getText().toString().equals(cel2.getText().toString())){
                    ConexionWeb conexionWeb = new ConexionWeb(PantallaRegistro.this);
                    conexionWeb.agregarVariables("nombre", nombre.getText().toString());
                    conexionWeb.agregarVariables("username", alias.getText().toString().toLowerCase());
                    conexionWeb.agregarVariables("cel", cel.getText().toString());

                    generarPassword(6);

                    conexionWeb.agregarVariables("contra", pW);


                    try {
                        URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/registrar.php");
                        conexionWeb.execute(url);
                    }catch (MalformedURLException e){
                        new AlertDialog.Builder(PantallaRegistro.this).setMessage(e.getMessage()).setTitle("Error").show();
                    }

                }else{
                    cel.requestFocus();
                    cel.setError("Los números telefónicos no coinciden");
                    cel2.setError("Los números telefónicos no coinciden");
                }

            }
        });
    }

    private void generarPassword(int l){
        long m = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(m);
        char c;
        int i=0;
        while(i<l) {
            c = (char)r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >='A' && c <='Z') ){
                pW += c;
                i++;
            }
        }
    }
    public void resultado(String res){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);



        if(res.startsWith("Error_404_2")){
            res = "Error al enviar o recibir información con el php";
        }
        if(res.startsWith("Error_404_1")){
            res = "Hosting no encontrado";
        }
        if(res.startsWith("Error_404")){
            res = "Al parecer no existe el php buscado";
        }
        if(res.startsWith("Insertado")){
            //MANDAR MENSAJE DE TEXTO
            SmsManager enviarSMS = SmsManager.getDefault();

            enviarSMS.sendTextMessage(cel.getText().toString(), null, "¡Game Of Arrows te da la bienvenida, " +
                    nombre.getText().toString() + "!\n Usuario: " + alias.getText().toString() +
                    "\n Contraseña: " + pW, null, null);

            //Toast.makeText(PantallaRegistro.this, "Mensaje Enviado", Toast.LENGTH_SHORT).show();
            nombre.setText("");
            alias.setText("");
            cel.setText("");
            cel2.setText("");
            Toast.makeText(PantallaRegistro.this, "Usuario registrado. A la brevedad recibirá un mensaje con su información", Toast.LENGTH_LONG).show();
            PantallaRegistro.this.finish();
        }
        if(res.startsWith("No insertado")){
            //alert.setTitle("Error al registrar").setMessage("Intentalo mas tarde").show();
            Toast.makeText(PantallaRegistro.this,"Error al insertar. Inténtalo más tarde.",Toast.LENGTH_LONG).show();
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
