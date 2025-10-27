package com.example.inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getInmueble(){
        return inmueble;
    }

    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");

        if(inmueble != null){
            this.inmueble.setValue(inmueble);
        }

    }
    public void actualizarDisponibilidadInmueble(Boolean disponible){
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.inmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = ApiClient.getApiInmobiliaria().actualizarDisponibilidadInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Disponibilidad actualizada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Fallo de conexión:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}