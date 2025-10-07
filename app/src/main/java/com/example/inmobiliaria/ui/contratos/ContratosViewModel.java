package com.example.inmobiliaria.ui.contratos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContratosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ContratosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}