package mx.edu.ittepic.gameofarrows;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class JuegoOL extends AppCompatActivity {
    //ConexionWeb conexionWebl;
    EditText usuarioET;
    Lienzo lienzo;
    Bitmap flecha;
    int x,y,x2,y2,izq,der,top,yDiana;
    float xt;
    CountDownTimer tiro,bPotencia,turno, lectura;
    int alto, ancho, potencia, viento,elTurno;
    Random random;
    float tiempo;
    boolean direccion, timeOver, esMiTurno;
    String username,wind;
    int userP,cpuP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_juego_ol);
        //setContentView(R.layout.activity_juego);
        lienzo = new Lienzo(this);
        esMiTurno= true;
        setContentView(lienzo);
        //conexionWebl = new ConexionWeb(this);
        usuarioET = new EditText(JuegoOL.this);
        elTurno=0;
        userP= cpuP = 0;
        x=0;
        y=300;
        x2=0;
        top=500;
        izq=der=0;
        potencia = 0;
        random = new Random();
        viento = random.nextInt(100-50+1) + 50;//(max - min +1) + min
        if(random.nextInt(10-2+1)+2<6){viento= viento*-1;}
        wind = viento/10+"";
        tiempo = 10;
        direccion = true;

        timeOver = false;
        xt= 415;
        Bundle b = getIntent().getExtras();
        username = b.getString("user");
        username = username.toLowerCase();

        Display display = this.
                getWindowManager().getDefaultDisplay();
        Point size = new Point();

        // Utilizamos uno u otro metodo para tomar el tamaño
        // de pantalla, dependiendo de la version de android
        if(Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.HONEYCOMB_MR2){
            display.getSize(size);
            ancho = size.x;
            alto = size.y;
        }
        else {
            // Esto es deprecated, pero es necesario para
            // versiones anteriores
            ancho = display.getWidth();
            alto = display.getHeight();
        }
        yDiana = random.nextInt((alto-350)-50+1) + 50;
        y2=alto-110;
        //System.out.println("***************************ALTO************************* " + alto);
        //System.out.println("***************************ANCHO************************* " + ancho);
        AlertDialog.Builder alert = new AlertDialog.Builder(JuegoOL.this);
        alert.setTitle("Game Of Arrows")
                .setMessage("¿Elije una opción")
                .setPositiveButton("Empezar una partida", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new AlertDialog.Builder(JuegoOL.this).setTitle("Usuario a retar")
                                .setView(usuarioET)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConexionWeb conexionWeb = new ConexionWeb(JuegoOL.this);
                                        conexionWeb.agregarVariables("des",usuarioET.getText().toString().toLowerCase()+"");
                                        conexionWeb.agregarVariables("rem",username.toLowerCase()+"");
                                        conexionWeb.agregarVariables("mensaje",username+"");
                                        System.out.println("**************************" + usuarioET.getText().toString() + "****");
                                        System.out.println("**************************"+username+"****");
                                        try {
                                            URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/enviar.php");
                                            conexionWeb.execute(url);
                                        } catch (MalformedURLException e) {
                                            new AlertDialog.Builder(JuegoOL.this).setMessage(e.getMessage()).setTitle("Error").show();
                                        }
                                        dialog.dismiss();
                                    }
                                }).show();

                    }
                })
                .setNegativeButton("Unirme a una partida", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        new AlertDialog.Builder(JuegoOL.this).setTitle("Usuario que te reta")
                                .setView(usuarioET)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //conexionWebl.agregarVariables("rem",usuarioET.getText().toString().toLowerCase());
                                        //conexionWebl.agregarVariables("user",username.toLowerCase()+"");
                                        dialog.dismiss();
                                        //INICIAR HILO PARA EMPEZAR A LEER DATOS
                                        lectura.start();
                                    }
                                }).show();
                    }
                })
                        //.setView(image)
                .show();
        flecha = BitmapFactory.decodeResource(getResources(), R.drawable.flecha);
        lectura = new CountDownTimer(300000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                ConexionWeb conexionWeb = new ConexionWeb(JuegoOL.this);
                conexionWeb.agregarVariables("rem",usuarioET.getText().toString().toLowerCase());
                conexionWeb.agregarVariables("user",username.toLowerCase()+"");
                try {
                    URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/recibir.php");
                    conexionWeb.execute(url);
                } catch (MalformedURLException e) {
                    new AlertDialog.Builder(JuegoOL.this).setMessage(e.getMessage()).setTitle("Error").show();
                }
            }

            @Override
            public void onFinish() {

            }
        };
        tiro = new CountDownTimer(15000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                //x+=2;
                //y--;
                //Toast.makeText(Juego.this,"Ancho"+ancho+"\n"+"anchoflecha"+flecha.getWidth(),Toast.LENGTH_SHORT).show();
                //System.out.println("Alto"+alto+"\n"+"anchoflecha"+flecha.getWidth()+"\n"+"ancho x"+x);
                if (x<=((ancho - 200)-flecha.getWidth()+10)){

                    x+=20;
                    if(viento>0){
                        y--;
                        viento--;
                    }
                    if(viento<0){
                        y++;
                        viento++;
                    }

                    //tiro.cancel();
                }else {
                    //La flecha ya choco, asi que los valores se restauran para que el siguiente turno empieze
                    ConexionWeb conexionWeb = new ConexionWeb(JuegoOL.this);
                    conexionWeb.agregarVariables("des",usuarioET.getText().toString().toLowerCase());
                    conexionWeb.agregarVariables("rem",username.toLowerCase()+"");
                    conexionWeb.agregarVariables("mensaje",usuarioET.getText().toString().toLowerCase()+"");
                    try {
                        URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/enviar.php");
                        conexionWeb.execute(url);
                    } catch (MalformedURLException e) {
                        new AlertDialog.Builder(JuegoOL.this).setMessage(e.getMessage()).setTitle("Error").show();
                    }
                    esMiTurno=false;
                    tiro.cancel();
                    userP = userP + puntos();
                    //cpuP = cpuP + tiroComputadora();
                    viento = random.nextInt(100-10+1) + 10;//(max - min +1) + min
                    if(random.nextInt(10-2+1)+2>=6){
                        //vientoDirecion=true;

                    }else{
                        //vientoDirecion=false;
                        viento=viento*-1;
                        wind = viento/10+"";
                    }
                    tiempo = 10;
                    xt= 415;
                    potencia = 0;
                    y=300;
                    y2=alto-110;
                    yDiana = random.nextInt((alto-350)-50+1) + 50;
                    x=0;
                    timeOver=false;
                    tiro.cancel();
                    elTurno++;
                    if(elTurno>=3){
                        enviarPuntos();
                        //Toast.makeText(Juego.this,"It's Over :(",Toast.LENGTH_LONG).show();
                        ImageView image = new ImageView(JuegoOL.this);
                        image.setImageResource(R.drawable.engrane);
                        AlertDialog.Builder alert = new AlertDialog.Builder(JuegoOL.this);
                        alert.setTitle("Game Of Arrows")
                                .setMessage("¿Quieres volver a jugar?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        elTurno=0;
                                        userP = cpuP = 0;
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        JuegoOL.this.finish();
                                    }
                                })
                                        //.setView(image)
                                .show();
                    }
                }
                lienzo.invalidate();
            }

            @Override
            public void onFinish() {
                //tiro.start();
            }
        };

        bPotencia = new CountDownTimer(10000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(direccion){
                    potencia++;
                    y2-=10;
                    //System.out.println("****************************************************" + potencia);
                    tiro.cancel();
                }else {
                    potencia--;
                    y2+=10;
                    //System.out.println("****************************************************" + potencia);
                    tiro.cancel();
                }
                //System.out.println("****************************************************" + potencia);
                if (y2<200){
                    direccion=false;
                }
                if(y2>alto-200){
                    direccion=true;
                }
                lienzo.invalidate();
            }
            @Override
            public void onFinish() {

            }
        };
        turno = new CountDownTimer(10000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(tiempo>=0){
                    tiempo-=0.1;
                    xt-=5;
                    //System.out.println("****************************************************" + xt);
                }
            }

            @Override
            public void onFinish() {
                timeOver = true;
                bPotencia.cancel();
                tiro.start();
            }
        };
    }

    //****************************************************************************** LIENZO *************************************************************************

    public class Lienzo extends View {
        boolean dibuja;
        float anchoUsu;
        float altoF;
        int aFlecha;
        public Lienzo(Context context){
            super(context);
            setBackgroundResource(R.drawable.juego);
            dibuja=true;
            anchoUsu = BitmapFactory.decodeResource(getResources(), R.drawable.usu).getHeight();
            altoF = BitmapFactory.decodeResource(getResources(), R.drawable.fu).getHeight();
            aFlecha = BitmapFactory.decodeResource(getResources(), R.drawable.d).getHeight();
            //System.out.println("--------------------------------------------------------------------------------------"+ BitmapFactory.decodeResource(getResources(), R.drawable.d).getWidth());
        }

        //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onDraw(Canvas c){
            Paint p = new Paint();
            p.setColor(Color.RED);
            c.drawRect(ancho - 50, y2, ancho - 10, alto - 100, p);//POTENCIA9
            p.setColor(Color.GRAY);
            c.drawRect(10, 10, 415, 50, p);
            p.setColor(Color.LTGRAY);
            c.drawRect(10, 10, xt, 50, p);//Tiempo
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.d), ancho - 200,yDiana,p);//DIANA
            c.drawBitmap(flecha, x, y, p);
            if(viento<0){
                c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fu),160,50,p);
            }
            if (viento>0){
                c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fd),160,50,p);
            }
            Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
            p.setTypeface(f);
            p.setTextSize(40);
            p.setFakeBoldText(true);
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.usu),
                    ancho - (p.measureText(username) / 2) - (anchoUsu / 2), 10, p);

            p.setColor(Color.DKGRAY);
            int tiem =(int)tiempo;
            c.drawText("Tiempo:  " + tiem, 50, 50, p);
            c.drawText(username + ": " + userP, ancho / 2 - (anchoUsu / 2), 50, p);
            c.drawText("CPU: " + cpuP, ancho / 2 - (anchoUsu / 2), 100, p);
            c.drawText(username, ancho - p.measureText(username), anchoUsu + 45, p);
            p.setColor(Color.WHITE);
            //p.setTextSize(60);
            c.drawText(wind+" km/h",115,250,p);
            //dibuja = false;
        }

        public boolean onTouchEvent(MotionEvent e){
            //e.getX y e.getY que corresponden a la coordenada tocada
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   "+esMiTurno);
            if (esMiTurno){
                if(elTurno<3){
                    if(e.getAction()== MotionEvent.ACTION_DOWN){
                        //cuando tocas la pantalla, en un View/Surface
                        //x=(int)e.getX();
                        y=(int)e.getY();
                        tiro.cancel();

                        bPotencia.start();
                        turno.start();
                        invalidate();
                    }
                    if(e.getAction()== MotionEvent.ACTION_MOVE){
                        //Se ejecuta cuando arrastras
                        //x=(int)e.getX();
                        y=(int)e.getY();
                        invalidate();
                    }
                    if(e.getAction()== MotionEvent.ACTION_UP){
                        //cuando destocas la paantalla
                        if(!timeOver) {
                            bPotencia.cancel();
                            turno.cancel();
                            tiro.start();
                        }
                    }
                }
            }
            return true;
        }
    }

    public void onDestroy(){
        tiro.cancel();
        super.onDestroy();
    }
    public int tiroComputadora(){
        return random.nextInt(10-5+1) + 5;//(max - min +1) + min
    }
    public int puntos(){
        if(y>=yDiana && y<yDiana+30 || y>=yDiana+270 && y<yDiana+300){
            Toast.makeText(this, "!Tienes que acertar a la diana!", Toast.LENGTH_SHORT).show();
            return 2;
        }
        if(y>=yDiana+30 && y<yDiana+60 || y>=yDiana+240 && y<yDiana+270){
            Toast.makeText(this,"¡Mal tiro!",Toast.LENGTH_SHORT).show();
            return 4;
        }
        if(y>=yDiana+60 && y<yDiana+90 || y>=yDiana+210 && y<yDiana+240){
            Toast.makeText(this,"¡No está mal!",Toast.LENGTH_SHORT).show();
            return 6;
        }
        if(y>=yDiana+90 && y<yDiana+120 || y>=yDiana+180 && y<yDiana+210){
            Toast.makeText(this,"¡Buen tiro!",Toast.LENGTH_SHORT).show();
            return 8;
        }
        if(y>=yDiana+120 && y<yDiana+180){
            Toast.makeText(this,"¡Excelente tiro!",Toast.LENGTH_SHORT).show();
            return 10;
        }
        Toast.makeText(this,"¡Hay mucho que mejorar!",Toast.LENGTH_SHORT).show();
        return 0;
    }

    public void enviarPuntos(){
        ConexionWeb conexionWeb = new ConexionWeb(JuegoOL.this);
        conexionWeb.agregarVariables("user", username.toLowerCase()+"");
        conexionWeb.agregarVariables("puntos", userP+"");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + userP);
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + username.toLowerCase());
        try {
            URL url = new URL("http://gameofarrows.ueuo.com/GameOfArrows/puntos.php");
            conexionWeb.execute(url);
        } catch (MalformedURLException e) {
            new AlertDialog.Builder(this).setMessage(e.getMessage()).setTitle("Error").show();
        }
    }

    public void resultado(String res){
        if(res.startsWith("msj")){
            //Toast.makeText(this,"Se inserto",Toast.LENGTH_SHORT).show();
            String[] m = res.split("-");
            System.out.println("*********************************************"+m[1]);

            if(m[1].toString().equals(username)){
                System.out.println("*********************************************"+m[1]+"****** Ahora es tu turno");
                esMiTurno=true;
                Toast.makeText(this,"Es mi turno",Toast.LENGTH_SHORT);
            }else{
                System.out.println("*********************************************"+m[1]+"****** Ahora es turno de tu oponente");
                esMiTurno=false;
            }
        }
        if (res.startsWith("Fallo r")){
            //Toast.makeText(this,"Hubo pedo",Toast.LENGTH_SHORT).show();
            System.out.println("*********************************************  Error en RECIBIR");
        }
        if (res.startsWith("Fallo e")){
            //Toast.makeText(this,"Hubo pedo",Toast.LENGTH_SHORT).show();
            System.out.println("*********************************************  Error en Enviar");
        }
        /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Respuesta desde servidor")
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
