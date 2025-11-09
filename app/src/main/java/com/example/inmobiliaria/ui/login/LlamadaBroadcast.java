package com.example.inmobiliaria.ui.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import android.widget.Toast;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class LlamadaBroadcast extends BroadcastReceiver {
    public static boolean appIniciada = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean deteccionAgitado = intent.getBooleanExtra("deteccion_agitado", false);
        if (deteccionAgitado) {
            realizarLlamada(context);
        }
        appIniciada = true;
    }

    private void realizarLlamada(Context context) {
        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:2664585470")); // teléfono de la mio para probar
        intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intentLlamada);
        } else {
            Toast.makeText(context, "Permiso denegado para llamadas telefónicas", Toast.LENGTH_LONG).show();
        }
    }
}