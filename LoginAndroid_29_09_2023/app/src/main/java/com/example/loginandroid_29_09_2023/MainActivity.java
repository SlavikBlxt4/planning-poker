package com.example.loginandroid_29_09_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.loginandroid_29_09_2023.login_user.view.LoginUserM; // Importamos la actividad de login

public class MainActivity extends AppCompatActivity{
    private static final long SPLASH_DISPLAY_LENGTH = 3000; // Duración del splash screen (3 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreen); // Establecer el tema del Splash Screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Establecer el layout del Splash Screen

        // Después de la duración del Splash, redirigimos a LoginUserM
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Crear un Intent para iniciar la actividad de LoginUserM NO CARGABA PORQUE ESTABA LISTA PELIS
                Intent mainIntent = new Intent(MainActivity.this, LoginUserM.class);
                startActivity(mainIntent);
                MainActivity.this.finish(); // Finalizar el Splash Screen para no regresar a él
            }
        }, SPLASH_DISPLAY_LENGTH); // Retraso de 3 segundos antes de redirigir
    }
}
