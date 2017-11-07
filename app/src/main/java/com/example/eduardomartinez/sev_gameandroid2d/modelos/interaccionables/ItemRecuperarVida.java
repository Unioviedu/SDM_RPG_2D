package com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;

/**
 * Created by karolmc on 29/10/2017.
 */

public class ItemRecuperarVida extends Interaccionable {

    public ItemRecuperarVida(Context context, double x, double y) {
        super(context, x, y, 100 , 100);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.item_recuperar_vida);
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
        Jugador jugador = habitacion.jugador;
        int vidasAntes = jugador.vidasActuales;
        if(vidasAntes < jugador.vidasTotales){
            habitacion.jugador.vidasActuales++;
        }
        return vidasAntes < jugador.vidasTotales;
    }
}
