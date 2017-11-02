package com.example.eduardomartinez.sev_gameandroid2d.modelos.disparos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class DisparoJugadorNormal extends DisparoJugador {

    public DisparoJugadorNormal(Context context, double x, double y, double orientacionX, double orientacionY) {
        super(context, x, y, orientacionX, orientacionY);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.disparo_jugador_normal);
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
