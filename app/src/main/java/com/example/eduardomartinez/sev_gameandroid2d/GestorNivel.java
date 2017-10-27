package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;

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
//        TODO: Generar las habitaciones aleatoriamente
//        while(habitacionesGeneradas) {
//            int numero = random.nextInt(11);
//            if (!numeroHabitaciones.contains(numero)) {
//                numeroHabitaciones.add(numero);
//                habitacionesNivel.add(new Habitacion(context, numero));
//            }
//            if(numeroHabitaciones.size() == longitudJuego) {
//                habitacionesGeneradas = true;
//            }
//        }

        for(int i = 0; i < longitudJuego; i++){
            habitacionesNivel.add(new Habitacion(context, i));
        }
        return habitacionesNivel;

    }


}
