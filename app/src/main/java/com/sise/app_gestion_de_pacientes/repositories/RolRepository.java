package com.sise.app_gestion_de_pacientes.repositories;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.app_gestion_de_pacientes.entities.Rol;
import com.sise.app_gestion_de_pacientes.shared.BaseResponse;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.shared.HttpUtil;
import com.sise.app_gestion_de_pacientes.shared.Constants;

import java.lang.reflect.Type;
import java.util.List;

public class RolRepository {

    public void obtenerRoles(Callback<List<Rol>> callback) {
        new Thread(() -> {
            try {
                String response = HttpUtil.GET(Constants.BASE_URL_API,"/roles");

                if (response == null) {
                    callback.onFailure();
                    return;
                }

                Type type = TypeToken.getParameterized(BaseResponse.class, TypeToken.getParameterized(List.class, Rol.class).getType()).getType();
                BaseResponse<List<Rol>> baseResponse = new Gson().fromJson(response, type);

                if (baseResponse == null || !baseResponse.isSuccess()) {
                    callback.onFailure();
                    return;
                }

                callback.onSuccess(baseResponse.getData());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFailure();
            }
        }).start();
    }
}
