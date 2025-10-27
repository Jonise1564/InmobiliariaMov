package com.example.inmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri= new MutableLiveData<>();
    public LiveData<Uri> getmUri() {
        return mUri;
    }


    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }
}