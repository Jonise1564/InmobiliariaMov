package com.example.inmobiliaria.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.example.inmobiliaria.model.Contrato;


public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;

    private FragmentDetalleContratoBinding binding;

    private Contrato contrato;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        vm.recibirArgumentos(getArguments());

        vm.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            //Mostrar los titulos fijos y los datos formateados
            binding.tvDireccion.setText("Dirección: " + vm.obtenerDireccion());
            binding.tvFechas.setText(vm.obtenerFechas());
            binding.tvInquilino.setText("Inquilino: " + vm.obtenerInquilino());
            binding.tvMonto.setText("Monto: " + vm.obtenerMonto());
            binding.tvEstado.setText("Estado: " + vm.obtenerEstado());
            this.contrato = contrato;//Inicializo contrato
        });


        //Estos botones llaman a los metodos del viewModel
        binding.btnVerPagos.setOnClickListener(v -> {
            //Navego al fragment de pagos
            Bundle bundle = new Bundle();
            bundle.putInt("idContrato", contrato.getIdContrato());

            Navigation.findNavController(v).navigate(R.id.pagosFragment, bundle);
        });
        binding.btnVerInquilino.setOnClickListener(v -> {

            //Navego al fragment de inquilino
            Bundle bundle = new Bundle();
            bundle.putSerializable("inquilino", contrato.getInquilino());
         Navigation.findNavController(v).navigate(R.id.inquilinosFragment, bundle);

        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}