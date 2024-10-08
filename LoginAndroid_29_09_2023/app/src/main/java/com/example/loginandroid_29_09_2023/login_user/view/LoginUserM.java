package com.example.loginandroid_29_09_2023.login_user.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.User;
import com.example.loginandroid_29_09_2023.login_user.ContractLoginUser;
import com.example.loginandroid_29_09_2023.login_user.presenter.LoginUserPresenter;
import com.example.loginandroid_29_09_2023.lstMov.view.LstMovies;
import com.google.android.material.button.MaterialButton;

//FUNCIONALIDAD ACTIVITY LOGIN
public class LoginUserM extends AppCompatActivity implements ContractLoginUser.View {

    private EditText edtEmail;
    private EditText edtPassword;
    private MaterialButton btnLogin;
//INSTANCIO SERVICE
    private LoginUserPresenter presenter = new LoginUserPresenter(this);

    /* PATRÓN SINGLETON */
    private static LoginUserM mainActivity = null;
    public static LoginUserM getInstance() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LoginUserM", "onCreate llamado"); // Agregar un log aquí
        Toast.makeText(this, "Actividad creada", Toast.LENGTH_SHORT).show(); // Mostrar un toast aquí
        setContentView(R.layout.activity_login_user_m);
        mainActivity = this;
        initComponents();  // Inicializar componentes
    }


    private void initComponents() {
        Log.d("LoginUserM", "Iniciando componentes de la UI");

        // Conectar los campos de email y password con el layout
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin); // Aquí se conecta el botón desde el XML

        // Verificar si el botón se está inicializando correctamente
        if (btnLogin == null) {
            Log.e("LoginUserM", "Error: btnLogin es null");
        } else {
            Log.d("LoginUserM", "btnLogin encontrado, configurando OnClickListener");
        }

        // Agregar funcionalidad al botón Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginUserM", "Clic en el botón Login detectado");
                Toast.makeText(LoginUserM.this, "Botón Login presionado", Toast.LENGTH_SHORT).show(); // Notificación en pantalla

                // Obtener los valores ingresados en los campos de texto
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                Log.d("LoginUserM", "Email: " + email + " Password: " + password);

                // Validar que los campos no estén vacíos
                if (email.isEmpty()) {
                    Log.e("LoginUserM", "Error: el campo de email está vacío");
                    edtEmail.setError("Por favor ingresa tu email");
                    return;
                }

                if (password.isEmpty()) {
                    Log.e("LoginUserM", "Error: el campo de contraseña está vacío");
                    edtPassword.setError("Por favor ingresa tu contraseña");
                    return;
                }

                // Crear el objeto User con los valores ingresados
                User user = new User();
                user.setUsername(email);  // Asignar el email ingresado
                user.setToken(password);   // Asignar la contraseña ingresada

                // Agregar Log antes de llamar al presenter
                Log.d("LoginUserM", "Enviando User al Presenter: " + user.getUsername());

                // Llamar al método login del presenter
                presenter.login(user); // Aquí llamas la función loginAPI a través del presenter
            }
        });
    }

    // Implementación del callback para éxito en el login
    @Override
    public void successLogin(User user) {
        Log.d("LoginUserM", "Login exitoso para el usuario: " + user.getUsername());
        Toast.makeText(mainActivity, "Login exitoso, bienvenido " + user.getUsername(), Toast.LENGTH_SHORT).show();
        // Redirigir a la siguiente pantalla o realizar alguna acción
        Intent cambiarAPantallPelis = new Intent(LoginUserM.getInstance(), LstMovies.class);
        startActivity(cambiarAPantallPelis);
    }

    // Implementación del callback para error en el login
    @Override
    public void failureLogin(String err) {
        Log.e("LoginUserM", "Error en el login: " + err);
        Toast.makeText(mainActivity, "Error en login: " + err, Toast.LENGTH_SHORT).show();
    }
}
