package com.example.loginandroid_29_09_2023.lstMov.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.utils.ApiService;
import com.example.loginandroid_29_09_2023.utils.RetrofitCliente;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPeliculaDialogFragment extends DialogFragment {

    private Pelicula pelicula;
    private int position;
    private EditText etTitulo, etDescripcion, etDirector, etAnyo;
    private OnSaveListener listener;

    // Interfaz para notificar cuando se guarda la película
    public interface OnSaveListener {
        void onSave(Pelicula peliculaEditada, int position);
    }

    public void setOnSaveListener(OnSaveListener listener) {
        this.listener = listener;
    }

    public static EditarPeliculaDialogFragment newInstance(Pelicula pelicula, int position) {
        EditarPeliculaDialogFragment fragment = new EditarPeliculaDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("pelicula", (Serializable) pelicula);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_pelicula, container, false);

        etTitulo = view.findViewById(R.id.etTitulo);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        etDirector = view.findViewById(R.id.etDirector);
        etAnyo = view.findViewById(R.id.etAnyo);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);

        if (getArguments() != null) {
            pelicula = (Pelicula) getArguments().getSerializable("pelicula");
            position = getArguments().getInt("position");

            // Rellenar los campos con los datos de la película
            etTitulo.setText(pelicula.getTitulo());
            etDescripcion.setText(pelicula.getDescripcion());
            etDirector.setText(pelicula.getDirector());
            etAnyo.setText(String.valueOf(pelicula.getAnyo()));
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actualizar los datos de la película
                pelicula.setTitulo(etTitulo.getText().toString());
                pelicula.setDescripcion(etDescripcion.getText().toString());
                pelicula.setDirector(etDirector.getText().toString());
                pelicula.setAnyo(Integer.parseInt(etAnyo.getText().toString()));

                // Llamar al servidor para guardar la película
                guardarCambiosEnServidor(pelicula);
            }
        });

        return view;
    }

    private void guardarCambiosEnServidor(Pelicula pelicula) {
        // Crear la instancia de Retrofit
        ApiService apiService = RetrofitCliente.getClient(ApiService.URL).create(ApiService.class);

        // Hacer la llamada al endpoint PUT para actualizar la película
        Call<Pelicula> call = apiService.updatePelicula(pelicula.getId(), pelicula);
        call.enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                if (response.isSuccessful()) {
                    Pelicula peliculaActualizada = response.body();
                    // Notificar al listener que la película ha sido editada y actualizada en el servidor
                    if (listener != null) {
                        listener.onSave(peliculaActualizada, position);
                    }
                    // Cerrar el diálogo
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Error al actualizar la película", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
