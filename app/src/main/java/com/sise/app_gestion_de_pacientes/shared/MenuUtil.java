package com.sise.app_gestion_de_pacientes.shared;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.sise.app_gestion_de_pacientes.R;
import com.sise.app_gestion_de_pacientes.activities.LoginActivity;

public class MenuUtil {

    public static boolean onClickMenuItem(Activity activity, MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            SharedPreferencesUtil.eliminar(activity, Constants.SHARED_PREFERENCES_USUARIO_LOGUEADO);
            Toast.makeText(activity, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();

            return true;
        } else {
            Toast.makeText(activity, "Opción aún no disponible", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
