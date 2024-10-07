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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserModel implements ContractLoginUser.Model {
    private static final String IP_BASE = "10.0.2.2:3000";
    private LoginUserPresenter presenter;

    public LoginUserModel(LoginUserPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void loginAPI(User user, final OnLoginUserListener onLoginUserListener) {
        Log.d("LoginUserModel", "Llamando a la API para login con usuario: " + user.getUsername());

        ApiService apiService = RetrofitCliente.getClient("http://" + IP_BASE + "/").create(ApiService.class);

        Call<MyData> call = apiService.getDataUser("usuarios/login");
        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                if (response.isSuccessful()) {
                    MyData myData = response.body();
                    Log.d("LoginUserModel", "Respuesta exitosa de la API");

                    ArrayList<User> lstUsers = myData.getLstUsers();
                    if (!lstUsers.isEmpty()) {
                        Log.d("LoginUserModel", "Usuario recibido: " + lstUsers.get(0).getUsername());
                        onLoginUserListener.onFinished(lstUsers.get(0));
                    } else {
                        Log.e("LoginUserModel", "Lista de usuarios vacía");
                        onLoginUserListener.onFailure("Lista de usuarios vacía");
                    }
                } else {
                    Log.e("LoginUserModel", "Código de estado HTTP: " + response.code());
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("LoginUserModel", "Cuerpo de error: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    onLoginUserListener.onFailure("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                Log.e("LoginUserModel", "Error en la llamada a la API: " + t.getMessage());
                onLoginUserListener.onFailure(t.getMessage());
            }
        });
    }
}
