package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class DisparoJugador extends Modelo {
    private Sprite sprite;

    public double velocidadXOriginal = 30;
    private double velocidadYOriginal = 30;
    public double velocidadX;
    public double velocidadY;
    private int orientacion;

    public DisparoJugador(Context context, double x, double y, int orientacion) {
        super(context, x, y, 55, 55);

        if (orientacion == Jugador.DERECHA) {
            velocidadX = velocidadXOriginal;
            velocidadY = 0;
        } else if (orientacion == Jugador.IZQUIERDA) {
            velocidadX = -velocidadXOriginal;
            velocidadY = 0;
        } else if (orientacion == Jugador.ARRIBA) {
            velocidadY = -velocidadYOriginal;
            velocidadX = 0;
        } else if (orientacion == Jugador.ABAJO) {
            velocidadY = velocidadYOriginal;
            velocidadX = 0;
        }

        cDerecha = 6;
        cIzquierda = 6;
        cArriba = 6;
        cAbajo = 6;

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
        //sprite.dibujarSprite(canvas, (int) x - Habitacion.ScrollX,  (int) y - Habitacion.ScrollY);
        sprite.dibujarSprite(canvas, (int) x,  (int) y);
    }


}
