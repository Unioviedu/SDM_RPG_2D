package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by eduardomartinez on 29/10/17.
 */

public class DisparoEnemigoRebota extends DisparoEnemigo {
    private Sprite sprite;

    public DisparoEnemigoRebota(Context context, double x, double y, int orientacion) {
        super(context, x, y);

        if (orientacion == Jugador.ARRIBA)
            velocidadY = -10;
        else
            velocidadY = 10;

        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_disparo1),
                ancho, altura,
                24, 4, true);
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
