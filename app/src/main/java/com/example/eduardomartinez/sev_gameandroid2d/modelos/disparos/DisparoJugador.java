package com.example.eduardomartinez.sev_gameandroid2d.modelos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.Tile;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public abstract class DisparoJugador extends Modelo {

    public double velocidadX;
    public double velocidadY;
    float velocidad;
    double orientacionX;
    double orientacionY;

    public boolean rebota = false;

    int cont = 0;

    public static int MAX_NUMERO_REBOTES = 5;


    public DisparoJugador(Context context, double x, double y, double orientacionX, double orientacionY, boolean rebota) {
        super(context, x, y, Ar.coor(1), Ar.coor(1));

        cArriba = 6;
        cAbajo = 6;
        cDerecha = 6;
        cIzquierda = 6;
        this.orientacionX = orientacionX;
        this.orientacionY = orientacionY;
        this.context = context;

        this.rebota = rebota;

        inicializar(  orientacionX,  orientacionY);
    }

    protected void inicializar(double orientacionX, double orientacionY) {

        doInicializar();

        int orientacionDisparo = Utilidades.orientacion(orientacionX, orientacionY);

        if (orientacionDisparo == Jugador.DERECHA)
            velocidadX = velocidad;
        else if (orientacionDisparo == Jugador.IZQUIERDA)
            velocidadX = -velocidad;
        else if (orientacionDisparo == Jugador.ARRIBA)
            velocidadY = -velocidad;
        else if (orientacionDisparo == Jugador.ABAJO)
            velocidadY = velocidad;
    }

    @Override
    public void dibujar(Canvas canvas){
        int yArriva = (int)  y - Habitacion.scrollEjeY - altura / 2;
        int xIzquierda = (int) x - Habitacion.scrollEjeX - ancho / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + ancho, yArriva + altura);
        imagen.draw(canvas);
    }

    protected abstract void doInicializar();

    public abstract DisparoJugador disparar(double x, double y, double orientacionX, double orientacionY, boolean rebota);

    public void rebota(Habitacion habitacion) {
        double alturaHabitacion = habitacion.altoMapaTiles()* Tile.altura;
        double anchuraHabitacion = habitacion.anchoMapaTiles()*Tile.ancho;

        double distanciaArriba = Math.abs(0-y);
        double distanciaAbajo = alturaHabitacion - y;
        double distanciaIzquierda = Math.abs(0-x);
        double distanciaDerecha = anchuraHabitacion - x;

        if (x > anchuraHabitacion/2) {
            if (y > alturaHabitacion/2) {
                if (distanciaDerecha < distanciaAbajo)
                    paredLateral();
                else
                    paredArribaAbajo();
            }else {
                if (distanciaDerecha < distanciaArriba)
                    paredLateral();
                else
                    paredArribaAbajo();
            }
        } else {
            if (y > alturaHabitacion/2) {
                if (distanciaIzquierda < distanciaAbajo)
                    paredLateral();
                else
                    paredArribaAbajo();
            }else {
                if (distanciaIzquierda < distanciaArriba) {
                    paredLateral();
                } else
                    paredArribaAbajo();
            }
        }

        cont++;

        if (cont >= MAX_NUMERO_REBOTES)
            rebota = false;
    }

    private void paredArribaAbajo() {
        velocidadY = -velocidadY;
    }

    private void paredLateral() {
        velocidadX = -velocidadX;
    }


}
