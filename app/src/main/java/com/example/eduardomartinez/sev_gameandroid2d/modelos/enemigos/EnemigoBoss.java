package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigo;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigoRebota;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigoRebotaParedes;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 7/11/17.
 */

public class EnemigoBoss extends Enemigo {
    public int vidas = 5;

    public EnemigoBoss(Context context, double x, double y) {
        super(context, x, y, Ar.coor(3), Ar.coor(2.5));

        cadenciaDisparo = 1200;

        spriteActual = crearSprite(R.drawable.boss,
                CAMINANDO_DERECHA,
                24, 4, true);
    }

    @Override
    public void aplicarReglasMovimiento(Habitacion habitacion) {

    }

    @Override
    public void actualizar(long tiempo) {
        spriteActual.actualizar(tiempo);
    }

    @Override
    public List<DisparoEnemigo> disparar(Context context, double posJugadorX, double posJugadorY, long milisegundos) {
        if (milisegundos - miliSegundosDisparo> cadenciaDisparo
                + Math.random()* cadenciaDisparo) {

            miliSegundosDisparo = milisegundos;

            List<DisparoEnemigo> disparosEnemigo = new LinkedList<>();

            DisparoEnemigo dis1 = new DisparoEnemigoRebota(context, x, y, Jugador.ABAJO);
            DisparoEnemigo dis2 = new DisparoEnemigoRebotaParedes(context, x, y, Jugador.IZQUIERDA_ABAJO);
            DisparoEnemigo dis3 = new DisparoEnemigoRebotaParedes(context, x, y, Jugador.DERECHA_ABAJO);

            Sprite imagen = new Sprite (CargadorGraficos.cargarDrawable(context, R.drawable.disparo_boss),
                    ancho, altura,
                    1, 1, true);

            dis1.sprite = imagen;
            dis2.sprite = imagen;
            dis3.sprite = imagen;

            disparosEnemigo.add(dis1);
            disparosEnemigo.add(dis2);
            disparosEnemigo.add(dis3);

            return disparosEnemigo;
        } else
            return null;
    }

    @Override
    public void golpeado() {
        vidas--;

        if (vidas == 0)
            estado = Estados.INACTIVO;
    }
}
