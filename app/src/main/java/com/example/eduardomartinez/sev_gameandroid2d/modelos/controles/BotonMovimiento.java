package com.example.eduardomartinez.sev_gameandroid2d.modelos.controles;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by karolmc on 26/10/2017.
 */

public class BotonMovimiento extends Modelo {

    public BotonMovimiento(Context context) {
        super(context, GameView.pantallaAncho*0.15 , GameView.pantallaAlto*0.8,
                280,280);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.pad);
    }

    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (x + ancho / 2) && clickX >= (x - ancho / 2)
                && clickY <= (y + altura / 2) && clickY >= (y - altura / 2)) {
            estaPulsado = true;
        }
        return estaPulsado;
    }

    public int getOrientacionX(float cliclX) {
        return (int) (x - cliclX);
    }

    public int getOrientacionY(float cliclY) {
        return (int) (y - cliclY);
    }
}
