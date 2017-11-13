package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Tile;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigo;

/**
 * Created by eduardomartinez on 6/11/17.
 */

public class DisparoEnemigoRebotaParedes extends DisparoEnemigo {
    private double velocidad = 18;
    int cont = 0;

    public static int MAX_NUMERO_REBOTES = 5;

    public DisparoEnemigoRebotaParedes(Context context, double x, double y, int orientacion) {
        super(context, x, y);

        if (orientacion == Jugador.DERECHA_ABAJO) {
            velocidadX = velocidad;
            velocidadY = velocidad;
        } else if (orientacion == Jugador.DERECHA_ARRIBA) {
            velocidadX = velocidad;
            velocidadY = -velocidad;
        } else if (orientacion == Jugador.IZQUIERDA_ABAJO) {
            velocidadX = -velocidad;
            velocidadY = velocidad;
        } else if (orientacion == Jugador.IZQUIERDA_ARRIBA) {
            velocidadX = -velocidad;
            velocidadY = -velocidad;
        }

        rebota = true;

        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.disparo_rebota_paredes),
                ancho, altura,
                1, 1, true);
    }

    @Override
    protected void inicializar() {

    }

    @Override
    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x - Habitacion.scrollEjeX, (int) y - Habitacion.scrollEjeY);
    }

    @Override
    public void rebota(Habitacion habitacion) {
        double alturaHabitacion = habitacion.altoMapaTiles()*Tile.altura;
        double anchuraHabitacion = habitacion.anchoMapaTiles()*Tile.ancho;

        double distanciaArriba = Math.abs(0-y);
        double distanciaAbajo = alturaHabitacion - y;
        double distanciaIzquierda = Math.abs(0-x);
        double distanciaDerecha = anchuraHabitacion - x;

        if (x > anchuraHabitacion/2) {
            if (y > alturaHabitacion/2) {
                if (distanciaDerecha < distanciaAbajo)
                    paredLateral();
                else
                    paredArribaAbajo();
            }else {
                if (distanciaDerecha < distanciaArriba)
                    paredLateral();
                else
                    paredArribaAbajo();
            }
        } else {
            if (y > alturaHabitacion/2) {
                if (distanciaIzquierda < distanciaAbajo)
                    paredLateral();
                else
                    paredArribaAbajo();
            }else {
                if (distanciaIzquierda < distanciaArriba) {
                    paredLateral();
                } else
                    paredArribaAbajo();
            }
        }

        cont++;

        if (cont >= MAX_NUMERO_REBOTES)
            rebota = false;
    }

    private void paredArribaAbajo() {
        velocidadY = -velocidadY;
    }

    private void paredLateral() {
        velocidadX = -velocidadX;
    }
}
