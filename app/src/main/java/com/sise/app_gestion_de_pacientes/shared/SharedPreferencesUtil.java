package com.sise.app_gestion_de_pacientes.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static void guardar(Context context, String clave, String valor) {
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(clave, valor);
        editor.apply();
    }

    public static String obtener(Context context, String clave) {
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        return preferences.getString(clave, null);
    }

    public static void eliminar(Context context, String clave) {
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(clave);
        editor.apply();
    }
}
