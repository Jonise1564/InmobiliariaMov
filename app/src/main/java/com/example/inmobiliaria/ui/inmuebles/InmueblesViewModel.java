package com.example.inmobiliaria.ui.inmuebles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InmueblesViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mText;

    public InmueblesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("INQUILINOS");
    }

    public LiveData<String> getText() {
        return mText;
    }
}