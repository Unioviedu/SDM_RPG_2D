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

public class DisparoJugadorRapido extends DisparoJugador {

    public DisparoJugadorRapido(Context context, double x, double y, double orientacionX, double orientacionY) {
        super(context, x, y, orientacionX, orientacionY);

        velocidad = 100;

        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_disparo1),
                ancho, altura,
                24, 4, true);

        inicializar(orientacionX,orientacionY);
    }

    @Override
   protected void inicializar( double orientacionX, double orientacionY) {
        int orientacionDisparo = Utilidades.orientacion(orientacionX, orientacionY);

        if (orientacionDisparo == Jugador.DERECHA)
            velocidadX = velocidad;
        else if (orientacionDisparo == Jugador.IZQUIERDA)
            velocidadX = -velocidad;
        else if (orientacionDisparo == Jugador.ARRIBA)
            velocidadY = -velocidad;
        else if (orientacionDisparo == Jugador.ABAJO)
            velocidadY = velocidad;
    }

    @Override
    public DisparoJugadorRapido disparar(Context context, double x, double y, double orientacionPadDispararX, double orientacionPadDispararY) {
        return new DisparoJugadorRapido(context, x, y, orientacionPadDispararX, orientacionPadDispararY);
    }
}
