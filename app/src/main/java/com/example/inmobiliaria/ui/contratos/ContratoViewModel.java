package com.example.inmobiliaria.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.request.ApiClient;
import com.example.inmobiliaria.request.InmoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmueblesVigentes = new MutableLiveData<>();
    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesVigentes() {
        return inmueblesVigentes;
    }

    //Llama al endpoint para obtener los inmuebles con contrato vigente
    public void cargarInmueblesConContratoVigente() {
        String token = ApiClient.leerToken(getApplication());
        InmoService api = ApiClient.getApiInmobiliaria();

        Call<List<Inmueble>> call = api.obtenerInmueblesConContratoVigente("Bearer " + token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmueblesVigentes.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener los inmuebles con contrato vigente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}