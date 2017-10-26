package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class Disparo extends Modelo {
    private Sprite sprite;
    private double velocidadX = 10;
    private double velocidadY = 10;
    private int orientacion;

    public Disparo(Context context, double x, double y, int orientacion) {
        super(context, x, y, 35, 35);

        if (orientacion == Jugador.IZQUIERDA)
            velocidadX *= -1;
        else if (orientacion == Jugador.ARRIBA)
            velocidadY *= -1;

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
                24, 5, true);
    }

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas) {
        //sprite.dibujarSprite(canvas, (int) x - Habitacion.ScrollX,  (int) y - Habitacion.ScrollY);
        sprite.dibujarSprite(canvas, (int) x,  (int) y);
    }


}
