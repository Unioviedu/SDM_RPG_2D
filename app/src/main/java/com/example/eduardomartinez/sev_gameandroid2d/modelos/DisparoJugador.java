package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public abstract class DisparoJugador extends Modelo {
    protected Sprite sprite;

    public double velocidadX;
    public double velocidadY;
    float velocidad;
    double orientacionX;
    double orientacionY;


    public DisparoJugador(Context context, double x, double y, double orientacionX, double orientacionY) {
        super(context, x, y, 110, 110);

        cArriba = 6;
        cAbajo = 6;
        cDerecha = 6;
        cIzquierda = 6;
        this.orientacionX = orientacionX;
        this.orientacionY = orientacionY;
        this.context = context;

        inicializar(  orientacionX,  orientacionY);
    }

    protected void inicializar(double orientacionX, double orientacionY) {

        doInicializar();

        int orientacionDisparo = Utilidades.orientacion(orientacionX, orientacionY);

        if (orientacionDisparo == Jugador.DERECHA)
            velocidadX = velocidad;
        else if (orientacionDisparo == Jugador.IZQUIERDA)
            velocidadX = -velocidad;
        else if (orientacionDisparo == Jugador.ARRIBA)
            velocidadY = -velocidad;
        else if (orientacionDisparo == Jugador.ABAJO)
            velocidadY = velocidad;
    }

    protected abstract void doInicializar();

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x - Habitacion.scrollEjeX,  (int) y - Habitacion.scrollEjeY);
    }

    public abstract DisparoJugador disparar(double orientacionX, double orientacionY);


}
