package com.example.inmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.request.ApiClient;
import com.example.inmobiliaria.request.InmoService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri= new MutableLiveData<>();



    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getmUri() {
        return mUri;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }

    public void cargarInmueble(String direccion, String valor, String tipo, String uso,String ambientes, String superficie, boolean disponible){
        int superficiePars, ambientesPars;
        double precio;


        try {
            precio=Double.parseDouble(valor);
            superficiePars=Integer.parseInt(superficie);
            ambientesPars=Integer.parseInt(ambientes);
            if      (direccion.isBlank() || tipo.isBlank() || uso.isBlank() ||
                    ambientes.isBlank() || superficie.isBlank() || valor.isBlank()) {
                Toast.makeText(getApplication(), "Debe ingresar todos lo campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if(mUri.getValue()==null){
                Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_SHORT).show();
            }

            Inmueble inmueble=new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setValor(precio);
            inmueble.setTipo(tipo);
            inmueble.setUso(uso);
            inmueble.setAmbientes(ambientesPars);
            inmueble.setSuperficie(superficiePars);
            inmueble.setDisponible(disponible);

            byte[] imagen = transformarImagen();
            //Transforma atraves de gson para convertirlo en JSON
            String inmuebleJson = new Gson().toJson(inmueble);

            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);

            //Armar Multipart
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

            InmoService inmoService = ApiClient.getApiInmobiliaria();
            String token= ApiClient.leerToken(getApplication());
            Call<Inmueble> call = inmoService.cargarInmueble("Bearer " + token, imagePart, inmuebleBody);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble cargado correctamente", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplication(), "ERROR al cargar inmueble", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "ERROR al cargar inmueble", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (NumberFormatException mfe) {
            Toast.makeText(getApplication(), "Error ingresar numeros en los campos de valor, superficie y ambientes", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();  //lo puedo usar porque estoy en viewmodel
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {
            Toast.makeText(getApplication(), "No ha seleccinado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }
}