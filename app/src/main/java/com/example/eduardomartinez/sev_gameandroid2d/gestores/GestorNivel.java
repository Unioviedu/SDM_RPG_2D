package com.example.eduardomartinez.sev_gameandroid2d.gestores;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;

import java.util.ArrayList;
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

        boolean habitacionesGeneradas = false;
        List<Habitacion> habitacionesNivel = new LinkedList<>();
        List<Integer> numeroHabitaciones = new ArrayList<>();
        Random random = new Random();
        //TODO: Generar las habitaciones aleatoriamente

        for(int i = 0; i < longitudJuego; i++){
            habitacionesNivel.add(new Habitacion(context, i));
        }
        return habitacionesNivel;

    }


}
