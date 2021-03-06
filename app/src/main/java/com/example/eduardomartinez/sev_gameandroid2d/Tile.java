package com.example.eduardomartinez.sev_gameandroid2d;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.eduardomartinez.sev_gameandroid2d.gestores.CargadorGraficos;

/**
 * Created by karolmc on 24/10/2017.
 */

public class Tile {
    public static final int PASABLE = 0;
    public static final int SOLIDO = 1;
    public static final int DESTRUIBLE = 2;
    public static final int PINCHOS = 3;

    public static int ancho = GameView.pantallaAncho/10;
    public static int altura = GameView.pantallaAncho/10;

    public int tipoDeColision;

    public Drawable imagen;

    public Tile(Context context, int tipoDeColision, int imagen) {
        this.imagen = CargadorGraficos.cargarDrawable(context, imagen);
        this.tipoDeColision = tipoDeColision;
    }

    public static int determinarImagenPared(boolean habitacionJefe, int altoMapaTiles, int anchoMapaTiles, int x, int y) {
        if(habitacionJefe){
            if (x == 0 && y == 0)
            return R.drawable.habitacion_boss_esquina_izq_arriba;
            else if (x == 0 && y == altoMapaTiles - 1)
                return R.drawable.habitacion_boss_esquina_izq_abajo;
            else if (x == anchoMapaTiles - 1 && y == 0)
                return R.drawable.habitacion_boss_esquina_der_arriba;
            else if (x == anchoMapaTiles - 1 && y == altoMapaTiles - 1)
                return R.drawable.habitacion_boss_esquina_der_abajo;
            else if (x == 0 && y != 0 && y != altoMapaTiles - 1)
                return R.drawable.habitacion_boss_pared_izquierda;
            else if (x == anchoMapaTiles - 1 && y != 0 && y != altoMapaTiles - 1)
                return R.drawable.habitacion_boss_pared_derecha;
            else if (x != 0 && x != anchoMapaTiles - 1 && y == 0)
                return R.drawable.habitacion_boss_pared_arriba;
            else if (x != 0 && x != anchoMapaTiles - 1 && y == altoMapaTiles - 1)
                return R.drawable.habitacion_boss_pared_abajo;
            else
                throw new RuntimeException("Pared Incorrecta");
        } else {
            if (x == 0 && y == 0)
                return R.drawable.habitacion_esquina_izq_arriba;
            else if (x == 0 && y == altoMapaTiles - 1)
                return R.drawable.habitacion_esquina_izq_abajo;
            else if (x == anchoMapaTiles - 1 && y == 0)
                return R.drawable.habitacion_esquina_der_arriba;
            else if (x == anchoMapaTiles - 1 && y == altoMapaTiles - 1)
                return R.drawable.habitacion_esquina_der_abajo;
            else if (x == 0 && y != 0 && y != altoMapaTiles - 1)
                return R.drawable.habitacion_pared_izq;
            else if (x == anchoMapaTiles - 1 && y != 0 && y != altoMapaTiles - 1)
                return R.drawable.habitacion_pared_der;
            else if (x != 0 && x != anchoMapaTiles - 1 && y == 0)
                return R.drawable.habitacion_pared_arriba;
            else if (x != 0 && x != anchoMapaTiles - 1 && y == altoMapaTiles - 1)
                return R.drawable.habitacion_pared_abajo;
            else
                throw new RuntimeException("Pared Incorrecta");
        }
    }

    public static int determinarImagenSuelo(boolean habitacionJefe){
        return habitacionJefe? R.drawable.habitacion_boss_suelo: R.drawable.habitacion_suelo;
    }

    public static int determinarImagenPiedra(boolean habitacionJefe){
        return habitacionJefe? R.drawable.piedra_boss: R.drawable.piedra_habitacion;
    }
}
