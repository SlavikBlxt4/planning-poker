package com.example.loginandroid_29_09_2023.login_user.model;

import android.util.Log;

import com.example.loginandroid_29_09_2023.beans.User;
import com.example.loginandroid_29_09_2023.login_user.ContractLoginUser;
import com.example.loginandroid_29_09_2023.login_user.model.data.MyData;
import com.example.loginandroid_29_09_2023.login_user.presenter.LoginUserPresenter;
import com.example.loginandroid_29_09_2023.utils.ApiService;
import com.example.loginandroid_29_09_2023.utils.RetrofitCliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//DAO/REPOSITORY
public class LoginUserModel implements ContractLoginUser.Model {
    private static final String IP_BASE = "10.0.2.2:3000";
    private LoginUserPresenter presenter;

    public LoginUserModel(LoginUserPresenter presenter) {
        this.presenter = presenter;
    }
//HAY QUE MODIFICAR
    @Override
    public void loginAPI(User user, final OnLoginUserListener onLoginUserListener) {
        // Crear una instancia de ApiService
        ApiService apiService = RetrofitCliente.getClient("http://" + IP_BASE).create(ApiService.class);

        // Realizar la solicitud al Servlet con los parámetros ACTION, EMAIL, y PASSWORD
        Call<List<User>> call = apiService.getDataUser("USER.LOGIN", user.getUsername(), user.getToken());

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    // Procesar la respuesta aquí
                    List<User> lstUsers = response.body();

                    if (lstUsers != null && !lstUsers.isEmpty()) {
                        // Llamar al listener con el primer usuario
                        onLoginUserListener.onFinished(lstUsers.get(0));
                    } else {
                        // Lista vacía o nula
                        Log.e("LoginUserModel", "Lista de usuarios vacía o nula");
                        onLoginUserListener.onFailure("Lista de usuarios vacía o nula");
                    }
                } else {
                    // Manejar una respuesta no exitosa
                    Log.e("Response Error", "Código de estado HTTP: " + response.code());
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Response Error", "Cuerpo de error: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    onLoginUserListener.onFailure("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Manejar errores de red o del servidor
                Log.e("Response Error", "Cuerpo de error: " + t.getMessage());
                onLoginUserListener.onFailure(t.getMessage());
            }
        });
    }
}

