package com.sise.app_gestion_de_pacientes.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sise.app_gestion_de_pacientes.entities.Usuario;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.shared.Constants;
import com.sise.app_gestion_de_pacientes.shared.HttpUtil;
import com.sise.app_gestion_de_pacientes.shared.BaseResponse;
import android.content.Context;
import com.sise.app_gestion_de_pacientes.shared.SharedPreferencesUtil;

public class UsuarioRepository {

    public void insertarUsuario(Usuario usuario, Callback<Usuario> callback) {
        try {
            String response = HttpUtil.POST(Constants.BASE_URL_API, "/usuarios", new Gson().toJson(usuario));
            if (response == null) {
                callback.onFailure();
                return;
            }
            BaseResponse<Usuario> baseResponse = new Gson().fromJson(
                    response,
                    TypeToken.getParameterized(BaseResponse.class, Usuario.class).getType()
            );
            if (baseResponse == null || !baseResponse.isSuccess()) {
                callback.onFailure();
                return;
            }
            callback.onSuccess(baseResponse.getData());

        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure();
        }
    }
    public void loginUsuario(String usuario, String password, String rol, Context context, Callback<Usuario> callback) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("usuario", usuario);
            json.addProperty("password", password);
            json.addProperty("rol", rol);
            String response = HttpUtil.POST(Constants.BASE_URL_API, "/usuarios/login", json.toString());

            if (response == null) {
                callback.onFailure();
                return;
            }
            BaseResponse<Usuario> baseResponse = new Gson().fromJson(
                    response,
                    TypeToken.getParameterized(BaseResponse.class, Usuario.class).getType()
            );
            if (!baseResponse.isSuccess()) {
                callback.onFailure();
                return;
            }

            Usuario usuarioObj = baseResponse.getData();
            guardarUsuarioLogueado(context, usuarioObj);
            callback.onSuccess(usuarioObj);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure();
        }
    }

    public void guardarUsuarioLogueado(Context context, Usuario usuario) {
        SharedPreferencesUtil.guardar(context, Constants.SHARED_PREFERENCES_USUARIO_LOGUEADO, new Gson().toJson(usuario));
    }

    public void validarUsuarioLogueado(Context context, Callback<Usuario> callback) {
        try {
            String json = SharedPreferencesUtil.obtener(context, Constants.SHARED_PREFERENCES_USUARIO_LOGUEADO);
            if (json == null) {
                callback.onFailure();
                return;
            }
            Usuario usuario = new Gson().fromJson(json, Usuario.class);
            callback.onSuccess(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure();
        }
    }
    public void logoutUsuario(Context context) {
        SharedPreferencesUtil.eliminar(context, Constants.SHARED_PREFERENCES_USUARIO_LOGUEADO);
    }
}
