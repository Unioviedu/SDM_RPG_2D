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

public class DisparoJugadorNormal extends DisparoJugador {

    public DisparoJugadorNormal(Context context, double x, double y, double orientacionX, double orientacionY) {
        super(context, x, y, 110, 110);

        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_disparo1),
                ancho, altura,
                24, 4, true);
    }

    @Override
    protected void doInicializar() {
        velocidad = 30;
    }

    @Override
    public DisparoJugador disparar(double orientacionX, double orientacionY) {
        return new DisparoJugadorNormal(context, x, y, orientacionX, orientacionY);
    }


}
