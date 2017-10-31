package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by eduardomartinez on 29/10/17.
 */

public abstract class DisparoEnemigo extends Modelo {
    private Sprite sprite;
    public double velocidadX;
    public double velocidadY;

    public DisparoEnemigo(Context context, double x, double y) {
        super(context, x, y, 110, 110);

        cArriba = 6;
        cAbajo = 6;
        cDerecha = 6;
        cIzquierda = 6;
    }

    protected abstract void inicializar();

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public abstract void dibujar(Canvas canvas);
}
