package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by eduardomartinez on 29/10/17.
 */

public abstract class DisparoEnemigo extends Modelo {
    public Sprite sprite;
    public double velocidadX;
    public double velocidadY;

    public boolean rebota = false;

    public DisparoEnemigo(Context context, double x, double y) {
        super(context, x, y, Ar.coor(1), Ar.coor(1));

        cArriba = 6;
        cAbajo = 6;
        cDerecha = 6;
        cIzquierda = 6;

        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.disparo_direcciones),
                ancho, altura,
                1, 1, true);
    }

    protected abstract void inicializar();

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public abstract void dibujar(Canvas canvas);

    public abstract void rebota(Habitacion habitacion);
}
