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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;

public class Juego extends AppCompatActivity {
    //android:background="#050505"
    Lienzo lienzo;
    Bitmap flecha;
    int x,y,x2,y2,izq,der,top;
    float xt;
    CountDownTimer tiro,bPotencia,turno;
    int alto, ancho, potencia, viento,elTurno;
    Random random;
    float tiempo;
    boolean direccion, timeOver;
    String username;
    int userP,cpuP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_juego);
        lienzo = new Lienzo(this);
        setContentView(lienzo);
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
        tiempo = 10;
        direccion = true;
        timeOver = false;
        xt= 510;
        Bundle b = getIntent().getExtras();
        username = b.getString("user");

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
        y2=alto-110;
        //System.out.println("***************************ALTO************************* " + alto);
        //System.out.println("***************************ANCHO************************* " + ancho);

        flecha = BitmapFactory.decodeResource(getResources(),R.drawable.img12);

        tiro = new CountDownTimer(15000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                //x+=2;
                //y--;
                //Toast.makeText(Juego.this,"Ancho"+ancho+"\n"+"anchoflecha"+flecha.getWidth(),Toast.LENGTH_SHORT).show();
                //System.out.println("Alto"+alto+"\n"+"anchoflecha"+flecha.getWidth()+"\n"+"ancho x"+x);
                if (x<=(ancho-flecha.getWidth())){
                    x+=20;
                    if(viento>0){
                        y--;
                        viento--;
                    }

                    //tiro.cancel();
                }else {
                    //La flecha ya choco, asi que los valores se restauran para que el siguiente turno empieze
                    userP = puntos();

                    cpuP = tiroComputadora();
                    viento = random.nextInt(100-50+1) + 50;//(max - min +1) + min
                    tiempo = 10;
                    xt= 510;
                    potencia = 0;
                    y=300;
                    y2=alto-110;
                    x=0;
                    timeOver=false;
                    tiro.cancel();
                    elTurno++;
                    if(elTurno>=3){
                        //Toast.makeText(Juego.this,"It's Over ;(",Toast.LENGTH_LONG).show();
                        ImageView image = new ImageView(Juego.this);
                        image.setImageResource(R.drawable.img2);
                        AlertDialog.Builder alert = new AlertDialog.Builder(Juego.this);
                        alert.setTitle("Respuesta desde servidor")
                                .setMessage("¿Quieres volver a jugar?")
                                .setPositiveButton("Shi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Juego.this.finish();
                                    }
                                })
                                .setNegativeButton("Ño", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        elTurno=0;
                                    }
                                })
                                .setView(image)
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
                System.out.println("****************************************************" + potencia);
                if (y2<100){
                    direccion=false;
                }
                if(y2>alto-100){
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
                if(tiempo>0){
                    tiempo-=0.1;
                    xt-=5;
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

    public class Lienzo extends View{

        public Lienzo(Context context){
            super(context);
            setBackgroundResource(R.drawable.lc);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onDraw(Canvas c){
            Paint p = new Paint();
            Typeface f = Typeface.createFromAsset(getAssets(),"GOT.ttf");
            p.setTypeface(f);
            p.setTextSize(40);
            p.setFakeBoldText(true);
            p.setColor(Color.RED);
            c.drawRoundRect(ancho - 50, y2, ancho - 10, alto - 100, 20f, 20f, p);
            p.setColor(Color.DKGRAY);
            c.drawRoundRect(10, 10, xt, 50, 20f, 20.0f, p);
            p.setColor(Color.LTGRAY);
            c.drawText("Tiempo", 150, 50, p);
            c.drawBitmap(flecha,x,y,p);

        }

        public boolean onTouchEvent(MotionEvent e){
            //e.getX y e.getY que corresponden a la coordenada tocada
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
            return true;
        }
    }

    public void onDestroy(){
        tiro.cancel();
        super.onDestroy();
    }
    public int tiroComputadora(){
        return random.nextInt(10-0+1) + 0;//(max - min +1) + min
    }
    public int puntos(){
        return 10;
    }
}
