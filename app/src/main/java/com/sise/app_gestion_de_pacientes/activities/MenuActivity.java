package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sise.app_gestion_de_pacientes.R;

public class MenuActivity extends AppCompatActivity {

    private final String TAG = MenuActivity.class.getName();
    private TextView greetingTextView; // ← Aquí se mostrará el saludo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Ejecutado metodo onCreate()");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔹 Obtenemos el TextView donde vamos a poner el saludo
        greetingTextView = findViewById(R.id.greeting);

        // 🔹 Recibimos el nombre del usuario desde LoginActivity
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        if (nombreUsuario != null) {
            // 🔹 Cambiamos el texto con el nombre recibido
            greetingTextView.setText("Bienvenido " + nombreUsuario);
        }
    }

    public void onClickPerfilRegistrarPacientes(View view){
        Intent intent = new Intent(this, PerfilRegistrarPacientesActivity.class);
        startActivity(intent);
    }
    public void onClickPerfilRegistrarMedico(View view){
        Intent intent = new Intent(this, PerfilRegistrarMedicoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"Ejecutado metodo onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"Ejecutado metodo onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"Ejecutado metodo onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"Ejecutado metodo onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"Ejecutado metodo onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Ejecutado metodo onDestroy()");
    }
}
