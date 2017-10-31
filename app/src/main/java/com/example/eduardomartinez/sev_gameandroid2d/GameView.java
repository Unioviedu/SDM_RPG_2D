package com.example.eduardomartinez.sev_gameandroid2d;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.eduardomartinez.sev_gameandroid2d.modelos.Vida;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.controles.PadMovimiento;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback  {

    boolean iniciado = false;
    public Context context;
    public GameLoop gameloop;

    public static int pantallaAncho;
    public static int pantallaAlto;

    private List<Habitacion> habitaciones;
    public int habitacionActual = 0;

    private PadMovimiento padMovimiento;
    private PadMovimiento padDisparo;

    public List<Vida> vidas;

    public GameView(Context context) {
        super(context);
        iniciado = true;

        getHolder().addCallback(this);
        setFocusable(true);

        this.context = context;
        gameloop = new GameLoop(this);
        gameloop.setRunning(true);
    }

    public Habitacion getHabitacionActual(){
        return habitaciones.get(habitacionActual);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // valor a Binario
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Indice del puntero
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        int pointerId  = event.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                accion[pointerId] = ACTION_DOWN;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                accion[pointerId] = ACTION_UP;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for(int i =0; i < pointerCount; i++){
                    pointerIndex = i;
                    pointerId  = event.getPointerId(pointerIndex);
                    accion[pointerId] = ACTION_MOVE;
                    x[pointerId] = event.getX(pointerIndex);
                    y[pointerId] = event.getY(pointerIndex);
                }
                break;
        }

        procesarEventosTouch();
        return true;
    }

    int NO_ACTION = 0;
    int ACTION_MOVE = 1;
    int ACTION_UP = 2;
    int ACTION_DOWN = 3;
    int accion[] = new int[6];
    float x[] = new float[6];
    float y[] = new float[6];

    public void procesarEventosTouch(){
        boolean pulsacionPadMover = false;
        boolean pulsacionPadDisparar = false;

        for(int i=0; i < 6; i++){
            if(accion[i] != NO_ACTION ) {

                if(accion[i] == ACTION_DOWN){
                    if(getHabitacionActual().nivelPausado) {
                        getHabitacionActual().nivelPausado = false;
                        hasPerdido();
                    }
                }

                if(padDisparo.estaPulsado(x[i], y[i])){
                    double orientacionX = padDisparo.getOrientacionXDisparo(x[i]);
                    double orientacionY = padDisparo.getOrientacionYDisparo(y[i]);

                    if (accion[i] != ACTION_UP) {
                        pulsacionPadDisparar = true;
                        getHabitacionActual().botonDispararPulsado = true;
                        getHabitacionActual().orientacionPadDispararX = orientacionX;
                        getHabitacionActual().orientacionPadDispararY = orientacionY;
                    }
                }


                if(padMovimiento.estaPulsado(x[i], y[i])){
                    float orientacionX = padMovimiento.getOrientacionX(x[i]);
                    float orientacionY = padMovimiento.getOrientacionY(y[i]);

                    if (accion[i] != ACTION_UP) {
                        pulsacionPadMover = true;
                        getHabitacionActual().orientacionPadMoverX = orientacionX;
                        getHabitacionActual().orientacionPadMoverY = orientacionY;
                    }
                }
            }


        }
        if(!pulsacionPadMover) {
            getHabitacionActual().orientacionPadMoverX = 0;
            getHabitacionActual().orientacionPadMoverY = 0;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("Tecla","Tecla pulsada: "+keyCode);

        if( keyCode == KeyEvent.KEYCODE_A) {
            getHabitacionActual().orientacionPadMoverX = 25;
        }
        if( keyCode == KeyEvent.KEYCODE_D) {
            getHabitacionActual().orientacionPadMoverX = -25;
        }
        if( keyCode == KeyEvent.KEYCODE_S) {
            getHabitacionActual().orientacionPadMoverY = -25;
        }
        if( keyCode == KeyEvent.KEYCODE_W) {
            getHabitacionActual().orientacionPadMoverY = 25;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp (int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_A || keyCode == KeyEvent.KEYCODE_S
                || keyCode == KeyEvent.KEYCODE_W || keyCode == KeyEvent.KEYCODE_D) {
            getHabitacionActual().orientacionPadMoverX = 0;
            getHabitacionActual().orientacionPadMoverY = 0;
        }
        return super.onKeyDown(keyCode, event);
    }



    protected void inicializar() throws Exception {

        habitaciones = GestorNivel.getInstance().seleccionarLongitudJuego(context);
        getHabitacionActual().gameView = this;

        padDisparo = new PadMovimiento(context, pantallaAncho*0.85, pantallaAlto*0.8);
        padMovimiento = new PadMovimiento(context);

        vidas = new ArrayList<>();

        double posX = 0.05 * pantallaAncho;
        double posY = 0.05 * pantallaAlto;
        for(int i = 0; i < getHabitacionActual().jugador.vidasTotales; i++){
            vidas.add(new Vida(context, posX + i*70, posY));
        }

    }

    public void actualizar(long tiempo) throws Exception {
        if(!getHabitacionActual().nivelPausado) {
            getHabitacionActual().actualizar(tiempo);
        }
    }

    protected void dibujar(Canvas canvas) {
        getHabitacionActual().dibujar(canvas);

        if(!getHabitacionActual().nivelPausado) {
            padDisparo.dibujar(canvas);
            padMovimiento.dibujar(canvas);

            for (int i = 0; i < getHabitacionActual().jugador.vidasTotales; i++) {
                Log.d("vidas", "vidas totales "+getHabitacionActual().jugador.vidasTotales+" vidas actuales "+getHabitacionActual().jugador.vidasActuales);
                if (i < getHabitacionActual().jugador.vidasActuales) {
                    vidas.get(i).setVidaLlena();
                    vidas.get(i).dibujar(canvas);
                }else {
                    vidas.get(i).setVidaVacia();
                    vidas.get(i).dibujar(canvas);
                }
            }
        }

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        pantallaAncho = width;
        pantallaAlto = height;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (iniciado) {
            iniciado = false;
            if (gameloop.isAlive()) {
                iniciado = true;
                gameloop = new GameLoop(this);
            }

            gameloop.setRunning(true);
            gameloop.start();
        } else {
            iniciado = true;
            gameloop = new GameLoop(this);
            gameloop.setRunning(true);
            gameloop.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        iniciado = false;

        boolean intentarDeNuevo = true;
        gameloop.setRunning(false);
        while (intentarDeNuevo) {
            try {
                gameloop.join();
                intentarDeNuevo = false;
            }
            catch (InterruptedException e) {
            }
        }
    }

    public void nivelCompleto() throws Exception {

        if (habitacionActual < habitaciones.size()){ // Número Máximo de Nivel
            habitacionActual++;
        } else {
            habitacionActual = 0;
        }
    }

    public void hasPerdido(){
        ((Activity) context).finish();
    }

}