package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigo;

/**
 * Created by eduardomartinez on 29/10/17.
 */

public class DisparoEnemigoDirecciones extends DisparoEnemigo {
    private Sprite sprite;
    private int velocidad = 20;

    public DisparoEnemigoDirecciones(Context context, double x, double y, int orientacion) {
        super(context, x, y);

        if (orientacion == Jugador.ARRIBA)
            velocidadY = -velocidad;
        else if (orientacion == Jugador.ABAJO)
            velocidadY = velocidad;
        else if (orientacion == Jugador.DERECHA)
            velocidadX = velocidad;
        else if (orientacion == Jugador.IZQUIERDA)
            velocidadX = -velocidad;

        else if (orientacion == Jugador.DERECHA_ARRIBA) {
            velocidadX = velocidad;
            velocidadY = -velocidad;
        }

        else if (orientacion == Jugador.DERECHA_ABAJO) {
            velocidadX = velocidad;
            velocidadY = velocidad;
        }

        else if (orientacion == Jugador.IZQUIERDA_ARRIBA) {
            velocidadX = -velocidad;
            velocidadY = -velocidad;
        }

        else if (orientacion == Jugador.IZQUIERDA_ABAJO) {
            velocidadX = -velocidad;
            velocidadY = velocidad;
        }


        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.disparo_direcciones),
                ancho, altura,
                1, 1, true);
    }

    public void inicializar() {

    }

    @Override
    public void dibujar(Canvas canvas) {

        sprite.dibujarSprite(canvas, (int) x - Habitacion.scrollEjeX, (int) y - Habitacion.scrollEjeY);
    }

    @Override
    public void rebota(Habitacion habitacion) {

    }


}
