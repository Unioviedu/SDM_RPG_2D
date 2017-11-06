package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class EnemigoDisparoRebote extends Enemigo {
    int cont = 0;

    public EnemigoDisparoRebote(Context context, double x, double y) {
        super(context, x, y, 59*2, 50*2);

        cadenciaDisparo = 1800;

        spriteActual = crearSprite(R.drawable.protagonista_animacion_caminando_izquierda,
                CAMINANDO_DERECHA,
                6, 6, true);
    }

    @Override
    public void aplicarReglasMovimiento(Habitacion habitacion) {

    }

    @Override
    public void actualizar(long tiempo) {
        spriteActual.actualizar(tiempo);
    }

    @Override
    public DisparoEnemigo disparar(Context context, double posJugadorX, double posJugadorY,
                                   long milisegundos) {
        if (cont > 0)
            return null;

        if (milisegundos - miliSegundosDisparo> cadenciaDisparo
                + Math.random()* cadenciaDisparo) {

            miliSegundosDisparo = milisegundos;

            cont++;
            return new DisparoEnemigoRebotaParedes(context, x, y);
        } else
            return null;
    }

    @Override
    public void golpeado() {

    }
}
