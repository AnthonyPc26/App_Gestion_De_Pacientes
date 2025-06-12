package com.sise.app_gestion_de_pacientes.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.entities.Paciente;
import com.sise.app_gestion_de_pacientes.viewmodel.PacienteViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PerfilRegistrarPacientesActivity extends AppCompatActivity {

    private PacienteViewModel pacienteViewModel;
    private final String TAG = PerfilRegistrarPacientesActivity.class.getName();
    private EditText etDni, etNombres, etApellidos, etFechaNacimiento, etTelefono, etCorreo, etDireccion;
    private Spinner spSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Ejecutado metodo onCreate()");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_registrar_pacientes);

        // Inicializar vistas
        etDni = findViewById(R.id.et_dni);
        etNombres = findViewById(R.id.et_nombres);
        etApellidos = findViewById(R.id.et_apellidos); // Aquí van ambos apellidos
        etFechaNacimiento = findViewById(R.id.et_fecha_nacimiento);
        etTelefono = findViewById(R.id.et_telefono);
        etCorreo = findViewById(R.id.et_correo);
        etDireccion = findViewById(R.id.et_direccion);
        spSexo = findViewById(R.id.spn_sexo);

        // Adapter para Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sexo_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapter);

        pacienteViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        observePacienteViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void observePacienteViewModel() {
        pacienteViewModel.getInsertarPacienteStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                String text = aBoolean ? "¡Se ha insertado el Paciente correctamente!" : "¡Ocurrió un error inesperado!";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClickRegistrarPaciente(View view) {
        runOnUiThread(() -> {
            String dni = etDni.getText().toString().trim();
            String nombres = etNombres.getText().toString().trim();
            String apellidosCompletos = etApellidos.getText().toString().trim();
            String fechaTexto = etFechaNacimiento.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String sexo = spSexo.getSelectedItem().toString();

            // Validaciones
            if (dni.isEmpty() || !dni.matches("\\d{8}")) {
                Toast.makeText(view.getContext(), "Ingrese un DNI válido de 8 dígitos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nombres.isEmpty() || !nombres.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                Toast.makeText(view.getContext(), "Ingrese un nombre válido (solo letras).", Toast.LENGTH_SHORT).show();
                return;
            }

            if (apellidosCompletos.isEmpty()) {
                Toast.makeText(view.getContext(), "Ingrese ambos apellidos separados por espacio.", Toast.LENGTH_SHORT).show();
                return;
            }

            String apellidoPaterno = "";
            String apellidoMaterno = "";

            String[] partes = apellidosCompletos.split(" ");
            if (partes.length >= 2) {
                apellidoPaterno = partes[0];
                apellidoMaterno = partes[1];
            } else {
                Toast.makeText(view.getContext(), "Debe ingresar al menos dos apellidos (paterno y materno).", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!apellidoPaterno.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$") ||
                    !apellidoMaterno.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                Toast.makeText(view.getContext(), "Los apellidos deben contener solo letras.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fechaTexto.isEmpty()) {
                Toast.makeText(view.getContext(), "Ingrese la fecha de nacimiento.", Toast.LENGTH_SHORT).show();
                return;
            }

            Date fechaNacimiento;
            try {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                fechaNacimiento = formato.parse(fechaTexto);
            } catch (Exception e) {
                Toast.makeText(view.getContext(), "Fecha inválida. Usa formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            if (telefono.isEmpty() || !telefono.matches("^\\d{9,}$")) {
                Toast.makeText(view.getContext(), "Ingrese un número de teléfono válido (al menos 9 dígitos).", Toast.LENGTH_SHORT).show();
                return;
            }

            if (correo.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(view.getContext(), "Ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (direccion.isEmpty() || direccion.length() < 5) {
                Toast.makeText(view.getContext(), "Ingrese una dirección válida (mínimo 5 caracteres).", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sexo.equals("Seleccione sexo")) {
                Toast.makeText(view.getContext(), "Seleccione un sexo válido.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto Paciente
            Paciente paciente = new Paciente();
            paciente.setDni(dni);
            paciente.setNombres(nombres);
            paciente.setApellidoPaterno(apellidoPaterno);
            paciente.setApellidoMaterno(apellidoMaterno);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setTelefono(telefono);
            paciente.setCorreo(correo);
            paciente.setDireccion(direccion);
            paciente.setSexo(sexo);
            paciente.setEstadoAuditoria("1");
            paciente.setFechaRegistro(new Date());

            // Insertar paciente
            pacienteViewModel.insertarPaciente(paciente);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Ejecutado metodo onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Ejecutado metodo onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Ejecutado metodo onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Ejecutado metodo onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Ejecutado metodo onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Ejecutado metodo onDestroy()");
    }
}
