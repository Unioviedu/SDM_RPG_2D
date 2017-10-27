package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class DisparoJugador extends Modelo {
    private Sprite sprite;

    public double velocidadX;
    public double velocidadY;
    float sensibilidad = 20;
    float velocidad = 20;


    public DisparoJugador(Context context, double x, double y, int orientacion, float orientacionX, float orientacionY) {
        super(context, x, y, 110, 110);

        if(orientacionX < -sensibilidad)
            velocidadX = velocidad;
        if(orientacionX > sensibilidad)
            velocidadX = -velocidad;
        if(orientacionY < -sensibilidad)
            velocidadY = velocidad;
        if(orientacionY > sensibilidad)
            velocidadY = -velocidad;
        if((orientacionX <= sensibilidad && orientacionX >= -sensibilidad) &&
                orientacionY >= -sensibilidad && orientacionY <= sensibilidad) {
            if (orientacion == Jugador.DERECHA) {
                velocidadX = velocidad;
                velocidadY = 0;
            } else {
                velocidadX = -velocidad;
                velocidadY = 0;
            }
        }

        cDerecha = 6;
        cIzquierda = 6;
        cArriba = 6;
        cAbajo = 6;

        inicializar();
    }

    private void inicializar() {
        sprite = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_disparo1),
                ancho, altura,
                24, 4, true);
    }

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas) {
        //sprite.dibujarSprite(canvas, (int) x - Habitacion.ScrollX,  (int) y - Habitacion.ScrollY);
        sprite.dibujarSprite(canvas, (int) x,  (int) y);
    }


}
