package com.example.inmobiliaria.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentCrearInmuebleBinding;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mViewModel;

    private FragmentCrearInmuebleBinding binding;

    private ActivityResultLauncher<Intent> arl;
    private Intent intent;


    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       //return inflater.inflate(R.layout.fragment_crear_inmueble, container, false);
        mViewModel= new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        binding=FragmentCrearInmuebleBinding.inflate(getLayoutInflater());
        abrirGaleria();
        binding.btsubirimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        mViewModel.getmUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imginmueble.setImageURI(uri);
            }
        });

        binding.btguardarinmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.cargarInmueble(
                        binding.etdireccion.getText().toString(),
                        binding.etprecio.getText().toString(),
                        binding.ettipo.getText().toString(),
                        binding.etuso.getText().toString(),
                        binding.etambientes.getText().toString(),
                        binding.etsuperficie.getText().toString(),
                        binding.cbdisponible.isChecked()
                );
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//TODO PARA ABRIR LA GALERIA
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d("AgregarInmuebleFragment","Result"+ result);
                mViewModel.recibirFoto(result);

            }
        });
    }

}