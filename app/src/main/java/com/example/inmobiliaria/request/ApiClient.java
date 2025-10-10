package com.example.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    private static InmoService inmoService;

    // Interfaz interna para definir los endpoints
    public interface InmoService {
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);
    }

    // Inicializa Retrofit y devuelve la instancia del servicio
    public static InmoService getApiInmobiliaria() {
        //Evitar múltiples instancias
        //Ahorrar memoria y recursos
        //Mantener consistencia
        if (inmoService == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URLBASE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            inmoService = retrofit.create(InmoService.class);
        }
        return inmoService;
    }

    // Guarda el token en SharedPreferences
    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    // Lee el token desde SharedPreferences
    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
}
