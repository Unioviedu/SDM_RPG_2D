package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.eduardomartinez.sev_gameandroid2d.modelos.DisparoJugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by karolmc on 24/10/2017.
 */

public class Habitacion {

    private Context context;

    private int numeroHabitacion;
    public Tile[][] mapaTiles;
    private boolean inicializado;
    public GameView gameView;

    private Jugador jugador;
    private List<DisparoJugador> disparosJugador;

    public boolean botonDispararPulsado;
    public boolean botonSaltarPulsado;

    public float orientacionPadMoverX;
    public float orientacionPadMoverY;
    public double orientacionPadDispararX;
    public double orientacionPadDispararY;

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
        jugador.aplicarReglasMovimiento(this);

    }

    public void dibujar (Canvas canvas) {
        if(inicializado) {

            dibujarTiles(canvas);

            for (DisparoJugador disparoJugador: disparosJugador)
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

            for (DisparoJugador disparoJugador : disparosJugador)
                disparoJugador.actualizar(tiempo);

            if (botonDispararPulsado) {
                disparosJugador.add(new DisparoJugador(context, jugador.x, jugador.y,
                        jugador.orientacion, orientacionPadDispararX, orientacionPadDispararY));
                botonDispararPulsado = false;
            }

            aplicarReglasMoviemiento();
            aplicarReglasDisparoJugador();
        }
    }

    private void aplicarReglasDisparoJugador() {
        for (Iterator<DisparoJugador> iterator = disparosJugador.iterator();
             iterator.hasNext(); ) {

            DisparoJugador disparoJugador = iterator.next();

            int tileXDisparo = (int) disparoJugador.x / Tile.ancho;
            int tileYDisparoInferior =
                    (int) (disparoJugador.y + disparoJugador.cAbajo) / Tile.altura;
            int tileYDisparoSuperior =
                    (int) (disparoJugador.y - disparoJugador.cArriba) / Tile.altura;

            if (disparoJugador.velocidadX > 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorDerecha(tileXDisparo, tileYDisparoInferior,
                        tileYDisparoSuperior, disparoJugador);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }

            if (disparoJugador.velocidadX < 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorIzquierda(tileXDisparo, tileYDisparoInferior,
                        tileYDisparoSuperior, disparoJugador);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }


            int tileYDisparo = (int) disparoJugador.y / Tile.altura;
            int tileXDisparoDerecha =
                    (int) (disparoJugador.x + disparoJugador.cDerecha) / Tile.ancho;
            int tileXDisparoIzquierda =
                    (int) (disparoJugador.x - disparoJugador.cIzquierda) / Tile.ancho;

            if (disparoJugador.velocidadY > 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorAbajo(tileYDisparo, tileXDisparoDerecha,
                        tileXDisparoIzquierda, disparoJugador);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }

            if (disparoJugador.velocidadY < 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorArriba(tileYDisparo, tileXDisparoDerecha,
                        tileXDisparoIzquierda, disparoJugador);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }

        }
    }

    private boolean aplicarReglasDisparoJugadorDerecha(int tileXDisparo, int tileYDisparoInferior,
                                                    int tileYDisparoSuperior, DisparoJugador disparoJugador) {
        boolean iteratorRemove = false;
            if (tileXDisparo + 1 <= anchoMapaTiles() - 1 &&
                    mapaTiles[tileXDisparo + 1][tileYDisparoInferior].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXDisparo + 1][tileYDisparoSuperior].tipoDeColision
                            == Tile.PASABLE) {
                disparoJugador.x += disparoJugador.velocidadX;
            } else if (tileXDisparo <= anchoMapaTiles() - 1) {
                int TileDisparoBordeDerecho = tileXDisparo * Tile.ancho + Tile.ancho;
                double distanciaX =
                        TileDisparoBordeDerecho - (disparoJugador.x + disparoJugador.cDerecha);

                if (distanciaX > 0) {
                    double velocidadNecesaria =
                            Math.min(distanciaX, disparoJugador.velocidadX);
                    disparoJugador.x += velocidadNecesaria;
                } else {
                    iteratorRemove = true;
                }
            }

            return iteratorRemove;
    }

    private boolean aplicarReglasDisparoJugadorIzquierda(int tileXDisparo, int tileYDisparoInferior,
                                                       int tileYDisparoSuperior, DisparoJugador disparoJugador) {
        boolean iteratorRemove = false;
        if (tileXDisparo - 1 >= 0 && tileYDisparoSuperior < altoMapaTiles()-1 &&
                mapaTiles[tileXDisparo - 1][tileYDisparoInferior].tipoDeColision
                        == Tile.PASABLE &&
                mapaTiles[tileXDisparo - 1][tileYDisparoSuperior].tipoDeColision
                        == Tile.PASABLE) {
            disparoJugador.x += disparoJugador.velocidadX;
        } else if (tileXDisparo >= 0) {
            int TileDisparoBordeIzquierdo = tileXDisparo * Tile.ancho;
            double distanciaX =
                    (disparoJugador.x - disparoJugador.cIzquierda) - TileDisparoBordeIzquierdo;

            if (distanciaX > 0) {
                double velocidadNecesaria =
                        Utilidades.proximoACero(-distanciaX, disparoJugador.velocidadX);
                disparoJugador.x += velocidadNecesaria;
            } else {
                iteratorRemove = true;
            }
        }

        return iteratorRemove;
    }

    private boolean aplicarReglasDisparoJugadorAbajo(int tileYDisparo, int tileXDisparoDerecha,
                                                       int tileXDisparoIzquierda, DisparoJugador disparoJugador) {
        boolean iteratorRemove = false;
        if (tileYDisparo+1 <= altoMapaTiles()-1 &&
                mapaTiles[tileXDisparoDerecha][tileYDisparo+1].tipoDeColision
                == Tile.PASABLE &&
                mapaTiles[tileXDisparoIzquierda][tileYDisparo+1].tipoDeColision
                == Tile.PASABLE) {
            disparoJugador.y += disparoJugador.velocidadY;
        } else if (tileYDisparo <= altoMapaTiles() - 1) {
                int TileDisparoBordeAbajo = tileYDisparo * Tile.altura + Tile.altura;
                double distanciaY =
                        TileDisparoBordeAbajo - (disparoJugador.y + disparoJugador.cAbajo);

                if (distanciaY > 0) {
                    double velocidadNecesaria =
                            Math.min(distanciaY, disparoJugador.velocidadY);
                    disparoJugador.y += velocidadNecesaria;
                } else {
                    iteratorRemove = true;
                }
        }
        return iteratorRemove;
    }

    private boolean aplicarReglasDisparoJugadorArriba(int tileYDisparo, int tileXDisparoDerecha,
                                                      int tileXDisparoIzquierda, DisparoJugador disparoJugador) {
        boolean iteratorRemove = false;
        if (tileYDisparo-1 >= 0 && tileXDisparoDerecha < anchoMapaTiles()-1 &&
                mapaTiles[tileXDisparoDerecha][tileYDisparo-1].tipoDeColision ==
                Tile.PASABLE &&
                mapaTiles[tileXDisparoIzquierda][tileYDisparo-1].tipoDeColision ==
                Tile.PASABLE) {
            disparoJugador.y += disparoJugador.velocidadY;
        } else if (tileYDisparo >= 0) {
            int TileDisparoBordeArriba = tileYDisparo*Tile.altura;
            double distanciaY = (disparoJugador.y - disparoJugador.cArriba) -
                    TileDisparoBordeArriba;

            if (distanciaY > 0) {
                double velocidadNecesaria =
                        Utilidades.proximoACero(-distanciaY, disparoJugador.velocidadY);
                disparoJugador.y += velocidadNecesaria;
            } else {
                iteratorRemove = true;
            }
        }
        return iteratorRemove;
    }
}
