package com.example.eduardomartinez.sev_gameandroid2d;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class PantallaInicioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pantalla_inicio);
    }

    public void iniciarJuego(View v){
        Intent longitudJuegoActivity = new Intent(PantallaInicioActivity.this, PantallaSeleccionLongitudJuegoActivity.class);

        startActivity(longitudJuegoActivity);
    }
}
