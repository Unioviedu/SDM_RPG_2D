package com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;

/**
 * Created by karolmc on 29/10/2017.
 */

public class ItemEscudo extends Interaccionable  {

    public ItemEscudo(Context context, double x, double y) {
        super(context, x, y, 100 , 100);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.escudo_item);
    }

    @Override
    public void dibujar(Canvas canvas) {
        int yArriva = (int)  y - Habitacion.scrollEjeY - altura / 2;
        int xIzquierda = (int) x - Habitacion.scrollEjeX - ancho / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + ancho, yArriva + altura);
        imagen.draw(canvas);
    }

    @Override
    public boolean activarItem(Habitacion habitacion){
        habitacion.jugador.escudo = true;
        habitacion.gameView.escudo.x = 0.05 * GameView.pantallaAncho + (habitacion.jugador.vidasTotales)*70;
        return true;
    }
}
