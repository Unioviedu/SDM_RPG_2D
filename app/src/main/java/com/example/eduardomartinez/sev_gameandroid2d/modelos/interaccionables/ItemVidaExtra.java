package com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Vida;

/**
 * Created by karolmc on 29/10/2017.
 */

public class ItemVidaExtra extends Interaccionable {

    public ItemVidaExtra(Context context, double x, double y) {
        super(context, x, y, 100 , 100);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.item_vida_extra);
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
        habitacion.jugador.vidasTotales++;
        habitacion.jugador.vidasActuales++;
        habitacion.gameView.escudo.x = 0.05 * GameView.pantallaAncho + (habitacion.jugador.vidasTotales)*70;
        habitacion.gameView.vidas.add(new Vida(habitacion.gameView.context, 0.05 * GameView.pantallaAncho + (habitacion.jugador.vidasTotales-1)*70, 0.05 * GameView.pantallaAlto));
        return true;
    }
}
