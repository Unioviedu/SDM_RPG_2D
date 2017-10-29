package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Modelo;

import java.util.HashMap;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class Enemigo extends Modelo {
    protected int cadenciaDisparo;
    protected long miliSegundosDisparo;

    public int estado = Estados.ACTIVO;
    public float aceleracionX;
    public float aceleracionY;

    public static final String BASICO = "Basico";
    public static final String EXPLOTAR = "Explotar";

    private Sprite sprite;
    private HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();

    public Enemigo(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);

        //Sprite basico = crearSprite();
    }

    @Override
    public void dibujar(Canvas canvas) {
        if (estado != Estados.INACTIVO) {
            sprite.dibujarSprite(canvas, (int) x, (int) y);
        }
    }

    private Sprite crearSprite(int animacion, String nombre, int fps, int frames, boolean bucle) {
        Sprite temp = new Sprite (CargadorGraficos.cargarDrawable(context, animacion),
                ancho, altura,
                fps, frames, bucle);
        sprites.put(nombre, temp);

        return temp;
    }

    public void destruir() {
        estado = Estados.EXPLOTANDO;
        sprite = sprites.get(EXPLOTAR);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
