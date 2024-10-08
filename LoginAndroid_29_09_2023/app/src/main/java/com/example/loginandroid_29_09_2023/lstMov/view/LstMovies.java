package com.example.loginandroid_29_09_2023.lstMov.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.adapters.PeliculaAdapter;
import com.example.loginandroid_29_09_2023.lstMov.presenter.LstMoviesPresenter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class LstMovies extends AppCompatActivity
            implements ContractListMovies.View {
    private LstMoviesPresenter lstMoviesPresenter;
    private RecyclerView recyclerView;
    private PeliculaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_movies4);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstMoviesPresenter = new LstMoviesPresenter(this);
        lstMoviesPresenter.lstMovies("");

    }
    @SuppressLint("NonConstantResourceId")

     /*
    @Override


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_opcion1:
                // Manejar clic en Opción 1
                return true;
            case R.id.action_opcion2:
                // Manejar clic en Opción 2
                return true;
            // ... otros casos según sea necesario
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

      */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public void successMovies(ArrayList<Pelicula> lstPelicula) {
        if (lstPelicula == null || lstPelicula.isEmpty()) {
            Log.e("LstMovies", "Lista de películas vacía");
            return;
        }

        // Mostrar una notificación para confirmar la recepción de datos
        Toast.makeText(this, lstPelicula.get(0).getTitulo(), Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerViewPeliculas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PeliculaAdapter(this, lstPelicula);

        adapter.setOnItemClickListener(new PeliculaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula pelicula) {
                Toast.makeText(LstMovies.this, pelicula.getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }



    @Override
    public void failureMovies(String err) {

    }
}