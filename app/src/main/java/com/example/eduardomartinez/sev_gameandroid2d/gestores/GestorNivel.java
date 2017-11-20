package com.example.eduardomartinez.sev_gameandroid2d.gestores;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by karolmc on 25/10/2017.
 */

public class GestorNivel {

    private static final GestorNivel ourInstance = new GestorNivel();
    public int longitudJuego;

    public static GestorNivel getInstance() {
        return ourInstance;
    }

    private GestorNivel() {
    }

    public List<Habitacion>  seleccionarLongitudJuego(Context context) throws Exception {
        List<Habitacion> habitacionesNivel = new LinkedList<>();

        for(int i = 0; i < 10; i++){
            habitacionesNivel.add(new Habitacion(context, i, false));
        }
        Collections.shuffle(habitacionesNivel);

        habitacionesNivel.add(longitudJuego-1, new Habitacion(context, 10, true));

        return habitacionesNivel;

    }


}
