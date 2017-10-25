package com.example.eduardomartinez.sev_gameandroid2d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PantallaSeleccionLongitudJuegoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_pantalla_seleccion_longitud_juego);
    }
}
