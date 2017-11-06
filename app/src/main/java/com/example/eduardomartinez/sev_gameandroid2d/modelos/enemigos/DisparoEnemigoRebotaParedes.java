package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Tile;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;

/**
 * Created by eduardomartinez on 6/11/17.
 */

public class DisparoEnemigoRebotaParedes extends DisparoEnemigo {
    private double velocidad = 10;
    int cont = 0;

    public DisparoEnemigoRebotaParedes(Context context, double x, double y) {
        super(context, x, y);

        velocidadX = velocidad;
        velocidadY = velocidad;

        rebota = true;

        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_disparo1),
                ancho, altura,
                24, 4, true);
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

        Log.i("distancia", "arriba "+distanciaArriba+" abajo "+distanciaAbajo+" derecha "+distanciaDerecha+ " izquierda "+distanciaIzquierda);

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
    }

    private void paredArribaAbajo() {
        velocidadY = -velocidadY;
    }

    private void paredLateral() {
        velocidadX = -velocidadX;
    }
}
