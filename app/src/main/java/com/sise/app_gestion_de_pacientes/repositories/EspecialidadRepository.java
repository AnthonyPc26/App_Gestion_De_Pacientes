package com.sise.app_gestion_de_pacientes.repositories;

import com.sise.app_gestion_de_pacientes.api.ApiService;
import com.sise.app_gestion_de_pacientes.entities.Especialidad;
import com.sise.app_gestion_de_pacientes.shared.ApiClient;
import com.sise.app_gestion_de_pacientes.shared.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspecialidadRepository {

    public interface EspecialidadCallback {
        void onSuccess(List<Especialidad> lista);
        void onError(String mensajeError);
    }

    public static void obtenerEspecialidades(EspecialidadCallback callback) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<BaseResponse<List<Especialidad>>> call = apiService.getEspecialidades();

        call.enqueue(new Callback<BaseResponse<List<Especialidad>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Especialidad>>> call, Response<BaseResponse<List<Especialidad>>> response) {
                if (response.isSuccessful() && response.body() != null && Boolean.TRUE.equals(response.body().getSuccess())) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Error en la respuesta del servidor: " +
                            (response.body() != null ? response.body().getMessage() : "Sin mensaje"));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Especialidad>>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}
