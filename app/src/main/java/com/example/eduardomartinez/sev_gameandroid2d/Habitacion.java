package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.eduardomartinez.sev_gameandroid2d.modelos.DisparoJugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.DisparoEnemigo;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.Enemigo;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.EnemigoDisparoDirecciones;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.enemigos.EnemigoRebota;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables.DisparoRapido;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables.Interaccionable;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.Jugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables.Pinchos;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables.VidaExtra;

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

    public Jugador jugador;
    private List<Enemigo> enemigos;
    private List<DisparoJugador> disparosJugador;
    private List<DisparoEnemigo> disparosEnemigo;

    public List<Interaccionable> interaccionables;

    public boolean botonDispararPulsado;

    public boolean nivelPausado;

    public float orientacionPadMoverX;
    public float orientacionPadMoverY;
    public double orientacionPadDispararX;
    public double orientacionPadDispararY;
    public static int scrollEjeX;
    public static int scrollEjeY;

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
        disparosJugador = new LinkedList<>();
        disparosEnemigo = new LinkedList<>();
        interaccionables = new LinkedList<>();
        enemigos = new LinkedList<>();
        inicializarMapaTiles();
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
                mapaTiles[x][y] = inicializarTile(context, tipoDeTile, x, y);
            }
        }
    }

    private Tile inicializarTile(Context context, char tipoDeTile, int x, int y) {
        switch (tipoDeTile){
            case '#':
                return new Tile(context, Tile.SOLIDO, Tile.determinarImagenPared(altoMapaTiles(), anchoMapaTiles(), x, y));
            case 'P':
                return new Tile(context, Tile.SOLIDO, R.drawable.piedra_1);
            case 'J':
                jugador = new Jugador(context, x * Tile.ancho + Tile.ancho/2, y * Tile.altura + Tile.altura/2);
                return new Tile(context, Tile.PASABLE, R.drawable.habitacion_suelo);
            case 'T':
                interaccionables.add(new Pinchos(context, x * Tile.ancho + Tile.ancho/2, y * Tile.altura + Tile.altura/2));
                return new Tile(context, Tile.PASABLE, R.drawable.pinchos);
            case '.':
                return new Tile(context, Tile.PASABLE, R.drawable.habitacion_suelo);
            case 'V':
                interaccionables.add(new VidaExtra(context, x * Tile.ancho + Tile.ancho/2, y * Tile.altura + Tile.altura/2));
                return new Tile(context, Tile.PASABLE, R.drawable.habitacion_suelo);
            case 'R':
                interaccionables.add(new DisparoRapido(context, x * Tile.ancho + Tile.ancho/2, y * Tile.altura + Tile.altura/2));
                return new Tile(context, Tile.PASABLE, R.drawable.habitacion_suelo);
            case '1':
                enemigos.add(new EnemigoRebota(context, x * Tile.ancho + Tile.ancho/2, y * Tile.altura + Tile.altura/2));
                return new Tile(context, Tile.PASABLE, R.drawable.habitacion_suelo);
            case '2':
                enemigos.add(new EnemigoDisparoDirecciones(context, x * Tile.ancho + Tile.ancho/2, y * Tile.altura + Tile.altura/2));
                return new Tile(context, Tile.PASABLE, R.drawable.habitacion_suelo);
            default:
                throw new RuntimeException("Tipo de tile incorrecto");
        }
    }

    private void aplicarReglasMoviemiento() {
        if(inicializado) {
            jugador.aplicarReglasMovimiento(this);

            for(Enemigo enemigo: enemigos) {
                enemigo.aplicarReglasMovimiento(this);
                if(enemigo.colisiona(jugador))
                    if(jugador.golpeado() <= 0)
                        nivelPausado = true;
            }
        }
    }

    public void dibujar (Canvas canvas) {
        if(inicializado) {

            dibujarTiles(canvas);
            for(Interaccionable interaccionable: interaccionables)
                interaccionable.dibujar(canvas);

            for (DisparoJugador disparoJugador: disparosJugador)
                disparoJugador.dibujar(canvas);

            for(DisparoEnemigo disparoEnemigo: disparosEnemigo)
                disparoEnemigo.dibujar(canvas);

            jugador.dibujar(canvas);

            for (Enemigo enemigo: enemigos)
                enemigo.dibujar(canvas);

            if(nivelPausado && jugador.vidasActuales == 0){
                Drawable hasPerdido = CargadorGraficos.cargarDrawable(context, R.drawable.pantalla_has_perdido);
                hasPerdido.setBounds((int)(GameView.pantallaAncho/2 - 480),
                        (int)(GameView.pantallaAlto/2 - 320),
                        (int)(GameView.pantallaAncho/2 + 480),
                        (int)(GameView.pantallaAlto/2 + 320));
                hasPerdido.draw(canvas);
            }
        }
    }

    private void dibujarTiles(Canvas canvas){

        int tileXJugador = (int) jugador.x / Tile.ancho;
        int tileYJugador = (int) jugador.y / Tile.altura;

        int izquierda = (int) (tileXJugador - tilesEnDistanciaX(jugador.x - scrollEjeX));
        izquierda = Math.max(0,izquierda);

        if ( jugador.x  < anchoMapaTiles()* Tile.ancho - GameView.pantallaAncho*0.3 )
            if( jugador.x - scrollEjeX > GameView.pantallaAncho * 0.7 ){
                scrollEjeX = (int) ((jugador .x ) - GameView.pantallaAncho* 0.7);
            }
        if ( jugador.y  < altoMapaTiles()* Tile.altura - GameView.pantallaAlto*0.3 )
            if( jugador.y - scrollEjeY > GameView.pantallaAlto * 0.7 )
                scrollEjeY = (int) ((jugador .y ) - GameView.pantallaAlto* 0.7);

        if ( jugador.x  > GameView.pantallaAncho*0.3 )
            if( jugador.x - scrollEjeX < GameView.pantallaAncho *0.3 ){
                scrollEjeX = (int)(jugador .x - GameView.pantallaAncho*0.3);
            }

        if ( jugador.y  > GameView.pantallaAlto*0.3 )
            if (jugador.y - scrollEjeY < GameView.pantallaAlto * 0.3)
                scrollEjeY = (int) (jugador.y - GameView.pantallaAlto * 0.3);

        int derecha = izquierda +
                GameView.pantallaAncho / Tile.ancho + 1;
        // el ultimo tile visible, no puede superar el tamaño del mapa
        derecha = Math.min(derecha, anchoMapaTiles() - 1);


        for (int y = 0; y < altoMapaTiles() ; ++y) {
            for (int x = izquierda; x <= derecha; ++x) {
                if (mapaTiles[x][y].imagen != null) {
                    // Calcular la posición en pantalla correspondiente
                    // izquierda, arriba, derecha , abajo
                    mapaTiles[x][y].imagen.setBounds(
                            (x  * Tile.ancho) - scrollEjeX,
                            (y * Tile.altura) - scrollEjeY,
                            (x * Tile.ancho) + Tile.ancho - scrollEjeX,
                            (y * Tile.altura) + Tile.altura - scrollEjeY);

                    mapaTiles[x][y].imagen.draw(canvas);
                }
            }
        }
    }

    private float tilesEnDistanciaX(double distanciaX){
        return (float) distanciaX/Tile.ancho;
    }

    public void actualizar (long tiempo) {
        if (inicializado) {

            jugador.actualizar(tiempo);

            jugador.procesarOrdenes(orientacionPadMoverX, orientacionPadMoverY, botonDispararPulsado, orientacionPadDispararX, orientacionPadDispararY);

            for (DisparoJugador disparoJugador : disparosJugador)
                disparoJugador.actualizar(tiempo);

            if (botonDispararPulsado) {
                disparosJugador.add(jugador.disparar(context, orientacionPadDispararX, orientacionPadDispararY));
                botonDispararPulsado = false;
            }

            long tiempoDisparo = System.currentTimeMillis();

            for (Enemigo enemigo: enemigos) {
                DisparoEnemigo disparo = enemigo.disparar(context, jugador.x, jugador.y, tiempoDisparo);

                if (disparo != null)
                    disparosEnemigo.add(disparo);
            }

            /*for (DisparoEnemigo disparoEnemigo : disparosEnemigo) {
                //disparoEnemigo.actualizar(tiempo);
            }*/

            /*for (Enemigo enemigo: enemigos)
                enemigo.actualizar(tiempo);*/

            aplicarReglasDisparoEnemigo();
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

    private void aplicarReglasDisparoEnemigo() {
        for (Iterator<DisparoEnemigo> iterator = disparosEnemigo.iterator();
             iterator.hasNext(); ) {

            DisparoEnemigo disparoEnemigo = iterator.next();

            int tileXDisparo = (int) disparoEnemigo.x / Tile.ancho;
            int tileYDisparoInferior =
                    (int) (disparoEnemigo.y + disparoEnemigo.cAbajo) / Tile.altura;
            int tileYDisparoSuperior =
                    (int) (disparoEnemigo.y - disparoEnemigo.cArriba) / Tile.altura;

            if (disparoEnemigo.velocidadX > 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorDerecha(tileXDisparo, tileYDisparoInferior,
                        tileYDisparoSuperior, disparoEnemigo);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }

            if (disparoEnemigo.velocidadX < 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorIzquierda(tileXDisparo, tileYDisparoInferior,
                        tileYDisparoSuperior, disparoEnemigo);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }


            int tileYDisparo = (int) disparoEnemigo.y / Tile.altura;
            int tileXDisparoDerecha =
                    (int) (disparoEnemigo.x + disparoEnemigo.cDerecha) / Tile.ancho;
            int tileXDisparoIzquierda =
                    (int) (disparoEnemigo.x - disparoEnemigo.cIzquierda) / Tile.ancho;

            if (disparoEnemigo.velocidadY > 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorAbajo(tileYDisparo, tileXDisparoDerecha,
                        tileXDisparoIzquierda, disparoEnemigo);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }

            if (disparoEnemigo.velocidadY < 0) {
                boolean iteratorRemove = aplicarReglasDisparoJugadorArriba(tileYDisparo, tileXDisparoDerecha,
                        tileXDisparoIzquierda, disparoEnemigo);
                if (iteratorRemove) {
                    iterator.remove();
                    continue;
                }
            }
        }
    }

    private boolean aplicarReglasDisparoJugadorDerecha(int tileXDisparo, int tileYDisparoInferior,
                                                       int tileYDisparoSuperior, DisparoEnemigo disparoJugador) {
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
                                                         int tileYDisparoSuperior, DisparoEnemigo disparoJugador) {
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
                                                     int tileXDisparoIzquierda, DisparoEnemigo disparoJugador) {
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
                                                      int tileXDisparoIzquierda, DisparoEnemigo disparoJugador) {
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
