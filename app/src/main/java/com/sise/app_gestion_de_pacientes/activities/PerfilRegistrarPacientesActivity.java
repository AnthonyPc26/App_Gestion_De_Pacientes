package com.sise.app_gestion_de_pacientes.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.entities.Paciente;
import com.sise.app_gestion_de_pacientes.viewmodel.PacienteViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class PerfilRegistrarPacientesActivity extends AppCompatActivity {

    private static final String TAG = "PerfilRegistrarPaciente";
    private EditText etNumeroDocumento, etNombres, etApellidos, etFechaNacimiento, etTelefono, etCorreo, etDireccion;
    private Spinner spSexo, spTipoDocumento;
    private PacienteViewModel pacienteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Ejecutando onCreate()");
        setContentView(R.layout.activity_perfil_registrar_pacientes);

        spTipoDocumento = findViewById(R.id.spn_tipo_documento);
        etNumeroDocumento = findViewById(R.id.et_numero_documento);
        etNombres = findViewById(R.id.et_nombres);
        etApellidos = findViewById(R.id.et_apellidos);
        etFechaNacimiento = findViewById(R.id.et_fecha_nacimiento);
        etTelefono = findViewById(R.id.et_telefono);
        etCorreo = findViewById(R.id.et_correo);
        etDireccion = findViewById(R.id.et_direccion);
        spSexo = findViewById(R.id.spn_sexo);

        ArrayAdapter<CharSequence> adapterSexo = ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapterSexo);

        ArrayAdapter<CharSequence> adapterTipoDoc = ArrayAdapter.createFromResource(this, R.array.tipo_documento_array, android.R.layout.simple_spinner_item);
        adapterTipoDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDocumento.setAdapter(adapterTipoDoc);

        pacienteViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);

        pacienteViewModel.getInsertarPacienteStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success != null) {
                    String mensaje = success ? "¡Se ha insertado el Paciente correctamente!" : "¡Ocurrió un error al registrar!";
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickRegistrarPaciente(View view) {
        Paciente paciente = validarCampos(view);
        if (paciente != null) {
            Log.i(TAG, "Paciente validado, intentando guardar...");
            pacienteViewModel.insertarPaciente(paciente); // Esto debe ser asíncrono en el ViewModel
        } else {
            Log.w(TAG, "Validación fallida, paciente no insertado.");
        }
    }

    private Paciente validarCampos(View view) {
        String tipoDocumento = spTipoDocumento.getSelectedItem().toString();
        String numeroDocumento = etNumeroDocumento.getText().toString().trim();
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String fechaTexto = etFechaNacimiento.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String sexo = spSexo.getSelectedItem().toString();

        Pattern soloLetras = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
        Pattern soloNumeros = Pattern.compile("^\\d{8,}$");
        if (tipoDocumento.equals("Seleccione tipo")) {
            mostrarError(view, "Seleccione un tipo de documento válido.");
            return null;
        }
        if (!soloNumeros.matcher(numeroDocumento).matches()) {
            mostrarError(view, "Ingrese un número de documento válido (mínimo 8 dígitos).");
            return null;
        }
        if (!soloLetras.matcher(nombres).matches()) {
            mostrarError(view, "Ingrese un nombre válido (solo letras).");
            return null;
        }
        String[] partesApellidos = apellidos.split(" ");
        if (partesApellidos.length < 2 || !soloLetras.matcher(partesApellidos[0]).matches() || !soloLetras.matcher(partesApellidos[1]).matches()) {
            mostrarError(view, "Ingrese apellidos válidos: paterno y materno, solo letras.");
            return null;
        }
        Date fechaNacimiento;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            fechaNacimiento = formato.parse(fechaTexto);
        } catch (Exception e) {
            mostrarError(view, "Fecha inválida. Usa formato dd/MM/yyyy.");
            return null;
        }
        if (telefono.length() < 9) {
            mostrarError(view, "Ingrese un número de teléfono válido (mínimo 9 dígitos).");
            return null;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            mostrarError(view, "Ingrese un correo válido.");
            return null;
        }
        if (direccion.length() < 5) {
            mostrarError(view, "Ingrese una dirección válida (mínimo 5 caracteres).");
            return null;
        }
        if (sexo.equals("Seleccione sexo")) {
            mostrarError(view, "Seleccione un sexo válido.");
            return null;
        }
        sexo = sexo.substring(0, 1);
        Paciente paciente = new Paciente();
        paciente.setTipoDocumento(tipoDocumento);
        paciente.setNumeroDocumento(numeroDocumento);
        paciente.setNombres(nombres);
        paciente.setApellidoPaterno(partesApellidos[0]);
        paciente.setApellidoMaterno(partesApellidos[1]);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setTelefono(telefono);
        paciente.setCorreo(correo);
        paciente.setDireccion(direccion);
        paciente.setSexo(sexo);
        paciente.setEstadoAuditoria("1");
        paciente.setFechaRegistro(new Date());
        return paciente;
    }
    private void mostrarError(View view, String mensaje) {
        Toast.makeText(view.getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}