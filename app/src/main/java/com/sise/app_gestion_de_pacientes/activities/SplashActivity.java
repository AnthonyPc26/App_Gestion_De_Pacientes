package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.entities.Usuario;
import com.sise.app_gestion_de_pacientes.viewmodel.UsuarioViewModel;

public class SplashActivity extends AppCompatActivity {

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuarioValidadoLiveData().observe(this, usuario -> {
            if (usuario == null) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                Toast.makeText(this, "¡Bienvenido " + usuario.getUsuario() + "!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,
                        usuario.getRol().getIdRol().equals("ADMINISTRADOR")
                                ? PerfilAdministradorActivity.class
                                : MenuActivity.class
                );
                startActivity(intent);
            }
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            usuarioViewModel.validarUsuarioLogueado();
        }, 2000);
    }
}
