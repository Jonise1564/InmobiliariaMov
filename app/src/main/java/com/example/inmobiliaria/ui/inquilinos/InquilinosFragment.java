
package com.example.inmobiliaria.ui.inquilinos;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.databinding.FragmentInquilinosBinding;


public class InquilinosFragment extends Fragment {

    private FragmentInquilinosBinding binding;
    private InquilinosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);

        //Pasaje de argunemts
        vm.recibirArgumentos(getArguments());

        //Observa el inquilino y actualiza la vista
        vm.getInquilino().observe(getViewLifecycleOwner(), inq -> {
            binding.tvNombre.setText(vm.formatoNombre());
            binding.tvDni.setText(vm.formatoDni());
            binding.tvTelefono.setText(vm.formatoTelefono());
            binding.tvEmail.setText(vm.formatoEmail());
        });

        //Observa mensajes o errores
        vm.getMensaje().observe(getViewLifecycleOwner(), mensaje -> {
            binding.tvMensaje.setText(mensaje);
        });

        return binding.getRoot();
    }
}