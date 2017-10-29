package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class DisparoJugador extends Modelo {
    private Sprite sprite;

    public double velocidadX;
    public double velocidadY;
    float sensibilidad = 20;
    float velocidad = 40;


    public DisparoJugador(Context context, double x, double y, int orientacion, double orientacionX, double orientacionY) {
        super(context, x, y, 110, 110);

        cArriba = 6;
        cAbajo = 6;
        cDerecha = 6;
        cIzquierda = 6;

        int orientacionDisparo = Utilidades.orientacion(orientacionX, orientacionY);

        if (orientacionDisparo == Jugador.DERECHA)
            velocidadX = velocidad;
        else if (orientacionDisparo == Jugador.IZQUIERDA)
            velocidadX = -velocidad;
        else if (orientacionDisparo == Jugador.ARRIBA)
            velocidadY = -velocidad;
        else if (orientacionDisparo == Jugador.ABAJO)
            velocidadY = velocidad;

        inicializar();
    }

    private void inicializar() {
        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_disparo1),
                ancho, altura,
                24, 4, true);
    }

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x,  (int) y);
    }


}
