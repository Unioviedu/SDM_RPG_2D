package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

import java.util.HashMap;

/**
 * Created by eduardomartinez on 24/10/17.
 */

public class Jugador extends Modelo {
    public static final String PARADO_ARRIBA = "parado_arriba";
    public static final String PARADO_ABAJO = "parado_abajo";
    public static final String PARADO_DERECHA = "parado_derecha";
    public static final String PARADO_IZQUIERDA = "parado_izquierda";

    public static final String CAMINANDO_ARRIBA = "caminando_arriba";
    public static final String CAMINANDO_ABAJO = "caminando_abajo";
    public static final String CAMINANDO_DERECHA = "caminando_derecha";
    public static final String CAMINANDO_IZQUIERDA = "caminando_izquierda";

    public static final String DISPARANDO_ARRIBA = "disparando_arriba";
    public static final String DISPARANDO_ABAJO = "disparando_abajo";
    public static final String DISPARANDO_DERECHA = "disparando_derecha";
    public static final String DISPARANDO_IZQUIERDA = "disparando_izquierda";

    public static final String GOLPEADO_ARRIBA = "golpeado_arriba";
    public static final String GOLPEADO_ABAJO = "golpeado_abajo";
    public static final String GOLPEADO_DERECHA = "golpeado_derecha";
    public static final String GOLPEADO_IZQUIERDA = "golpeado_izquierda";

    private HashMap<String, Sprite> sprites = new HashMap<>();
    private Sprite spriteActual;

    private double xInicial;
    private double yInicial;
    public double velocidadX;
    public double velocidadY;

    public int orientacion;
    public static final int DERECHA = 1;
    public static int ARRIBA = 2;
    public static final int IZQUIERDA = -1;
    public static final int ABAJO = -2;

    public Disparo disparo;
    private boolean disparando;
    private boolean golpeado = false;

    float sensibilidadPad = 20;
    public float velocidad = 12;

    public int vidas;
    public double msInmunidad = 0;

    public Jugador(Context context, double xInicial, double yInicial) {
        super(context, xInicial, yInicial, 59*2, 50*2);

        this.xInicial = xInicial;
        this.yInicial = yInicial;

        this.x =  this.xInicial;
        this.y =  this.yInicial;

        orientacion = ABAJO;

        inicializar();
    }

    public void inicializar() {
        crearSprite(R.drawable.protagonista_animacion_parado_arriba,
                PARADO_ARRIBA, 2, true);
        Sprite paradoAbajo = crearSprite(R.drawable.protagonista_animacion_parado_abajo,
                PARADO_ABAJO, 2, true);
        crearSprite(R.drawable.protagonista_animacion_parado_derecha,
                PARADO_DERECHA, 2, true);
        crearSprite(R.drawable.protagonista_animacion_parado_izquierda,
                PARADO_IZQUIERDA, 2, true);

        crearSprite(R.drawable.protagonista_animacion_caminando_arriba,
                CAMINANDO_ARRIBA, 6, true);
        crearSprite(R.drawable.protagonista_animacion_caminando_abajo,
                CAMINANDO_ABAJO, 6, true);
        crearSprite(R.drawable.protagonista_animacion_caminando_derecha,
                CAMINANDO_DERECHA, 6, true);
        crearSprite(R.drawable.protagonista_animacion_caminando_izquierda,
                CAMINANDO_IZQUIERDA, 6, true);

        crearSprite(R.drawable.protagonista_animacion_disparando_arriba,
                DISPARANDO_ARRIBA, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_abajo,
                DISPARANDO_ABAJO, 2, false);
        crearSprite(R.drawable.protagonista_animacion_disparando_derecha,
                DISPARANDO_DERECHA, 2, false);
        crearSprite(R.drawable.protagonista_animacion_disparando_izquierda,
                DISPARANDO_IZQUIERDA, 2, false);

        crearSprite(R.drawable.protagonista_animacion_golpeado_arriba,
                GOLPEADO_ARRIBA, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_abajo,
                GOLPEADO_ABAJO, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_derecha,
                GOLPEADO_DERECHA, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_izquierda,
                GOLPEADO_IZQUIERDA, 2, false);

        spriteActual = paradoAbajo;
    }

    public void dibujar(Canvas canvas){
        spriteActual.dibujarSprite(canvas, (int) x, (int) y,msInmunidad > 0);
    }

    public void actualizar (long tiempo) {
        if(msInmunidad > 0)
            msInmunidad -= tiempo;

        boolean finSprite = spriteActual.actualizar(tiempo);

        if (golpeado && finSprite)
            golpeado = false;

        if(disparando && finSprite)
            disparando = false;

        comprobarCaminando();

        if (disparando)
            comprobarDisparando();

        if (golpeado)
            comprobarGolpeado();

    }

    public int golpeado(){
        if (msInmunidad <= 0) {
            if (vidas > 0) {
                vidas--;
                msInmunidad = 3000;
                golpeado = true;
                // Reiniciar animaciones que no son bucle
                sprites.get(GOLPEADO_IZQUIERDA).setFrameActual(0);
                sprites.get(GOLPEADO_DERECHA).setFrameActual(0);
                sprites.get(GOLPEADO_ARRIBA).setFrameActual(0);
                sprites.get(GOLPEADO_ABAJO).setFrameActual(0);
            }
        }
        return vidas;
    }

    private void comprobarCaminando() {
        if (velocidadX > 0)
            spriteActual = sprites.get(CAMINANDO_DERECHA);

        if (velocidadX < 0 )
            spriteActual = sprites.get(CAMINANDO_IZQUIERDA);

        if (velocidadY > 0)
            spriteActual = sprites.get(CAMINANDO_ABAJO);

        if (velocidadY < 0 )
            spriteActual = sprites.get(CAMINANDO_ARRIBA);

        if (velocidadX == 0 && velocidadY == 0)
            if (orientacion == DERECHA)
                spriteActual = sprites.get(PARADO_DERECHA);
            else if (orientacion == IZQUIERDA)
                spriteActual = sprites.get(PARADO_IZQUIERDA);
            else if (orientacion == ARRIBA)
                spriteActual = sprites.get(PARADO_ARRIBA);
            else if (orientacion == ABAJO)
                spriteActual = sprites.get(PARADO_ABAJO);
    }

    private void comprobarDisparando() {
        if (orientacion == DERECHA)
            spriteActual = sprites.get(DISPARANDO_DERECHA);
        else if (orientacion == IZQUIERDA)
            spriteActual = sprites.get(DISPARANDO_IZQUIERDA);
        else if (orientacion == ARRIBA)
            spriteActual = sprites.get(DISPARANDO_ARRIBA);
        else if (orientacion == ABAJO)
            spriteActual = sprites.get(DISPARANDO_ABAJO);
    }

    private void comprobarGolpeado() {
        if (orientacion == DERECHA)
            spriteActual = sprites.get(GOLPEADO_DERECHA);
        else if (orientacion == IZQUIERDA)
            spriteActual = sprites.get(GOLPEADO_IZQUIERDA);
        else if (orientacion == ARRIBA)
            spriteActual = sprites.get(GOLPEADO_ARRIBA);
        else if (orientacion == ABAJO)
            spriteActual = sprites.get(GOLPEADO_ABAJO);
    }

    private Sprite crearSprite(int animacion, String nombre, int frames, boolean bucle) {
        Sprite temp = new Sprite (CargadorGraficos.cargarDrawable(context, animacion),
                ancho, altura,
                frames*4, frames, bucle);
        sprites.put(nombre, temp);

        return temp;
    }

    public void procesarOrdenes(float orientacionMoverX, float orientacionMoverY,
                                boolean disparar, float orientacionPadDispararX,
                                float orientacionPadDispararY) {
        if (disparar) {
            disparando = true;

            sprites.get(DISPARANDO_DERECHA).setFrameActual(0);
            sprites.get(DISPARANDO_IZQUIERDA).setFrameActual(0);
            sprites.get(DISPARANDO_ARRIBA).setFrameActual(0);
            sprites.get(DISPARANDO_ABAJO).setFrameActual(0);
        }



        if (orientacionMoverX > sensibilidadPad) {
            velocidadX = -velocidad;
            orientacion = IZQUIERDA;
        } else if (orientacionMoverX < -sensibilidadPad){
            velocidadX = velocidad;
            orientacion = DERECHA;
        } else {
            velocidadX = 0;
        }

        if (orientacionMoverY > sensibilidadPad) {
            velocidadY = -velocidad;
            orientacion = ARRIBA;
        } else if (orientacionMoverY < -sensibilidadPad){
            velocidadY = velocidad;
            orientacion = ABAJO;
        } else {
            velocidadY = 0;
        }
    }
}
