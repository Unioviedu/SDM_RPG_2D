package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;

import java.util.HashMap;

/**
 * Created by karolmc on 29/10/2017.
 */

public class Vida extends Modelo {
    public static final String VIDA_LLENA = "vida_llena";
    public static final String VIDA_VACIA = "vida_vacia";


    private HashMap<String, Drawable> iconos = new HashMap<>();

    public Vida(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);
        iconos.put(VIDA_LLENA, CargadorGraficos.cargarDrawable(context, R.drawable.vida_llena));
        iconos.put(VIDA_VACIA, CargadorGraficos.cargarDrawable(context, R.drawable.vida_vacia));

        imagen = iconos.get(VIDA_LLENA);
    }

    public Vida(Context context, double x, double y) {
        this(context, x, y, 60, 60);
    }

    public void setVidaLlena() {
        imagen = iconos.get(VIDA_LLENA);
    }

    public void setVidaVacia() {
        imagen = iconos.get(VIDA_VACIA);
    }
}
