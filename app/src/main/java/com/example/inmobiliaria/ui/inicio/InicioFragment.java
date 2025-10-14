package com.example.inmobiliaria.ui.inicio;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment {

    private InicioViewModel mViewModel;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getViewLifecycleOwnerLiveData().observe(getViewLifecycleOwner(), lifecycleOwner -> {
                if (lifecycleOwner != null) {
                    mapFragment.getMapAsync(googleMap -> {
                        mViewModel.getUbicacionInmobiliaria().observe(lifecycleOwner, ubicacion -> {
                            mViewModel.getTituloMarker().observe(lifecycleOwner, titulo -> {
                                mViewModel.getZoom().observe(lifecycleOwner, zoom -> {
                                    googleMap.addMarker(new MarkerOptions().position(ubicacion).title(titulo));
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, zoom));
                                });
                            });
                        });
                    });
                }
            });
        }
} }
