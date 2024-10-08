package com.example.loginandroid_29_09_2023.beans;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")  // Mapea el campo "email" del JSON a la variable "username"

    private String username;
    @SerializedName("contraseña")  // Mapea el campo "email" del JSON a la variable "username"

    private String token; // Puedes agregar otros campos según tus necesidades

    public User(String username, String token) {
        this.username = username;
        this.token = token;
    }
    public User() {
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    // Getters y setters
}
