package com.example.inmobiliaria.ui.perfil;


import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.model.Propietario;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData <Propietario> mp= new MutableLiveData<>();
    private MutableLiveData<Boolean>    bMestado= new MutableLiveData<>();
    private  MutableLiveData<Integer> mIcono= new MutableLiveData<>();




    public LiveData<String> getmTag() {
        return mTag;
    }

    private  MutableLiveData<String> mTag= new MutableLiveData<>();


    public LiveData<Integer> getmIcono() {
        return mIcono;
    }
    public LiveData<Boolean> getbMestado() {
        return bMestado;
    }
    public LiveData<Propietario> getMP(){
        return mp;
    }


    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }


    public void guardar(String icono, String dni, String nom, String ap, String email, String tel) {
        if (icono.equals("editar")) {
            bMestado.setValue(true);
            mIcono.setValue(android.R.drawable.ic_menu_save);
            mTag.setValue("guardar");
        } else {
            // validacion de campos
            if (dni.isEmpty() || nom.isEmpty() || ap.isEmpty() || email.isEmpty() || tel.isEmpty()) {
                Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
                return;
            }

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                Toast.makeText(getApplication(), "Email inválido", Toast.LENGTH_LONG).show();
                return;
            }

            if (!tel.matches("^[\\d\\s\\-()+]{7,20}$")) {
                Toast.makeText(getApplication(), "Teléfono inválido", Toast.LENGTH_LONG).show();
                return;
            }

            Propietario p = new Propietario();
            p.setIdPropietario(mp.getValue().getIdPropietario());
            p.setDni(dni);
            p.setNombre(nom);
            p.setApellido(ap);
            p.setEmail(email);
            p.setTelefono(tel);
            p.setClave(null);

            mIcono.setValue(android.R.drawable.ic_menu_edit);
            mTag.setValue("editar");
            bMestado.setValue(false);

            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer " + token, p);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Propietario actualizado", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), "Error al actualizar propietario", Toast.LENGTH_LONG).show();
                        Log.d("Error", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void leerPropietario(){
        String token= ApiClient.leerToken(getApplication());
        Call<Propietario> llamada =  ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer " +token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    mp.postValue(response.body());
                }
                else {
                    Toast.makeText(getApplication(),"Error al obtener el propietario",Toast.LENGTH_LONG).show();
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(),"Error en el servidor",Toast.LENGTH_LONG).show();
                Log.d("Error", t.getMessage());

            }
        });
    }


}