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

public class ItemPasarHabitacion extends Interaccionable {

    public boolean activa;

    public ItemPasarHabitacion(Context context, double x, double y) {
        super(context, x, y, 100 , 100);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.puerta);
    }

    @Override
    public void dibujar(Canvas canvas) {
        if(activa) {
            int yArriva = (int) y - Habitacion.scrollEjeY - altura / 2;
            int xIzquierda = (int) x - Habitacion.scrollEjeX - ancho / 2;

            imagen.setBounds(xIzquierda, yArriva, xIzquierda
                    + ancho, yArriva + altura);
            imagen.draw(canvas);
        }
    }

    @Override
    public boolean activarItem(Habitacion habitacion){
        if(activa){
            habitacion.gameView.habitacionCompleta();
        }
        return false;
    }
}
