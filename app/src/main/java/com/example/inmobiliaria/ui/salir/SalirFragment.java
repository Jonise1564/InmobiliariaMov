package com.example.inmobiliaria.ui.salir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentSalirBinding;

public class SalirFragment extends Fragment {

    private FragmentSalirBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mostrar el diálogo de confirmación
        new AlertDialog.Builder(getContext())
                .setTitle("Confirmación de salida")
                .setMessage("¿Quiere cerrar la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    requireActivity().finishAffinity(); // Cierra todas las actividades
                    System.exit(0); // Finaliza el proceso
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Navigation.findNavController(root).navigate(R.id.nav_inquilinos); // Redirige a inicio
                })
                .show();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
