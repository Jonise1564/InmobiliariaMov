package com.example.inmobiliaria.ui.pagos;

import androidx.lifecycle.ViewModel;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.inmobiliaria.model.Pago;
import com.example.inmobiliaria.request.ApiClient;
import com.example.inmobiliaria.request.InmoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> pagos = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private Application app;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<List<Pago>> getPagos() {
        return pagos;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    //Recibe los argumentos del fragment
    public void recibirArgumentos(Bundle args) {
        if (args == null) {
            mensaje.postValue("No se enviaron datos válidos para mostrar");
            return;
        }
        int idContrato = args.getInt("idContrato", -1);
        if (idContrato != -1) {
            cargarPagosPorContrato(idContrato);
        } else {
            mensaje.postValue("ID de contrato inválido.");
        }
    }

    //Carga los pagos de un contrato
    private void cargarPagosPorContrato(int idContrato) {
        String token = ApiClient.leerToken(app);
        InmoService api = ApiClient.getApiInmobiliaria();

        //Realiza la llamada a la API
        Call<List<Pago>> call = api.obtenerPagosPorContrato("Bearer " + token, idContrato);
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Pago> lista = response.body();

                   // Formatear las fechas
                    SimpleDateFormat entrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat salida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                    for (Pago p : lista) {
                        try {
                            Date fecha = entrada.parse(p.getFechaPago());
                            p.setFechaPago(salida.format(fecha));
                        } catch (Exception e) {
                            Toast.makeText(app, "Error al formatear fecha: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    pagos.postValue(lista);
                    mensaje.postValue(lista.isEmpty() ? "No hay pagos para este contrato." : "");
                } else {
                    mensaje.postValue("Error al obtener los pagos:");
                }
            }
            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                mensaje.postValue("Error de conexión: " + t.getMessage());
                Toast.makeText(app, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}