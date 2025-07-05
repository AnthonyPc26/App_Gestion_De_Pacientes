package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.entities.Especialidad;
import com.sise.app_gestion_de_pacientes.entities.Medico;
import com.sise.app_gestion_de_pacientes.shared.Message;
import com.sise.app_gestion_de_pacientes.viewmodel.MedicoViewModel;
import com.sise.app_gestion_de_pacientes.viewmodel.EspecialidadViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class PerfilRegistrarMedicoActivity extends AppCompatActivity {

    private static final String TAG = "PerfilRegistrarMedico";

    private EditText etNumeroDocumento, etNombres, etApellidos, etTelefono, etDireccion;
    private Spinner spTipoDocumento, spEspecialidad;

    private MedicoViewModel medicoViewModel;
    private EspecialidadViewModel especialidadViewModel;
    private List<Especialidad> especialidadesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_registrar_medico);

        // ✅ Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inputs
        etNumeroDocumento = findViewById(R.id.et_numero_documento);
        etNombres = findViewById(R.id.et_nombres);
        etApellidos = findViewById(R.id.et_apellidos);
        etTelefono = findViewById(R.id.et_telefono);
        etDireccion = findViewById(R.id.et_direccion);
        spTipoDocumento = findViewById(R.id.spn_tipo_documento);
        spEspecialidad = findViewById(R.id.spn_especialidad);

        // Spinner tipo documento
        ArrayAdapter<CharSequence> adapterTipoDoc = ArrayAdapter.createFromResource(
                this, R.array.tipo_documento_array, android.R.layout.simple_spinner_item);
        adapterTipoDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDocumento.setAdapter(adapterTipoDoc);

        // ViewModels
        medicoViewModel = new ViewModelProvider(this).get(MedicoViewModel.class);
        especialidadViewModel = new ViewModelProvider(this).get(EspecialidadViewModel.class);

        // Observadores
        especialidadViewModel.getEspecialidades().observe(this, especialidades -> {
            if (especialidades != null && !especialidades.isEmpty()) {
                especialidadesList = especialidades;
                ArrayAdapter<Especialidad> adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, especialidades);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spEspecialidad.setAdapter(adapter);
                Log.i(TAG, "Especialidades cargadas: " + especialidades.size());
            } else {
                Toast.makeText(this, "No se encontraron especialidades.", Toast.LENGTH_LONG).show();
            }
        });

        especialidadViewModel.getError().observe(this, error -> {
            if (error != null) {
                Log.e(TAG, "Error al cargar especialidades: " + error);
                Toast.makeText(this, "Error al cargar especialidades.", Toast.LENGTH_LONG).show();
            }
        });

        especialidadViewModel.cargarEspecialidades();

        medicoViewModel.getInsertarMedicoStatus().observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "¡Médico registrado correctamente!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, Message.INTENTAR_MAS_TARDE, Toast.LENGTH_LONG).show();
            }
        });
    }

    // ✅ Menú en toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            cerrarSesion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        SharedPreferences prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
        prefs.edit().remove("usuario_logueado").apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onClickRegistrarMedico(View view) {
        Medico medico = validarCampos(view);
        if (medico != null) {
            medicoViewModel.insertarMedico(medico);
        }
    }

    private Medico validarCampos(View view) {
        String tipoDocumento = spTipoDocumento.getSelectedItem().toString();
        String numeroDocumento = etNumeroDocumento.getText().toString().trim();
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        Pattern soloLetras = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
        Pattern soloNumeros = Pattern.compile("^\\d{8,}$");

        if (tipoDocumento.equals("Seleccione tipo")) {
            mostrarError(view, "Seleccione un tipo de documento válido.");
            return null;
        }
        if (!soloNumeros.matcher(numeroDocumento).matches()) {
            mostrarError(view, "Número de documento inválido.");
            return null;
        }
        if (!soloLetras.matcher(nombres).matches()) {
            mostrarError(view, "Nombres inválidos.");
            return null;
        }

        String[] partesApellidos = apellidos.split(" ");
        if (partesApellidos.length < 2) {
            mostrarError(view, "Ingrese apellidos completos.");
            return null;
        }

        String apellidoPaterno = partesApellidos[0];
        String apellidoMaterno = partesApellidos[1];

        if (!soloLetras.matcher(apellidoPaterno).matches() || !soloLetras.matcher(apellidoMaterno).matches()) {
            mostrarError(view, "Apellidos inválidos.");
            return null;
        }

        if (telefono.length() < 9) {
            mostrarError(view, "Teléfono inválido.");
            return null;
        }

        if (direccion.length() < 3) {
            mostrarError(view, "Dirección inválida.");
            return null;
        }

        if (especialidadesList == null || especialidadesList.isEmpty()) {
            mostrarError(view, "No hay especialidades disponibles.");
            return null;
        }

        Especialidad especialidad = (Especialidad) spEspecialidad.getSelectedItem();
        if (especialidad == null) {
            mostrarError(view, "Seleccione una especialidad válida.");
            return null;
        }

        Medico medico = new Medico();
        medico.setTipoDocumento(tipoDocumento);
        medico.setNumeroDocumento(numeroDocumento);
        medico.setNombres(nombres);
        medico.setApellidoPaterno(apellidoPaterno);
        medico.setApellidoMaterno(apellidoMaterno);
        medico.setTelefono(telefono);
        medico.setDireccion(direccion);
        medico.setIdEspecialidad(especialidad.getIdEspecialidad());
        medico.setEstadoAuditoria("1");
        medico.setFechaRegistro(new Date());

        return medico;
    }

    private void mostrarError(View view, String mensaje) {
        Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
