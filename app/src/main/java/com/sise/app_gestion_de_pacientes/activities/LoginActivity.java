package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sise.app_gestion_de_pacientes.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etContrasena;
    private Button btnLogin;
    private TextView togglePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Referencias a los elementos de la vista
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        togglePassword = findViewById(R.id.togglePassword); // ← ojito

        // Acción al hacer clic en el botón de login
        btnLogin.setOnClickListener(view -> validarLogin());

        // Acción para mostrar u ocultar contraseña
        togglePassword.setOnClickListener(v -> {
            if (etContrasena.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etContrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setText("🙈");
            } else {
                etContrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setText("👁️");
            }
            etContrasena.setSelection(etContrasena.getText().length()); // mantén el cursor al final
        });

        // Ajustes visuales para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

        // Validación simple
        if ((usuario.equals("admin") && contrasena.equals("123456")) ||
                (usuario.equals("doctor.diaz") && contrasena.equals("bts123"))) {

            Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("nombreUsuario", usuario); // Enviar el nombre
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    // Este método ya no se necesita, puedes borrarlo si quieres
    public void onClickMenuActivity(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        EditText etUsuario = findViewById(R.id.etUsuario);
        String nombreUsuario = etUsuario.getText().toString();
        intent.putExtra("nombre_usuario", nombreUsuario);
        startActivity(intent);
    }
}
