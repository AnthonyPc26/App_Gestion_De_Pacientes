package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.viewmodel.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etContrasena;
    private Switch switchAdmin;
    private Button btnLogin;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etPassword);
        switchAdmin = findViewById(R.id.switchAdmin);
        btnLogin = findViewById(R.id.btnLogin);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuarioLoginLiveData().observe(this, usuario -> {
            String rolSeleccionado = switchAdmin.isChecked() ? "ADMINISTRADOR" : "DOCTOR";
            if (usuario == null) {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            } else {
                String rolReal = usuario.getRol().getNombreRol().toUpperCase();

                if (!rolReal.equals(rolSeleccionado)) {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
                prefs.edit().putString("usuario_logueado", new Gson().toJson(usuario)).apply();

                Intent intent;
                if (rolReal.equals("ADMINISTRADOR")) {
                    intent = new Intent(this, PerfilAdministradorActivity.class);
                } else {
                    intent = new Intent(this, MenuActivity.class);
                    intent.putExtra("nombreUsuario", usuario.getUsuario());
                }

                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(view -> validarLogin());
    }

    private void validarLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (usuario.isEmpty()) {
            Toast.makeText(this, "Ingrese su usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contrasena.isEmpty()) {
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        String rol = switchAdmin.isChecked() ? "ADMINISTRADOR" : "DOCTOR";
        usuarioViewModel.loginUsuario(usuario, contrasena, rol);
    }

    public void irARegistrarUsuario(android.view.View view) {
        Intent intent = new Intent(this, PerfilRegistrarUsuarioActivity.class);
        startActivity(intent);
    }
}
