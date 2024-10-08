package com.example.loginandroid_29_09_2023.login_user.presenter;

import android.util.Log;

import com.example.loginandroid_29_09_2023.beans.User;
import com.example.loginandroid_29_09_2023.login_user.ContractLoginUser;
import com.example.loginandroid_29_09_2023.login_user.model.LoginUserModel;
//PARECIDO A UN SERVICE ENTRE VISTA Y MODEL
public class LoginUserPresenter implements ContractLoginUser.Presenter, ContractLoginUser.Model.OnLoginUserListener {

    private ContractLoginUser.View view;
    private ContractLoginUser.Model model;

    public LoginUserPresenter(ContractLoginUser.View view){
        this.view = view;
        model = new LoginUserModel(this);
    }
    @Override
    public void login(User user) {
        Log.d("LoginUserPresenter", "Iniciando login para usuario: " + user.getUsername());

        model.loginAPI(user, this);
    }

    @Override
    public void onFinished(User user) {
        Log.d("LoginUserPresenter", "Login exitoso para usuario: " + user.getUsername());

        view.successLogin(user);
    }

    @Override
    public void onFailure(String err) {
        Log.e("LoginUserPresenter", "Error en login: " + err);


    }
}
