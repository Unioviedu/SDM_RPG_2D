package com.example.eduardomartinez.sev_gameandroid2d.modelos.disparos;

import android.content.Context;
import android.graphics.Paint;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class DisparoJugadorRapido extends DisparoJugador {

    public DisparoJugadorRapido(Context context, double x, double y, double orientacionX, double orientacionY) {
        super(context, x, y, orientacionX, orientacionY);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.disparo_jugador_rapido);

    }

    @Override
    protected void doInicializar() {
        velocidad = 70;
    }

    @Override
    public DisparoJugador disparar(double orientacionX, double orientacionY) {
        return new DisparoJugadorRapido(context, x, y, orientacionX, orientacionY);
    }
}
