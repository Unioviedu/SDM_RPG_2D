package com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos;

import android.content.Context;

import com.example.eduardomartinez.sev_gameandroid2d.Ar;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Tile;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigo;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.disparos.DisparoEnemigoRebota;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 25/10/17.
 */

public class EnemigoRebota extends Enemigo {

    public EnemigoRebota(Context context, double x, double y) {
        super(context, x, y, Ar.coor(1), Ar.coor(1));

        velocidadX = 30;

        cadenciaDisparo = 1000;
        miliSegundosDisparo = 0;

        spriteActual = crearSprite(R.drawable.enemigo_izq_der_mov,
                CAMINANDO_DERECHA,
                20, 4, true);
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

        if (velocidadX > 0)
            aplicarReglasMovimientoDerecha(habitacion, tileXEnemigoIzquierda, tileXEnemigoDerecha,
                    tileYEnemigoInferior, tileYEnemigoCentro, tileYEnemigoSuperior);
        else
            aplicarReglasMovimientoIzquierda(habitacion, tileXEnemigoIzquierda, tileXEnemigoDerecha,
                    tileYEnemigoInferior, tileYEnemigoCentro, tileYEnemigoSuperior);
    }

    @Override
    public void actualizar(long tiempo) {
        spriteActual.actualizar(tiempo);
    }

    @Override
    public List<DisparoEnemigo> disparar(Context context, double posJugadorX, double posJugadorY,
                                   long milisegundos) {
        int orientacion;

        if (milisegundos - miliSegundosDisparo> cadenciaDisparo
                + Math.random()* cadenciaDisparo) {
            miliSegundosDisparo = milisegundos;

            if (posJugadorY < y)
                orientacion = Jugador.ARRIBA;
            else
                orientacion = Jugador.ABAJO;
        } else {
            return null;
        }

        List<DisparoEnemigo> disparosEnemigo = new LinkedList<>();
        disparosEnemigo.add(new DisparoEnemigoRebota(context, x, y, orientacion));

        return disparosEnemigo;
    }

    @Override
    public void golpeado() {
        estado = Estados.INACTIVO;
    }

    private void aplicarReglasMovimientoDerecha(Habitacion habitacion, int tileXEnemigoIzquierda,
                                                int tileXEnemigoDerecha, int tileYEnemigoInferior,
                                                int tileYEnemigoCentro, int tileYEnemigoSuperior) {
        if (habitacion.mapaTiles[tileXEnemigoDerecha + 1][tileYEnemigoInferior].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha + 1][tileYEnemigoCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha + 1][tileYEnemigoSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoInferior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoDerecha][tileYEnemigoSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            x += velocidadX;
        } else {
            int TileJugadorBordeDerecho = tileXEnemigoDerecha * Tile.ancho + Tile.ancho;
            double distanciaX = TileJugadorBordeDerecho - (x + cDerecha);

            if (distanciaX > 0) {
                double velocidadNecesaria = Math.min(distanciaX, velocidadX);
                x += velocidadNecesaria;
            } else {
                velocidadX = -velocidadX;
            }
        }
    }

    private void aplicarReglasMovimientoIzquierda(Habitacion habitacion, int tileXEnemigoIzquierda,
                                                int tileXEnemigoDerecha, int tileYEnemigoInferior,
                                                int tileYEnemigoCentro, int tileYEnemigoSuperior) {
        if (habitacion.mapaTiles[tileXEnemigoIzquierda - 1][tileYEnemigoInferior].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda - 1][tileYEnemigoCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda - 1][tileYEnemigoSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoInferior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXEnemigoIzquierda][tileYEnemigoSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            x += velocidadX;
        } else {
            int TileJugadorBordeIzquierdo = tileXEnemigoIzquierda * Tile.ancho;
            double distanciaX = (x - cIzquierda) - TileJugadorBordeIzquierdo;

            if (distanciaX > 0) {
                double velocidadNecesaria = Utilidades.proximoACero(-distanciaX, velocidadX);
                x += velocidadNecesaria;
            } else {
                velocidadX = -velocidadX;
            }
        }
    }
}
