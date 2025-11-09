package com.example.inmobiliaria.ui.contratos;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.databinding.FragmentContratosBinding;


public class ContratoFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratosBinding binding;

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);

        vm.getInmueblesVigentes().observe(getViewLifecycleOwner(), inmuebles -> {
            ContratoAdapter adapter = new ContratoAdapter(inmuebles, getContext());
            binding.rvListaContrato.setLayoutManager(new GridLayoutManager(getContext(), 2));
            binding.rvListaContrato.setAdapter(adapter);
        });

        vm.cargarInmueblesConContratoVigente();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(ContratoViewModel.class);
        // TODO: Use the ViewModel
    }
}
