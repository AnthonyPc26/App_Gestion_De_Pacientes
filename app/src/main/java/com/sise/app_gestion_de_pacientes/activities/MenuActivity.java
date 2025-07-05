package com.sise.app_gestion_de_pacientes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.shared.Constants;
import com.sise.app_gestion_de_pacientes.shared.SharedPreferencesUtil;

public class MenuActivity extends AppCompatActivity {

    private final String TAG = MenuActivity.class.getName();
    private TextView greetingTextView; // ← Aquí se mostrará el saludo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Ejecutado metodo onCreate()");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        greetingTextView = findViewById(R.id.greeting);

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        if (nombreUsuario != null) {
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            SharedPreferencesUtil.eliminar(this, Constants.SHARED_PREFERENCES_USUARIO_LOGUEADO);
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return com.sise.app_gestion_de_pacientes.shared.MenuUtil.onClickMenuItem(this, item)
                || super.onOptionsItemSelected(item);
    }

}
