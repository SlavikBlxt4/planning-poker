package com.example.loginandroid_29_09_2023.lstMov.presenter;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.lstMov.ContractListMovies;
import com.example.loginandroid_29_09_2023.lstMov.model.LstMoviesModel;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LstMoviesPresenter implements ContractListMovies.Presenter,
        ContractListMovies.Model.OnLstMoviesListener{

    private ContractListMovies.View vista;
    private LstMoviesModel lstMoviesModel;
    private Context context;

    public LstMoviesPresenter(ContractListMovies.View vista, Context context){
        this.vista = vista;
        this.context = context;
        lstMoviesModel = new LstMoviesModel(this, context);  // Pasar context al model también
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void lstMovies(String filtro) {
        lstMoviesModel.moviesAPI("", this);
    }

    @Override
    public void onFinished(ArrayList<Pelicula> lstPelicula) {
        vista.successMovies(lstPelicula);
    }

    @Override
    public void onFailure(String err) {
        Log.e("LstMoviesPresenter", "Error: " + err);
        Toast.makeText(context, "Error al cargar películas: " + err, Toast.LENGTH_SHORT).show();
    }
}
