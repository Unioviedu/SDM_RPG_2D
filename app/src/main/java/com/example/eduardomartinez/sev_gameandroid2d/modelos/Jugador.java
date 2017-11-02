package com.example.eduardomartinez.sev_gameandroid2d.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.example.eduardomartinez.sev_gameandroid2d.CargadorGraficos;
import com.example.eduardomartinez.sev_gameandroid2d.Habitacion;
import com.example.eduardomartinez.sev_gameandroid2d.R;
import com.example.eduardomartinez.sev_gameandroid2d.Tile;
import com.example.eduardomartinez.sev_gameandroid2d.Utilidades;
import com.example.eduardomartinez.sev_gameandroid2d.graficos.Sprite;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.disparos.DisparoJugador;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.disparos.DisparoJugadorNormal;
import com.example.eduardomartinez.sev_gameandroid2d.modelos.interaccionables.Interaccionable;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by eduardomartinez on 24/10/17.
 */

public class Jugador extends Modelo {
    public static final String PARADO_ARRIBA = "parado_arriba";
    public static final String PARADO_ABAJO = "parado_abajo";
    public static final String PARADO_DERECHA = "parado_derecha";
    public static final String PARADO_IZQUIERDA = "parado_izquierda";

    public static final String CAMINANDO_ARRIBA = "caminando_arriba";
    public static final String CAMINANDO_ABAJO = "caminando_abajo";
    public static final String CAMINANDO_DERECHA = "caminando_derecha";
    public static final String CAMINANDO_IZQUIERDA = "caminando_izquierda";

    public static final String DISPARANDO_ARRIBA = "disparando_arriba";
    public static final String DISPARANDO_ABAJO = "disparando_abajo";
    public static final String DISPARANDO_DERECHA = "disparando_derecha";
    public static final String DISPARANDO_IZQUIERDA = "disparando_izquierda";

    public static final String GOLPEADO_ARRIBA = "golpeado_arriba";
    public static final String GOLPEADO_ABAJO = "golpeado_abajo";
    public static final String GOLPEADO_DERECHA = "golpeado_derecha";
    public static final String GOLPEADO_IZQUIERDA = "golpeado_izquierda";

    private HashMap<String, Sprite> sprites = new HashMap<>();
    private Sprite spriteActual;

    private double xInicial;
    private double yInicial;
    public double velocidadX;
    public double velocidadY;

    public int orientacion;
    public double orientacionDisparoX;
    public double orientacionDisparoY;

    public static final int DERECHA = 1;
    public static final int ARRIBA = 2;
    public static final int IZQUIERDA = -1;
    public static final int ABAJO = -2;

    public static final int DERECHA_ARRIBA = 3;
    public static final int DERECHA_ABAJO = -3;
    public static final int IZQUIERDA_ARRIBA = 4;
    public static final int IZQUIERDA_ABAJO = -4;

    public DisparoJugador disparoJugador;
    private boolean disparando;
    private boolean golpeado = false;

    float sensibilidadPad = 20;
    public float velocidad = 12;

    public int vidasTotales;
    public int vidasActuales;
    public double msInmunidad = 0;

    public Jugador(Context context, double xInicial, double yInicial) {
        super(context, xInicial, yInicial, 59*2, 50*2);

        this.xInicial = xInicial;
        this.yInicial = yInicial;

        this.x =  this.xInicial;
        this.y =  this.yInicial;

        vidasTotales = vidasActuales = 3;

        orientacion = ABAJO;

        inicializar();

        disparoJugador = new DisparoJugadorNormal(context, x, y, orientacionDisparoX, orientacionDisparoY);
    }

    public void inicializar() {
        crearSprite(R.drawable.protagonista_animacion_parado_arriba,
                PARADO_ARRIBA, 2, true);
        Sprite paradoAbajo = crearSprite(R.drawable.protagonista_animacion_parado_abajo,
                PARADO_ABAJO, 2, true);
        crearSprite(R.drawable.protagonista_animacion_parado_derecha,
                PARADO_DERECHA, 2, true);
        crearSprite(R.drawable.protagonista_animacion_parado_izquierda,
                PARADO_IZQUIERDA, 2, true);

        crearSprite(R.drawable.protagonista_animacion_caminando_arriba,
                CAMINANDO_ARRIBA, 6, true);
        crearSprite(R.drawable.protagonista_animacion_caminando_abajo,
                CAMINANDO_ABAJO, 6, true);
        crearSprite(R.drawable.protagonista_animacion_caminando_derecha,
                CAMINANDO_DERECHA, 6, true);
        crearSprite(R.drawable.protagonista_animacion_caminando_izquierda,
                CAMINANDO_IZQUIERDA, 6, true);

        crearSprite(R.drawable.protagonista_animacion_disparando_arriba,
                DISPARANDO_ARRIBA, 25, 2, false);
        crearSprite(R.drawable.protagonista_animacion_disparando_abajo,
                DISPARANDO_ABAJO, 25, 2, false);
        crearSprite(R.drawable.protagonista_animacion_disparando_derecha,
                DISPARANDO_DERECHA, 25, 2, false);
        crearSprite(R.drawable.protagonista_animacion_disparando_izquierda,
                DISPARANDO_IZQUIERDA, 25, 2, false);

        crearSprite(R.drawable.protagonista_animacion_golpeado_arriba,
                GOLPEADO_ARRIBA, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_abajo,
                GOLPEADO_ABAJO, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_derecha,
                GOLPEADO_DERECHA, 2, false);
        crearSprite(R.drawable.protagonista_animacion_golpeado_izquierda,
                GOLPEADO_IZQUIERDA, 2, false);

        spriteActual = paradoAbajo;
    }

    public void dibujar(Canvas canvas){
        spriteActual.dibujarSprite(canvas, (int) x - Habitacion.scrollEjeX, (int) y - Habitacion.scrollEjeY,msInmunidad > 0);
    }

    public DisparoJugador disparar() {
        return disparoJugador.disparar(x, y, orientacionDisparoX, orientacionDisparoY);
    }

    public void actualizar (long tiempo) {
        if(msInmunidad > 0)
            msInmunidad -= tiempo;

        boolean finSprite = spriteActual.actualizar(tiempo);

        if (golpeado && finSprite)
            golpeado = false;

        if(disparando && finSprite)
            disparando = false;

        comprobarCaminando();

        if (disparando)
            comprobarDisparando();

        if (golpeado)
            comprobarGolpeado();

    }

    public int golpeado(){
        if (msInmunidad <= 0) {
            if (vidasActuales > 0) {
                vidasActuales--;
                msInmunidad = 3000;
                golpeado = true;
                // Reiniciar animaciones que no son bucle
                sprites.get(GOLPEADO_IZQUIERDA).setFrameActual(0);
                sprites.get(GOLPEADO_DERECHA).setFrameActual(0);
                sprites.get(GOLPEADO_ARRIBA).setFrameActual(0);
                sprites.get(GOLPEADO_ABAJO).setFrameActual(0);
            }
        }
        return vidasActuales;
    }

    private void comprobarCaminando() {
        if (velocidadX > 0)
            spriteActual = sprites.get(CAMINANDO_DERECHA);

        if (velocidadX < 0 )
            spriteActual = sprites.get(CAMINANDO_IZQUIERDA);

        if (velocidadY > 0)
            spriteActual = sprites.get(CAMINANDO_ABAJO);

        if (velocidadY < 0 )
            spriteActual = sprites.get(CAMINANDO_ARRIBA);

        if (velocidadX == 0 && velocidadY == 0)
            if (orientacion == DERECHA)
                spriteActual = sprites.get(PARADO_DERECHA);
            else if (orientacion == IZQUIERDA)
                spriteActual = sprites.get(PARADO_IZQUIERDA);
            else if (orientacion == ARRIBA)
                spriteActual = sprites.get(PARADO_ARRIBA);
            else if (orientacion == ABAJO)
                spriteActual = sprites.get(PARADO_ABAJO);
    }

    private void comprobarDisparando() {
        int orientacion = Utilidades.orientacion(orientacionDisparoX, orientacionDisparoY);

        if (orientacion == DERECHA)
            spriteActual = sprites.get(DISPARANDO_DERECHA);
        else if (orientacion == IZQUIERDA)
            spriteActual = sprites.get(DISPARANDO_IZQUIERDA);
        else if (orientacion == ARRIBA)
            spriteActual = sprites.get(DISPARANDO_ARRIBA);
        else if (orientacion == ABAJO)
            spriteActual = sprites.get(DISPARANDO_ABAJO);
    }

    private void comprobarGolpeado() {
        if (orientacion == DERECHA)
            spriteActual = sprites.get(GOLPEADO_DERECHA);
        else if (orientacion == IZQUIERDA)
            spriteActual = sprites.get(GOLPEADO_IZQUIERDA);
        else if (orientacion == ARRIBA)
            spriteActual = sprites.get(GOLPEADO_ARRIBA);
        else if (orientacion == ABAJO)
            spriteActual = sprites.get(GOLPEADO_ABAJO);
    }

    private Sprite crearSprite(int animacion, String nombre, int frames, boolean bucle) {
        Sprite temp = new Sprite (CargadorGraficos.cargarDrawable(context, animacion),
                ancho, altura,
                frames*4, frames, bucle);
        sprites.put(nombre, temp);

        return temp;
    }

    private Sprite crearSprite(int animacion, String nombre, int fps, int frames, boolean bucle) {
        Sprite temp = new Sprite (CargadorGraficos.cargarDrawable(context, animacion),
                ancho, altura,
                fps, frames, bucle);
        sprites.put(nombre, temp);

        return temp;
    }

    public void procesarOrdenes(float orientacionMoverX, float orientacionMoverY,
                                boolean disparar, double orientacionPadDispararX,
                                double orientacionPadDispararY) {
        if (disparar) {
            disparando = true;

            this.orientacionDisparoX = orientacionPadDispararX;
            this.orientacionDisparoY = orientacionPadDispararY;

            sprites.get(DISPARANDO_DERECHA).setFrameActual(0);
            sprites.get(DISPARANDO_IZQUIERDA).setFrameActual(0);
            sprites.get(DISPARANDO_ARRIBA).setFrameActual(0);
            sprites.get(DISPARANDO_ABAJO).setFrameActual(0);
        }



        if (orientacionMoverX > sensibilidadPad) {
            velocidadX = -velocidad;
            orientacion = IZQUIERDA;
        } else if (orientacionMoverX < -sensibilidadPad){
            velocidadX = velocidad;
            orientacion = DERECHA;
        } else {
            velocidadX = 0;
        }

        if (orientacionMoverY > sensibilidadPad) {
            velocidadY = -velocidad;
            orientacion = ARRIBA;
        } else if (orientacionMoverY < -sensibilidadPad){
            velocidadY = velocidad;
            orientacion = ABAJO;
        } else {
            velocidadY = 0;
        }
    }

    public void aplicarReglasMovimiento(Habitacion habitacion) {
        int tileXJugadorIzquierda
                = (int) (x - (ancho / 2 - 1)) / Tile.ancho;
        int tileXJugadorDerecha
                = (int) (x + (ancho / 2 - 1)) / Tile.ancho;

        int tileYJugadorInferior
                = (int) (y + (altura / 2 - 1)) / Tile.altura;
        int tileYJugadorCentro
                = (int) y / Tile.altura;
        int tileYJugadorSuperior
                = (int) (y - (altura / 2 - 1)) / Tile.altura;

        if (velocidadX > 0)
            aplicarMovimientoDerecha(habitacion, tileXJugadorDerecha,
                    tileYJugadorInferior, tileYJugadorCentro, tileYJugadorSuperior);
        if (velocidadY > 0)
            aplicarMovimientoAbajo(habitacion.mapaTiles[tileXJugadorIzquierda], habitacion.mapaTiles[tileXJugadorDerecha],
                    tileYJugadorInferior);
        if (velocidadX <= 0)
            aplicarMovimientoIzquierda(habitacion, tileXJugadorIzquierda,
                    tileYJugadorInferior, tileYJugadorCentro, tileYJugadorSuperior);
        if (velocidadY <= 0)
            aplicarMovimientoArriba(habitacion, tileXJugadorDerecha,
                    tileXJugadorIzquierda, tileYJugadorInferior, tileYJugadorSuperior);

        comprobarColisiones(habitacion);
    }

    private void comprobarColisiones(Habitacion habitacion) {
        for(Iterator<Interaccionable> iterator = habitacion.interaccionables.iterator(); iterator.hasNext();) {
            Interaccionable item = iterator.next();
                if (item.colisiona(this)) {
                    if (item.activarItem(habitacion)) {
                        iterator.remove();
                    }
                }
            }
    }

    private void aplicarMovimientoIzquierda(Habitacion habitacion, int tileXJugadorIzquierda,
                                            int tileYJugadorInferior, int tileYJugadorCentro,
                                            int tileYJugadorSuperior) {
        // Tengo un tile detrás y es PASABLE
        // El tile de detras está dentro del Nivel
        if (habitacion.mapaTiles[tileXJugadorIzquierda - 1][tileYJugadorInferior].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda - 1][tileYJugadorCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda - 1][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorInferior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            x += velocidadX;

            // No tengo un tile PASABLE detrás
            // o es el INICIO del nivel o es uno SOLIDO
        } else if (tileXJugadorIzquierda >= 0 && tileYJugadorInferior <= habitacion.altoMapaTiles() - 1 &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorInferior].tipoDeColision
                        == Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorCentro].tipoDeColision
                        == Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior].tipoDeColision
                        == Tile.PASABLE) {

            // Si en el propio tile del jugador queda espacio para
            // avanzar más, avanzo
            int TileJugadorBordeIzquierdo = tileXJugadorIzquierda * Tile.ancho;
            double distanciaX = (x - cIzquierda) - TileJugadorBordeIzquierdo;

            if (distanciaX > 0) {
                double velocidadNecesaria = Utilidades.proximoACero(-distanciaX, velocidadX);
                x += velocidadNecesaria;
            } else {
                x = TileJugadorBordeIzquierdo + cDerecha;
            }
        }
    }

    private void aplicarMovimientoAbajo(Tile[] mapaTile, Tile[] mapaTile1, int tileYJugadorInferior) {
        // Tengo un tile abajo y es PASABLE
        // El tile de delante está dentro del Nivel
        if (mapaTile1[tileYJugadorInferior + 1].tipoDeColision ==
                Tile.PASABLE &&
                mapaTile[tileYJugadorInferior + 1].tipoDeColision ==
                        Tile.PASABLE &&
                mapaTile1[tileYJugadorInferior].tipoDeColision ==
                        Tile.PASABLE &&
                mapaTile[tileYJugadorInferior].tipoDeColision ==
                        Tile.PASABLE) {

            y += velocidadY;

            // No tengo un tile PASABLE delante
            // o es el FINAL del nivel o es uno SOLIDO
        } else if (mapaTile1[tileYJugadorInferior].tipoDeColision ==
                Tile.PASABLE &&
                mapaTile[tileYJugadorInferior].tipoDeColision ==
                        Tile.PASABLE) {

            // Si en el propio tile del jugador queda espacio para
            // avanzar más, avanzo
            int tileJugadorBordeInferior = tileYJugadorInferior * Tile.altura + Tile.altura;
            double distanciaY = tileJugadorBordeInferior - (y + cAbajo);

            if (distanciaY > 0) {
                double velocidadNecesaria = Math.min(distanciaY, velocidadY);
                y += velocidadNecesaria;
            } else {
                // Opcional, corregir posición
                y = tileJugadorBordeInferior - cArriba;
            }
        }
    }

    private void aplicarMovimientoDerecha(Habitacion habitacion, int tileXJugadorDerecha, int tileYJugadorInferior, int tileYJugadorCentro, int tileYJugadorSuperior) {
        // Tengo un tile delante y es PASABLE
        // El tile de delante está dentro del Nivel
        if (habitacion.mapaTiles[tileXJugadorDerecha + 1][tileYJugadorInferior].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha + 1][tileYJugadorCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha + 1][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorInferior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            x += velocidadX;

            // No tengo un tile PASABLE delante
            // o es el FINAL del nivel o es uno SOLIDO
        } else if (habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorInferior].tipoDeColision ==
                Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorCentro].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            // Si en el propio tile del jugador queda espacio para
            // avanzar más, avanzo
            int TileJugadorBordeDerecho = tileXJugadorDerecha * Tile.ancho + Tile.ancho;
            double distanciaX = TileJugadorBordeDerecho - (x + cDerecha);

            if (distanciaX > 0) {
                double velocidadNecesaria = Math.min(distanciaX, velocidadX);
                x += velocidadNecesaria;
            } else {
                // Opcional, corregir posición
                x = TileJugadorBordeDerecho - cIzquierda;
            }
        }
    }

    private void aplicarMovimientoArriba(Habitacion habitacion, int tileXJugadorDerecha, int tileXJugadorIzquierda, int tileYJugadorInferior, int tileYJugadorSuperior) {
        // Tengo un tile arriba y es PASABLE
        // El tile de arriba está dentro del Nivel
        if (habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior - 1].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior - 1].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            y += velocidadY;

            // No tengo un tile PASABLE detrás
            // o es el INICIO del nivel o es uno SOLIDO
        } else if (tileXJugadorIzquierda >= 0 && tileYJugadorSuperior <= habitacion.altoMapaTiles() - 1 &&
                habitacion.mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE &&
                habitacion.mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior].tipoDeColision ==
                        Tile.PASABLE) {

            // Si en el propio tile del jugador queda espacio para
            // avanzar más, avanzo
            int tileJugadorBordeSuperior = tileYJugadorSuperior * Tile.altura;
            double distanciaY = (y - cArriba) - tileJugadorBordeSuperior;

            if (distanciaY > 0) {
                double velocidadNecesaria = Utilidades.proximoACero(-distanciaY, velocidadY);
                y += velocidadNecesaria;
            } else {
                // Opcional, corregir posición
                y = tileJugadorBordeSuperior + cAbajo;
            }
        }
    }
}
