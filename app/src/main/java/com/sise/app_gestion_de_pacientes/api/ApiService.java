package com.sise.app_gestion_de_pacientes.api;

import com.sise.app_gestion_de_pacientes.entities.Especialidad;
import com.sise.app_gestion_de_pacientes.shared.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("especialidades")
    Call<BaseResponse<List<Especialidad>>> getEspecialidades();

}
