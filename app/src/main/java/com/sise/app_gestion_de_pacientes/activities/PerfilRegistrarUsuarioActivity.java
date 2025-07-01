package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.entities.Rol;
import com.sise.app_gestion_de_pacientes.entities.Usuario;
import com.sise.app_gestion_de_pacientes.repositories.RolRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class PerfilRegistrarUsuarioActivity extends AppCompatActivity {

    private EditText etUsuario, etCorreo;
    private UsuarioViewModel usuarioViewModel;
    private Spinner spinnerRoles;
    private List<Rol> listaRoles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_registrar_usuario);

        etUsuario = findViewById(R.id.etUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        spinnerRoles = findViewById(R.id.spinnerRoles);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        observerUsuarioViewModel();
        cargarRolesEnSpinner();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });
    }
    private void cargarRolesEnSpinner() {
        RolRepository rolRepository = new RolRepository();
        rolRepository.obtenerRoles(new Callback<List<Rol>>() {
            @Override
            public void onSuccess(List<Rol> roles) {
                runOnUiThread(() -> {
                    listaRoles = roles;

                    List<String> nombresRoles = new ArrayList<>();
                    for (Rol rol : roles) {
                        nombresRoles.add(rol.getNombreRol());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(PerfilRegistrarUsuarioActivity.this, android.R.layout.simple_spinner_item, nombresRoles);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRoles.setAdapter(adapter);
                });
            }
            @Override
            public void onFailure() {
                runOnUiThread(() -> Toast.makeText(PerfilRegistrarUsuarioActivity.this, "Error al cargar roles", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void observerUsuarioViewModel() {
        usuarioViewModel.getInsertarUsuarioStatus().observe(this, success -> {
            if (!success) {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    public void onClickRegistrarUsuario(View view) {
        String usuarioStr = etUsuario.getText().toString().trim();
        String correoStr = etCorreo.getText().toString().trim();

        if (usuarioStr.isEmpty() || correoStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int posicionSeleccionada = spinnerRoles.getSelectedItemPosition();
        if (posicionSeleccionada < 0 || posicionSeleccionada >= listaRoles.size()) {
            Toast.makeText(this, "Seleccione un rol válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Rol rolSeleccionado = listaRoles.get(posicionSeleccionada);
        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioStr);
        usuario.setCorreo(correoStr);
        usuario.setRol(rolSeleccionado);
        usuarioViewModel.insertarUsuario(usuario);
    }
}