package com.example.eduardomartinez.sev_gameandroid2d.modelos.controles;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.GameView;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by eduardomartinez on 26/10/17.
 */

public class BotonDisparo extends Modelo {

    public BotonDisparo(Context context) {
        super(context, GameView.pantallaAncho*0.85, GameView.pantallaAlto*0.6,
                70, 70);

        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.buttonfire);
    }

    public boolean estaPulsado(float clicX, float clicY) {
        boolean estaPulsado = false;

        if (clicX <= (x + ancho / 2) && clicX >= (x - ancho / 2)
                && clicY <= (y + altura / 2) && clicY >= (y - altura / 2))
            estaPulsado = true;
        return estaPulsado;
    }
}
