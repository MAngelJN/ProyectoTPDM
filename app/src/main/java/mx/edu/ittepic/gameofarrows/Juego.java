package mx.edu.ittepic.gameofarrows;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
import android.widget.ProgressBar;
import android.widget.Toast;

public class Juego extends AppCompatActivity {

    Lienzo lienzo;
    Bitmap flecha;
    int x,y,x2,y2,izq,der,top,bot;
    CountDownTimer t,t2;
    int alto;
    int ancho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_juego);
        lienzo=new Lienzo(this);
        setContentView(lienzo);
        x=0;
        y=300;
        x2=0;
        y2=10;
        top=500;
        izq=der=0;

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

        flecha = BitmapFactory.decodeResource(getResources(),R.drawable.img12);
        t = new CountDownTimer(500000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                //x+=2;
                //y--;
                //Toast.makeText(Juego.this,"Ancho"+ancho+"\n"+"anchoflecha"+flecha.getWidth(),Toast.LENGTH_SHORT).show();
                System.out.println("Alto"+alto+"\n"+"anchoflecha"+flecha.getWidth()+"\n"+"ancho x"+x);
                if (!(x>=(ancho-flecha.getWidth()))){
                    x+=20;
                    t.cancel();}
                lienzo.invalidate();
            }

            @Override
            public void onFinish() {
                //t.start();


            }
        };

        t2 = new CountDownTimer(500000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!(x2>=ancho)){
                    x2+=2;
                    t.cancel();
                }
                lienzo.invalidate();

            }

            @Override
            public void onFinish() {

            }
        };


    }



    public class Lienzo extends View{

        public Lienzo(Context context){
            super(context);
        }

        public void onDraw(Canvas c){
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            c.drawRect(0, 10, x2,50, p);
            c.drawBitmap(flecha,x,y,p);

        }

        public boolean onTouchEvent(MotionEvent e){
            //e.getX y e.getY que corresponden a la coordenada tocada

            if(e.getAction()== MotionEvent.ACTION_DOWN){
                //cuando tocas la pantalla, en un View/Surface
                x=(int)e.getX();
                y=(int)e.getY();
                //t.start();
                t2.start();
                invalidate();
            }
            if(e.getAction()== MotionEvent.ACTION_MOVE){
                //Se ejecuta cuando arrastras
                x=(int)e.getX();
                y=(int)e.getY();
                invalidate();
            }
            if(e.getAction()== MotionEvent.ACTION_UP){
                t.start();
                t2.cancel();
                //cuando destocas la paantalla

            }
            return true;
        }


    }

    public void onDestroy(){
        t.cancel();
        super.onDestroy();
    }


}
