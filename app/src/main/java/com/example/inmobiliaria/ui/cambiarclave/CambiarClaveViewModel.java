package com.example.inmobiliaria.ui.cambiarclave;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.model.Propietario;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> estadoCambio = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();


    public LiveData<Boolean> getEstadoCambio() {
        return estadoCambio;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }


    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }

    public void cambiarClave(String actual, String nueva, String confirmar) {
        if (actual.trim().isEmpty() || nueva.trim().isEmpty() || confirmar.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
            estadoCambio.setValue(false);
            return;
        }

        if (!nueva.equals(confirmar)) {

            Toast.makeText(getApplication(), "La nueva contraseña y la confirmación no coinciden", Toast.LENGTH_LONG).show();
            estadoCambio.setValue(false);
            return;
        }

        if (actual.equals(nueva)) {
            Toast.makeText(getApplication(), "La nueva contraseña debe ser diferente a la actual", Toast.LENGTH_LONG).show();
            estadoCambio.setValue(false);
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        if (token == null || token.trim().isEmpty()) {
            Toast.makeText(getApplication(), "No se pudo autenticar el usuario", Toast.LENGTH_LONG).show();
            estadoCambio.setValue(false);
            return;
        }

        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().cambiarClave("Bearer " + token, actual, nueva);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña actualizada correctamente", Toast.LENGTH_LONG).show();

                    estadoCambio.setValue(true);

                } else {
                        Toast.makeText(getApplication(), "Error al cambiar la contraseñe", Toast.LENGTH_LONG).show();
                        estadoCambio.setValue(false);
                        Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

                Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();

                estadoCambio.setValue(false);
                Log.d("Error", t.getMessage());
            }
        });
    }

}
