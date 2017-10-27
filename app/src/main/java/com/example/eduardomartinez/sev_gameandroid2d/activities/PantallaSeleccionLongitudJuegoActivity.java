package com.example.eduardomartinez.sev_gameandroid2d.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.eduardomartinez.sev_gameandroid2d.GestorNivel;
import com.example.eduardomartinez.sev_gameandroid2d.R;

public class PantallaSeleccionLongitudJuegoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pantalla_seleccion_longitud_juego);
    }

    public void nivelLogitud3(View v){
        GestorNivel.getInstance().longitudJuego = 3;

        Intent actividadJuego = new Intent(PantallaSeleccionLongitudJuegoActivity.this, JuegoActivity.class);
        startActivity(actividadJuego);
    }

    public void nivelLogitud5(View v){
        GestorNivel.getInstance().longitudJuego = 5;

        Intent actividadJuego = new Intent(PantallaSeleccionLongitudJuegoActivity.this, JuegoActivity.class);
        startActivity(actividadJuego);
    }

    public void nivelLogitud7(View v){
        GestorNivel.getInstance().longitudJuego = 7;

        Intent actividadJuego = new Intent(PantallaSeleccionLongitudJuegoActivity.this, JuegoActivity.class);
        startActivity(actividadJuego);
    }
}
