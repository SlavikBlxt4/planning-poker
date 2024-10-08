package com.example.loginandroid_29_09_2023.lstMov.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.presenter.LstMoviesPresenter;
import com.example.loginandroid_29_09_2023.lstMov.data.DataMovies;
import com.example.loginandroid_29_09_2023.utils.ApiService;
import com.example.loginandroid_29_09_2023.utils.RetrofitCliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LstMoviesModel implements ContractListMovies.Model {
    private static final String IP_BASE = "http://10.0.0.2:3000/peliculas";
    private LstMoviesPresenter presenter;
    private Context context;  // Añadimos el context

    public LstMoviesModel(LstMoviesPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;  // Inicializamos el context
    }

    @Override
    public void moviesAPI(String filtro,
                          OnLstMoviesListener respuestaMovies) {
        ApiService apiService = RetrofitCliente.getClient(ApiService.URL).
                create(ApiService.class);

        Call<List<Pelicula>> call = apiService.getDataMovies("MOVIE.LST_PELICULA");
        call.enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Pelicula> peliculas = (ArrayList<Pelicula>) response.body();
                    Log.d("LstMoviesModel", "Películas recibidas: " + peliculas.size());
                    Toast.makeText(context, "Películas recibidas: " + peliculas.size(), Toast.LENGTH_SHORT).show();
                    respuestaMovies.onFinished(peliculas);
                } else {
                    Log.e("LstMoviesModel", "Error en la respuesta de la API: " + response.code());
                    Toast.makeText(context, "Error en la respuesta: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("LstMoviesModel", "Error en la llamada a la API: " + t.getMessage());
                Toast.makeText(context, "Error en la llamada: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}