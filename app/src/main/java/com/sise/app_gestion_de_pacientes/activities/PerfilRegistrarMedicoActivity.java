package com.sise.app_gestion_de_pacientes.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.entities.Especialidad;
import com.sise.app_gestion_de_pacientes.entities.Medico;
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

        // Inicialización de vistas
        etNumeroDocumento = findViewById(R.id.et_numero_documento);
        etNombres = findViewById(R.id.et_nombres);
        etApellidos = findViewById(R.id.et_apellidos);
        etTelefono = findViewById(R.id.et_telefono);
        etDireccion = findViewById(R.id.et_direccion);
        spTipoDocumento = findViewById(R.id.spn_tipo_documento);
        spEspecialidad = findViewById(R.id.spn_especialidad);

        // Adaptador para tipo de documento
        ArrayAdapter<CharSequence> adapterTipoDoc = ArrayAdapter.createFromResource(
                this, R.array.tipo_documento_array, android.R.layout.simple_spinner_item);
        adapterTipoDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDocumento.setAdapter(adapterTipoDoc);

        // ViewModels
        medicoViewModel = new ViewModelProvider(this).get(MedicoViewModel.class);
        especialidadViewModel = new ViewModelProvider(this).get(EspecialidadViewModel.class);

        // Observador de especialidades
        especialidadViewModel.getEspecialidades().observe(this, especialidades -> {
            if (especialidades != null && !especialidades.isEmpty()) {
                especialidadesList = especialidades;
                ArrayAdapter<Especialidad> adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, especialidades);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spEspecialidad.setAdapter(adapter);
                Log.i(TAG, "Especialidades cargadas: " + especialidades.size());
            } else {
                Log.w(TAG, "No se recibieron especialidades desde el API.");
                Toast.makeText(this, "No se encontraron especialidades disponibles.", Toast.LENGTH_LONG).show();
            }
        });

        // Error en caso no cargue
        especialidadViewModel.getError().observe(this, error -> {
            if (error != null) {
                Log.e(TAG, "Error al cargar especialidades: " + error);
                Toast.makeText(this, "Error al cargar especialidades.", Toast.LENGTH_LONG).show();
            }
        });

        // Cargar especialidades desde la API
        especialidadViewModel.cargarEspecialidades();

        // Observador para resultado de inserción
        medicoViewModel.getInsertarMedicoStatus().observe(this, success -> {
            String mensaje = success ? "¡Médico registrado correctamente!" : "Error al registrar médico.";
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        });
    }

    public void onClickRegistrarMedico(View view) {
        Medico medico = validarCampos(view);
        if (medico != null) {
            Log.i(TAG, "Validación exitosa. Registrando médico...");
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
            mostrarError(view, "Número de documento inválido (mínimo 8 dígitos).");
            return null;
        }

        if (!soloLetras.matcher(nombres).matches()) {
            mostrarError(view, "Nombres deben contener solo letras.");
            return null;
        }

        String[] partesApellidos = apellidos.split(" ");
        if (partesApellidos.length < 2) {
            mostrarError(view, "Ingrese apellidos completos: paterno y materno.");
            return null;
        }

        String apellidoPaterno = partesApellidos[0];
        String apellidoMaterno = partesApellidos[1];

        if (!soloLetras.matcher(apellidoPaterno).matches() || !soloLetras.matcher(apellidoMaterno).matches()) {
            mostrarError(view, "Apellidos inválidos. Solo letras.");
            return null;
        }

        if (telefono.length() < 9) {
            mostrarError(view, "Teléfono inválido (mínimo 9 dígitos).");
            return null;
        }

        if (direccion.length() < 5) {
            mostrarError(view, "Dirección inválida (mínimo 5 caracteres).");
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