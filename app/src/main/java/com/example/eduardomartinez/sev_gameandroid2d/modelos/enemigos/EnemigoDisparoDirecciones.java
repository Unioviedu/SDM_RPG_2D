package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class EnemigoDisparoDirecciones  extends Enemigo{

    public EnemigoDisparoDirecciones(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);
    }

    @Override
    public void aplicarReglasMovimiento(Habitacion habitacion) {

    }

    @Override
    public void actualizar(long tiempo) {

    }
}
