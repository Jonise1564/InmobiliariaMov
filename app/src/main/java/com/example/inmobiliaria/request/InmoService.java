package com.example.inmobiliaria.request;

import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.model.Propietario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface InmoService {

    @FormUrlEncoded
    @POST("api/Propietarios/login")
    Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

    @GET("api/Propietarios")
    Call<Propietario> obtenerPropietario(@Header("Authorization") String token);

    @PUT("api/Propietarios/actualizar")
    Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);

    @GET("api/Inmuebles")
    Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);


    @FormUrlEncoded
    @PUT("api/Propietarios/changePassword")
    Call<Propietario> cambiarClave(
            @Header("Authorization") String token,
            @Field("currentPassword") String currentPassword,
            @Field("newPassword") String newPassword
    );




}
