package com.example.eduardomartinez.sev_gameandroid2d.modelos.disparos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class DisparoJugadorRapido extends DisparoJugador {

    public DisparoJugadorRapido(Context context, double x, double y, double orientacionX, double orientacionY, boolean rebota) {
        super(context, x, y, orientacionX, orientacionY, rebota);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.disparo_jugador_rapido);

    }

    @Override
    protected void doInicializar() {
        velocidad = 70;
    }

    @Override
    public DisparoJugador disparar(double x, double y, double orientacionX, double orientacionY, boolean rebota) {
        return new DisparoJugadorRapido(context, x, y, orientacionX, orientacionY, rebota);
    }
}
