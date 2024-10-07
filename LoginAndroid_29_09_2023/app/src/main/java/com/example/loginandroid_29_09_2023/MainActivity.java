package com.example.loginandroid_29_09_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.loginandroid_29_09_2023.lstMov.view.LstMovies;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreen); // Sigue mostrando el Splash Screen al principio
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_m); // Aquí se debe mostrar el login

        // Elimina la redirección automática para que el usuario pueda interactuar con la pantalla de login
    }
}
