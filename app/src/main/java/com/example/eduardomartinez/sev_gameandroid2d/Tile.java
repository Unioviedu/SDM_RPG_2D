package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by karolmc on 24/10/2017.
 */

public class Tile {
    public static final int PASABLE = 0;
    public static final int SOLIDO = 1;
    public static final int DESTRUIBLE = 2;

    public static int ancho = 100;
    public static int altura = 100;

    public int tipoDeColision;

    public Drawable imagen;

    public Tile(Context context, int altoMapaTiles, int anchoMapaTiles, char tipoDeTile, int x, int y)
    {
        inicializarTile(context, altoMapaTiles, anchoMapaTiles, tipoDeTile, x ,y);
    }

    private void inicializarTile(Context context, int altoMapaTiles, int anchoMapaTiles, char tipoDeTile, int x, int y) {
        switch (tipoDeTile){
            case '#':
                imagen = CargadorGraficos.cargarDrawable(context,
                        determinarImagenPared(altoMapaTiles, anchoMapaTiles, x, y));
                tipoDeColision = SOLIDO;
                break;
            case 'P':
                imagen = CargadorGraficos.cargarDrawable(context, R.drawable.piedra_1);
                tipoDeColision = SOLIDO;
                break;
            case '.':
                imagen = CargadorGraficos.cargarDrawable(context, R.drawable.habitacion_suelo);
                tipoDeColision = PASABLE;
                break;
            default:
                throw new RuntimeException("Tipo de tile incorrecto");
        }
    }

    private int determinarImagenPared(int altoMapaTiles, int anchoMapaTiles, int x, int y) {
        if(x == 0 && y == 0)
            return R.drawable.habitacion_esquina_izq_arriba;
        else if(x == 0 && y == altoMapaTiles - 1)
            return R.drawable.habitacion_esquina_izq_abajo;
        else if(x == anchoMapaTiles - 1 && y == 0)
            return R.drawable.habitacion_esquina_der_arriba;
        else if(x == anchoMapaTiles - 1 && y == altoMapaTiles - 1)
            return R.drawable.habitacion_esquina_der_abajo;
        else if(x == 0 && y != 0 && y != altoMapaTiles - 1)
            return R.drawable.habitacion_pared_izq;
        else if(x == anchoMapaTiles - 1 && y != 0 && y != altoMapaTiles - 1)
            return R.drawable.habitacion_pared_der;
        else if(x != 0 && x != anchoMapaTiles - 1 && y == 0)
            return R.drawable.habitacion_pared_arriba;
        else if(x != 0 && x != anchoMapaTiles - 1 && y == altoMapaTiles - 1)
            return R.drawable.habitacion_pared_abajo;
        else
            throw new RuntimeException("Pared Incorrecta");
    }
}
