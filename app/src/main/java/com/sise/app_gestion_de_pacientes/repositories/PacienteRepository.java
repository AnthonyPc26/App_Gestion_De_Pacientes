package com.sise.app_gestion_de_pacientes.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.app_gestion_de_pacientes.entities.Paciente;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.shared.Constants;
import com.sise.app_gestion_de_pacientes.shared.HttpUtil;
import com.sise.app_gestion_de_pacientes.shared.BaseResponse;

public class PacienteRepository {

    public void insertarPaciente(Paciente paciente, Callback<Paciente> callback) {
        try {
            // Hace peticion enviando el objeto paciente como String, y devuelve la respuesta del API en string
            String response = HttpUtil.POST(Constants.BASE_URL_API, "/pacientes", new Gson().toJson(paciente));
            if (response == null) {
                callback.onFailure();
                return;
            }
            // Convertir el response String en el objeto BaseResponse<Paciente>
            BaseResponse<Paciente> baseResponse = new Gson().fromJson(
                    response,
                    TypeToken.getParameterized(BaseResponse.class, Paciente.class).getType()
            );
            if (baseResponse == null) {
                callback.onFailure();
                return;
            }

            if (!baseResponse.isSuccess()) {
                callback.onFailure();
                return;
            }
            callback.onSuccess(baseResponse.getData());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            callback.onFailure();
        }
    }
}
