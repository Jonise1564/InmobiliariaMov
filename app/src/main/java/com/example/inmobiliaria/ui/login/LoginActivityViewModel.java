package com.example.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    public MutableLiveData<String> mMensaje = new MutableLiveData<>();


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public void login(String mail, String clave){
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(mail,clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    String token = response.body();
                    ApiClient.guardarToken(getApplication(),token);
                    mMensaje.postValue("Bienvenido");
                    //Llamo al Activity Menu Navegable

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);

                }
                else
                    mMensaje.postValue("Usuario y/o contraseña Incorrecta; reintente");


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de Servidor");
            }
        });



    }


}
