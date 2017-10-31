package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

import java.util.HashMap;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public abstract class Enemigo extends Modelo {
    protected int cadenciaDisparo;
    protected long miliSegundosDisparo;

    public int estado = Estados.ACTIVO;
    private double xInicial;
    private double yInicial;
    public double velocidadX;
    public double velocidadY;

    public static final String CAMINANDO_ARRIBA = "caminando_arriba";
    public static final String CAMINANDO_ABAJO = "caminando_abajo";
    public static final String CAMINANDO_DERECHA = "caminando_derecha";
    public static final String CAMINANDO_IZQUIERDA = "caminando_izquierda";

    public static final String DISPARANDO_ARRIBA = "disparando_arriba";
    public static final String DISPARANDO_ABAJO = "disparando_abajo";
    public static final String DISPARANDO_DERECHA = "disparando_derecha";
    public static final String DISPARANDO_IZQUIERDA = "disparando_izquierda";
    public static final String EXPLOTAR = "Explotar";

    protected Sprite spriteActual;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();

    public Enemigo(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);

        xInicial = x;
        yInicial = y;
        this.x = x;
        this.y = y;

        /*cArriba = 19;
        cDerecha = 19;
        cIzquierda = 19;
        cAbajo = 19;*/

        //Sprite basico = crearSprite();
    }

    @Override
    public void dibujar(Canvas canvas) {
        if (estado != Estados.INACTIVO) {
            spriteActual.dibujarSprite(canvas, (int) x - Habitacion.scrollEjeX, (int) y - Habitacion.scrollEjeY);
        }
    }

    protected Sprite crearSprite(int animacion, String nombre, int fps, int frames, boolean bucle) {
        Sprite temp = new Sprite (CargadorGraficos.cargarDrawable(context, animacion),
                ancho, altura,
                fps, frames, bucle);
        sprites.put(nombre, temp);

        return temp;
    }

    public void destruir() {
        estado = Estados.EXPLOTANDO;
        spriteActual = sprites.get(EXPLOTAR);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public abstract void aplicarReglasMovimiento(Habitacion habitacion);

    public abstract void actualizar(long tiempo);

    public abstract DisparoEnemigo disparar (Context context, double posJugadorY, long milisegundos);
}
