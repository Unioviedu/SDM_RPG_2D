package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class EnemigoDisparoDirecciones  extends Enemigo{
    private double velocidad = 10;

    public EnemigoDisparoDirecciones(Context context, double x, double y) {
        super(context, x, y, 59*2, 50*2);

        velocidadX = 20;

        cadenciaDisparo = 100;
        miliSegundosDisparo = 0;

        spriteActual = crearSprite(R.drawable.protagonista_animacion_caminando_izquierda,
                CAMINANDO_DERECHA,
                6, 6, true);
    }

    @Override
    public void aplicarReglasMovimiento(Habitacion habitacion) {

    }

    @Override
    public void actualizar(long tiempo) {

    }

    @Override
    public DisparoEnemigo disparar(Context context, double posJugadorX, double posJugadorY,
                                   long milisegundos) {

        if (milisegundos - miliSegundosDisparo> cadenciaDisparo
                + Math.random()* cadenciaDisparo) {

            miliSegundosDisparo = milisegundos;

            int orientacion = Utilidades.orientacion8Direcciones(x, y, posJugadorX, posJugadorY);

            return new DisparoEnemigoDirecciones(context, x, y, orientacion);
        } else
            return null;
    }
}
