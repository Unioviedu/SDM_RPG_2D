package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.eduardomartinez.sev_gameandroid2d.modelos.Disparo;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by karolmc on 24/10/2017.
 */

public class Habitacion {

    private Context context;

    private int numeroHabitacion;
    private Tile[][] mapaTiles;
    private boolean inicializado;
    public GameView gameView;

    private Jugador jugador;
    private List<Disparo> disparosJugador;

    public boolean botonDispararPulsado;
    public boolean botonSaltarPulsado;

    public float orientacionPadMoverX;
    public float orientacionPadMoverY;
    public float orientacionPadDispararX;
    public float orientacionPadDispararY;

    public Habitacion(Context context, int numeroHabitacion) throws Exception {
        inicializado = false;

        this.context = context;
        this.numeroHabitacion = numeroHabitacion;
        inicializar();

        inicializado = true;
    }

    public int anchoMapaTiles(){
        return mapaTiles.length;
    }

    public int altoMapaTiles(){
        return mapaTiles[0].length;
    }

    public void inicializar() throws Exception {
        inicializarMapaTiles();
        disparosJugador = new LinkedList<>();
        jugador = new Jugador(context, 250, 250);
    }

    private void inicializarMapaTiles() throws Exception {
        InputStream is = context.getAssets().open(numeroHabitacion+".txt");
        int anchoLinea;

        List<String> lineas = new LinkedList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        {
            String linea = reader.readLine();
            anchoLinea = linea.length();
            while (linea != null)
            {
                lineas.add(linea);
                if (linea.length() != anchoLinea)
                {
                    Log.e("ERROR", "Dimensiones incorrectas en la línea");
                    throw new RuntimeException("Dimensiones incorrectas en la línea.");
                }
                linea = reader.readLine();
            }
        }

        mapaTiles = new Tile[anchoLinea][lineas.size()];

        for (int y = 0; y < altoMapaTiles(); ++y) {
            for (int x = 0; x < anchoMapaTiles(); ++x) {
                char tipoDeTile = lineas.get(y).charAt(x);
                mapaTiles[x][y] = new Tile(context, altoMapaTiles(),
                                            anchoMapaTiles(),tipoDeTile, x ,y);
            }
        }
    }

    private void aplicarReglasMoviemiento() {
        int tileXJugadorIzquierda
                = (int) (jugador.x - (jugador.ancho / 2 - 1)) / Tile.ancho;
        int tileXJugadorCentro
                = (int) jugador.x / Tile.ancho;
        int tileXJugadorDerecha
                = (int) (jugador.x + (jugador.ancho / 2 - 1)) / Tile.ancho;

        int tileYJugadorInferior
                = (int) (jugador.y + (jugador.altura / 2 - 1)) / Tile.altura;
        int tileYJugadorCentro
                = (int) jugador.y / Tile.altura;
        int tileYJugadorSuperior
                = (int) (jugador.y - (jugador.altura / 2 - 1)) / Tile.altura;

        if (jugador.velocidadX > 0) {
            if (mapaTiles[tileXJugadorDerecha][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE) {
                jugador.x += jugador.velocidadX;
            }
        }
        if (jugador.velocidadX <= 0) {
            if (mapaTiles[tileXJugadorIzquierda][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE) {
                jugador.x += jugador.velocidadX;
            }
        }
        if (jugador.velocidadY <= 0) {
            if (mapaTiles[tileXJugadorCentro][tileYJugadorSuperior].tipoDeColision ==
                            Tile.PASABLE) {
                jugador.y += jugador.velocidadY;
            }
        }
        if (jugador.velocidadY > 0) {
            if (mapaTiles[tileXJugadorCentro][tileYJugadorInferior].tipoDeColision ==
                            Tile.PASABLE) {
                jugador.y += jugador.velocidadY;
            }
        }
    }

    public void dibujar (Canvas canvas) {
        if(inicializado) {

            dibujarTiles(canvas);

            for (Disparo disparoJugador: disparosJugador)
                disparoJugador.dibujar(canvas);

            jugador.dibujar(canvas);
        }
    }

    private void dibujarTiles(Canvas canvas){

        for (int y = 0; y < altoMapaTiles(); ++y) {
            for (int x = 0; x < anchoMapaTiles(); ++x) {
                if (mapaTiles[x][y].imagen != null) {
                    // Calcular la posición en pantalla correspondiente
                    // izquierda, arriba, derecha , abajo
                    mapaTiles[x][y].imagen.setBounds(
                            (x  * Tile.ancho),
                            (y * Tile.altura),
                            (x * Tile.ancho) + Tile.ancho,
                            (y * Tile.altura) + Tile.altura);
                    mapaTiles[x][y].imagen.draw(canvas);
                }
            }
        }
    }

    public void actualizar (long tiempo) {
        if (inicializado) {

            jugador.actualizar(tiempo);

            jugador.procesarOrdenes(orientacionPadMoverX, orientacionPadMoverY, botonDispararPulsado, orientacionPadDispararX, orientacionPadDispararY);

            for (Disparo disparoJugador : disparosJugador)
                disparoJugador.actualizar(tiempo);

            if (botonSaltarPulsado)
                botonSaltarPulsado = false;

            if (botonDispararPulsado) {
                disparosJugador.add(new Disparo(context, jugador.x, jugador.y,
                        jugador.orientacion));
                botonDispararPulsado = false;
            }



            aplicarReglasMoviemiento();
        }
    }
}
