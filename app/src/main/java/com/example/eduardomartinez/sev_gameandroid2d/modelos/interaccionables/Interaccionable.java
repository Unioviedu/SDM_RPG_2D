package com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables;

/**
 * Created by karolmc on 29/10/2017.
 */

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by karolmc on 15/10/2017.
 */

public abstract class Interaccionable extends Modelo {

    protected Sprite sprite;

    public Interaccionable(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, Ar.coor(1), Ar.coor(1));
    }

    @Override
    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x - Habitacion.scrollEjeX, (int) y - Habitacion.scrollEjeY);
    }

    @Override
    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public abstract boolean activarItem(Habitacion habitacion);
}
