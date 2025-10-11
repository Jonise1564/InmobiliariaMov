package com.example.inmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        viewModel.getMensaje().observe(this, mensaje ->
                Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show()
        );

        viewModel.getCargando().observe(this, cargando ->
                binding.progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE)
        );
        //se utiliza para navegar a otra activity
        viewModel.getNavegacion().observe(this, navegar -> {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        });

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.etUsuario.getText().toString().trim();
            String password = binding.etClave.getText().toString().trim();
            viewModel.login(email, password);
        });
    }
}