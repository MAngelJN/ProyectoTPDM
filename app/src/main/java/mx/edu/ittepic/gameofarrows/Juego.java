package mx.edu.ittepic.gameofarrows;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class Juego extends AppCompatActivity {

    Lienzo lienzo;
    Bitmap flecha;
    int ancho = 100;
    int alto = 100;
    CountDownTimer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_juego);
        lienzo=new Lienzo(this);
        setContentView(lienzo);
        flecha = BitmapFactory.decodeResource(getResources(),R.drawable.img12);


    }

    public class Lienzo extends SurfaceView implements SurfaceHolder.Callback{
        Hilo h;

        public Lienzo(Context context){
            super(context);
            getHolder().addCallback(this);

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            h = new Hilo(this);
            h.start();

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        public void onDraw(Canvas c){
            Bitmap f = BitmapFactory.decodeResource(getResources(),R.drawable.img12);

            Paint p = new Paint();


            c.drawBitmap(flecha,ancho,alto,p);

        }

        public boolean onTouchEvent(MotionEvent e){
            Log.e("Touched",e.getAction()+"");
            t = new CountDownTimer(500,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    ancho++;
                    invalidate();

                }

                @Override
                public void onFinish() {

                }
            }.start();


            return true;
        }

    }

    public class Hilo extends Thread{
        boolean repetir;
        Lienzo puntero;
        public Hilo(Lienzo ref){
            repetir=true;
            puntero=ref;

        }
        public void run(){
            Canvas canvas=null;
            while(repetir){
                //Aquí 2do plano
                try{

                    canvas = puntero.getHolder().lockCanvas();
                    synchronized (puntero.getHolder()){
                        puntero.onDraw(canvas);
                    }
                }finally{
                    //Se ejecuta cuando el recurso se libera
                    if(canvas!=null) {
                        puntero.getHolder().unlockCanvasAndPost(canvas);
                    }

                }

            }
        }
    }


}
