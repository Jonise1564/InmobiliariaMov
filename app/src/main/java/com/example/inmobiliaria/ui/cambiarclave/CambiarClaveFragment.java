package com.example.inmobiliaria.ui.cambiarclave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliaria.databinding.FragmentCambiarClaveBinding;
import com.example.inmobiliaria.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel mViewModel;
    private FragmentCambiarClaveBinding binding;

    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCambiarClaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);

        binding.btnCambiarClave.setOnClickListener(v -> {
            String actual = binding.etClaveActual.getText().toString().trim();
            String nueva = binding.etClaveNueva.getText().toString().trim();
            String repetir = binding.etConfirmClave.getText().toString().trim();

            mViewModel.cambiarClave(actual,repetir,nueva);
        });
        binding.btnCancelar.setOnClickListener(v -> {
            binding.etClaveActual.setText("");
            binding.etClaveNueva.setText("");
            binding.etConfirmClave.setText("");
            // volver al fragment anterior
            requireActivity().getOnBackPressedDispatcher().onBackPressed();


        });
        mViewModel.getEstadoCambio().observe(getViewLifecycleOwner(), resultado -> {
            if(resultado){
                // limpiar los campos
                binding.etClaveActual.setText("");
                binding.etClaveNueva.setText("");
                binding.etConfirmClave.setText("");
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
