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

public class DisparoEnemigoRebota extends DisparoEnemigo {
    private int orientacion;

    public DisparoEnemigoRebota(Context context, double x, double y, int orientacion) {
        super(context, x, y);

        this.orientacion = orientacion;
        if (orientacion == Jugador.ARRIBA) {
            velocidadY = -10;
            sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                    R.drawable.disapro_enemigo_rebota_arriba),
                    ancho, altura,
                    24, 4, true);
        }
        else {
            velocidadY = 10;
            sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                    R.drawable.disapro_enemigo_rebota_abajo),
                    ancho, altura,
                    24, 4, true);
        }

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
