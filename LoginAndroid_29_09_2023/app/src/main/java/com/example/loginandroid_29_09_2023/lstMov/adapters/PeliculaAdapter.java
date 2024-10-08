package com.example.loginandroid_29_09_2023.lstMov.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.loginandroid_29_09_2023.R;
import com.example.loginandroid_29_09_2023.beans.Pelicula;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private List<Pelicula> peliculas;
    private Context context;

    public PeliculaAdapter(Context context, List<Pelicula> peliculas) {
        this.context = context;
        this.peliculas = peliculas;
    }

    public interface OnItemClickListener {
        void onItemClick(Pelicula pelicula);
    }

    private OnItemClickListener listener;

    // Método para establecer el listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_card, parent, false);
        return new ViewHolder(view, listener, this); // Pasar la referencia al adaptador al ViewHolder
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pelicula pelicula = peliculas.get(position);
        holder.tvTitulo.setText(pelicula.getTitulo());
        holder.tvDescripcion.setText(pelicula.getDescripcion());
        holder.tvDirector.setText(pelicula.getDirector());
        holder.tvAnyo.setText(String.valueOf(pelicula.getAnyo()));
        Glide.with(context).load(pelicula.getUrlImagen()).into(holder.ivPeliculaImagen);
        holder.bind(pelicula);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    // Método para actualizar una película en la lista
    public void updateMovie(Pelicula pelicula, int position) {
        peliculas.set(position, pelicula);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitulo, tvDescripcion, tvDirector, tvAnyo;
        public ImageView ivPeliculaImagen;
        private Pelicula currentPelicula;
        private PeliculaAdapter adapter; // Referencia al adaptador

        // Constructor del ViewHolder
        public ViewHolder(View itemView, final OnItemClickListener listener, PeliculaAdapter adapter) {
            super(itemView);
            this.adapter = adapter; // Asignar la referencia al adaptador

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            tvAnyo = itemView.findViewById(R.id.tvAnyo);
            ivPeliculaImagen = itemView.findViewById(R.id.ivPeliculaImagen);

            // Detectar clics en el ítem para abrir el modal de edición
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(currentPelicula);

                            // Abrir el dialog fragment para editar la película
                            EditarPeliculaDialogFragment dialog = EditarPeliculaDialogFragment.newInstance(currentPelicula, position);
                            dialog.setOnSaveListener(new EditarPeliculaDialogFragment.OnSaveListener() {
                                @Override
                                public void onSave(Pelicula peliculaEditada, int pos) {
                                    // Actualizar la película en el adaptador
                                    adapter.updateMovie(peliculaEditada, pos); // Llamar al método desde el adaptador
                                }
                            });

                            dialog.show(((FragmentActivity) itemView.getContext()).getSupportFragmentManager(), "EditarPeliculaDialog");
                        }
                    }
                }
            });
        }

        public void bind(Pelicula pelicula) {
            currentPelicula = pelicula;
        }
    }
}
