package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;

public class GameView extends SurfaceView implements SurfaceHolder.Callback  {

    boolean iniciado = false;
    Context context;
    GameLoop gameloop;

    public static int pantallaAncho;
    public static int pantallaAlto;

    private Habitacion habitacion;
    private Jugador jugador;
    public int numeroHabitacion = 0;

    public GameView(Context context) {
        super(context);
        iniciado = true;

        getHolder().addCallback(this);
        setFocusable(true);

        this.context = context;
        gameloop = new GameLoop(this);
        gameloop.setRunning(true);
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

        for(int i=0; i < 6; i++){
            if(accion[i] != NO_ACTION ) {

            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("Tecla","Tecla pulsada: "+keyCode);
        return false;
    }

    @Override
    public boolean onKeyUp (int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }



    protected void inicializar() throws Exception {
        habitacion = new Habitacion(context, numeroHabitacion);
        jugador = new Jugador(context, 250, 250);
    }

    public void actualizar(long tiempo) throws Exception {
            //TODO habitacion.actualizar(tiempo);
        jugador.actualizar(tiempo);
    }

    protected void dibujar(Canvas canvas) {

        habitacion.dibujar(canvas);
        jugador.dibujar(canvas);
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

        if (numeroHabitacion < 1){ // Número Máximo de Nivel
            numeroHabitacion++;
        } else {
            numeroHabitacion = 0;
        }
        inicializar();
    }


}