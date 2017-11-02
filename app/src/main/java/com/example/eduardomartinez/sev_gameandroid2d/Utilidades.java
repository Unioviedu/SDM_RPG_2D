package com.example.eduardomartinez.sev_gameandroid2d;

import android.util.Log;

import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.controles.PadMovimiento;

/**
 * Created by karolmc on 26/10/2017.
 */

public class Utilidades {

    public static double proximoACero(double a, double b) {
        if (Math.pow(a,2) <  Math.pow(b,2))
            return a;
        else
            return b;

    }

    public static int orientacion (double orientacionX, double orientacionY) {
        int sensibilidad = 30;
        int orientacion;

        if (orientacionY < sensibilidad && orientacionY > -sensibilidad) {
            if (orientacionX > 0)
                orientacion = Jugador.DERECHA;
            else
                orientacion = Jugador.IZQUIERDA;
        } else if (orientacionX < sensibilidad && orientacionX > -sensibilidad) {
            if (orientacionY > 0)
                orientacion = Jugador.ABAJO;
            else
                orientacion = Jugador.ARRIBA;
        } else {
            if (orientacionX > 0) {
                if (orientacionX > Math.abs(orientacionY))
                    orientacion = Jugador.DERECHA;
                else if (orientacionY > 0)
                    orientacion = Jugador.ABAJO;
                else
                    orientacion = Jugador.ARRIBA;
            } else {
                if (Math.abs(orientacionX) > Math.abs(orientacionY))
                    orientacion = Jugador.IZQUIERDA;
                else if (orientacionY > 0)
                    orientacion = Jugador.ABAJO;
                else
                    orientacion = Jugador.ARRIBA;
            }
        }

        return orientacion;
    }
}
