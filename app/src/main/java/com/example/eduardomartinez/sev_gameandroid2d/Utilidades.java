package com.example.eduardomartinez.sev_gameandroid2d;

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
}
