package com.example.inmobiliaria.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navegacion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>();

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public MutableLiveData<Boolean> getNavegacion() {
        return navegacion;
    }

    public MutableLiveData<Boolean> getCargando() {
        return cargando;
    }

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String email, String password) {
        cargando.setValue(true);

        ApiClient.InmoService apiService = ApiClient.getApiInmobiliaria();
        Call<String> call = apiService.login(email, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                cargando.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    mensaje.postValue("Bienvenido");
                    navegacion.postValue(true);
                } else {
                    mensaje.postValue("Usuario y/o contraseña incorrectos. Intente nuevamente.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                cargando.setValue(false);
                mensaje.postValue("Error de conexión con el servidor. Verifique su conexión e intente más tarde.");
            }
        });
    }
}
