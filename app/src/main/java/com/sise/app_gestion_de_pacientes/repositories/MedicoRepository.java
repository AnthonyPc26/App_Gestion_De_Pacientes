package com.sise.app_gestion_de_pacientes.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.app_gestion_de_pacientes.entities.Medico;
import com.sise.app_gestion_de_pacientes.shared.BaseResponse;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.shared.Constants;
import com.sise.app_gestion_de_pacientes.shared.HttpUtil;

public class MedicoRepository {
    public void insertarMedico(Medico medico, Callback<Medico> callback) {
        try {
            String response = HttpUtil.POST(Constants.BASE_URL_API, "/medicos", new Gson().toJson(medico));
            if (response == null) {
                callback.onFailure();
                return;
            }
            BaseResponse<Medico> baseResponse = new Gson().fromJson(
                    response,
                    TypeToken.getParameterized(BaseResponse.class, Medico.class).getType()
            );
            if (baseResponse == null || !baseResponse.isSuccess()) {
                callback.onFailure();
                return;
            }
            if (!baseResponse.isSuccess()) {
                callback.onFailure();
                return;
            }

            callback.onSuccess(baseResponse.getData());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure();
        }
    }
}
