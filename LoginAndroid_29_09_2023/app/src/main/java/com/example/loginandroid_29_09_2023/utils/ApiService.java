package com.example.loginandroid_29_09_2023.utils;

import com.example.loginandroid_29_09_2023.beans.Pelicula;
import com.example.loginandroid_29_09_2023.beans.User;
import com.example.loginandroid_29_09_2023.login_user.model.data.MyData;
import com.example.loginandroid_29_09_2023.lstMov.data.DataMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    public static final String URL ="http://192.168.43.180:8080/untitled_war_exploded/";
      @Headers({
              "Accept: application/json",
              "Content-Type: application/json"
      })

      @GET("usuarios/login")
      Call<List<User>> getDataUser(
              @Query("ACTION") String action,
              @Query("EMAIL") String email,
              @Query("PASSWORD") String password
      );
    // Método PUT para actualizar una película
    @PUT("peliculas/{id}")
    Call<Pelicula> updatePelicula(
            @Path("id") int id,           // El ID de la película en la URL
            @Body Pelicula pelicula        // El cuerpo de la petición que contiene los datos de la película
    );

      /*@GET("MyServlet")
        Call<MyData> getDataUser(@Query("ACTION") String action,
                                 @Query("EMAIL") String email,
                                 @Query("PASSWORD") String pass);
*/
/*      @GET("MyServlet")
        Call<ArrayList<Movie>> getMovies(@Query("genre") String genre,
                                    @Query("year") Integer year,
                                    @Query("director") String director);
      }*/

      // Método que devuelve una lista de películas directamente
      @GET("/peliculas")
      Call<List<Pelicula>> getDataMovies(@Query("ACTION") String action);

        @GET("MyServlet")
        Call<DataMovies> getDataMovies2(@Query("ACTION") String action);

        /*
        @GET("MyServlet")
          Call<MyData> getMyData(@Query("id") String id);

        @GET("MyServlet/{id}")
        Call<MyData> getMyDataURL(@Path("id") String id);*/

        /*
        @FormUrlEncoded
        @POST("/login")
        Call<ApiResponse> login(@Field("username") String username, @Field("password") String password);
    */
}
