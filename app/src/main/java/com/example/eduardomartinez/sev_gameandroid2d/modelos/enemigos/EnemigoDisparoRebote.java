package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Tile;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigo;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigoRebotaParedes;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class EnemigoDisparoRebote extends Enemigo {
    int cont = 0;
    int orientacionDisparo = Jugador.DERECHA_ABAJO;

    public EnemigoDisparoRebote(Context context, double x, double y) {
        super(context, x, y, Ar.coor(1), Ar.coor(1));

        cadenciaDisparo = 1800;
        velocidadY = -20;

        spriteActual = crearSprite(R.drawable.enemigo_dispara_rebota,
                CAMINANDO_DERECHA,
                3, 3, true);
    }

    @Override
    public void aplicarReglasMovimiento(Habitacion habitacion) {
        int tileXEnemigoIzquierda
                = (int) (x - (cIzquierda - 1)) / Tile.ancho;
        int tileXEnemigoDerecha
                = (int) (x + (cDerecha - 1)) / Tile.ancho;

        int tileYEnemigoInferior
                = (int) (y + (cAbajo - 1)) / Tile.altura;
        int tileYEnemigoCentro
                = (int) y / Tile.altura;
        int tileYEnemigoSuperior
                = (int) (y - (cArriba - 1)) / Tile.altura;

        if (velocidadY > 0)
            aplicarReglasMovimientoAbajo(habitacion, tileXEnemigoIzquierda, tileXEnemigoDerecha,
                    tileYEnemigoInferior, tileYEnemigoCentro, tileYEnemigoSuperior);
        else
            aplicarReglasMovimientoArriba(habitacion, tileXEnemigoIzquierda, tileXEnemigoDerecha,
                    tileYEnemigoInferior, tileYEnemigoCentro, tileYEnemigoSuperior);
    }

    private void aplicarReglasMovimientoAbajo(Habitacion habitacion, int tileXEnemigoIzquierda, int tileXEnemigoDerecha,
                                              int tileYEnemigoInferior, int tileYEnemigoCentro, int tileYEnemigoSuperior) {
        if (habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoInferior + 1].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoInferior + 1].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoInferior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoInferior].tipoDeColision ==
                        Tile.PASABLE) {

            y += velocidadY;
        } else {
            int TileJugadorBordeAbajo = tileYEnemigoInferior * Tile.altura + Tile.altura;
            double distanciaY = TileJugadorBordeAbajo - (y + cAbajo);

            if (distanciaY > 0) {
                double velocidadNecesaria = Math.min(distanciaY, velocidadY);
                y += velocidadNecesaria;
            } else {
                velocidadY = -velocidadY;
            }
        }
    }

    private void aplicarReglasMovimientoArriba(Habitacion habitacion, int tileXEnemigoIzquierda,
                                               int tileXEnemigoDerecha, int tileYEnemigoInferior,
                                               int tileYEnemigoCentro, int tileYEnemigoSuperior) {
        if (habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoSuperior - 1].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoSuperior - 1].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            y += velocidadY;
        } else {
            int TileJugadorBordeArriba = tileYEnemigoSuperior * Tile.altura;
            double distanciaY = (y - cArriba) - TileJugadorBordeArriba;

            if (distanciaY > 0) {
                double velocidadNecesaria = Utilidades.proximoACero(-distanciaY, velocidadY);
                y += velocidadNecesaria;
            } else {
                velocidadY = -velocidadY;
            }
        }
    }

    @Override
    public void actualizar(long tiempo) {

        spriteActual.actualizar(tiempo);
    }

    @Override
    public List<DisparoEnemigo> disparar(Context context, double posJugadorX, double posJugadorY,
                                         long milisegundos) {

        if (milisegundos - miliSegundosDisparo> cadenciaDisparo
                + Math.random()* cadenciaDisparo) {

            miliSegundosDisparo = milisegundos;

            nuevaOrientacionDisparo();

            List<DisparoEnemigo> disparosEnemigo = new LinkedList<>();

            disparosEnemigo.add(new DisparoEnemigoRebotaParedes(context, x, y, orientacionDisparo));

            return disparosEnemigo;
        } else
            return null;
    }

    private void nuevaOrientacionDisparo() {
        switch (orientacionDisparo) {
            case Jugador.DERECHA_ABAJO:
                orientacionDisparo = Jugador.DERECHA_ARRIBA;
                return;
            case Jugador.DERECHA_ARRIBA:
                orientacionDisparo = Jugador.IZQUIERDA_ABAJO;
                return;
            case Jugador.IZQUIERDA_ABAJO:
                orientacionDisparo = Jugador.IZQUIERDA_ARRIBA;
                return;
            case Jugador.IZQUIERDA_ARRIBA:
                orientacionDisparo = Jugador.DERECHA_ABAJO;
                return;
        }
    }

    @Override
    public void golpeado() {
        estado = Estados.INACTIVO;
    }
}
