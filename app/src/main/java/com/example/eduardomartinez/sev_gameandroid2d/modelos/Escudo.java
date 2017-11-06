package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;

import java.util.HashMap;

/**
 * Created by karolmc on 29/10/2017.
 */

public class Escudo extends Modelo {

    public Escudo(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, Ar.coor(0.4), Ar.coor(0.4));

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.escudo);
    }

    public Escudo(Context context, double x, double y) {
        this(context, x, y, 60, 60);
    }

    @Override
    public void dibujar(Canvas canvas){
        int yArriva = (int)  y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + ancho, yArriva + altura);
        imagen.draw(canvas);
    }


}

