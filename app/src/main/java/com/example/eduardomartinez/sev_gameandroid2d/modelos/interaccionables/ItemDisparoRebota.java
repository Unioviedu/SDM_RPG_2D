package com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Vida;

/**
 * Created by karolmc on 29/10/2017.
 */

public class ItemDisparoRebota extends Interaccionable {

    public ItemDisparoRebota(Context context, double x, double y) {
        super(context, x, y, Ar.coor(1) , Ar.coor(1));

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.item_disparo_rebota);
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
        habitacion.jugador.disparoJugador.rebota = true;
        return true;
    }
}
