package com.sise.app_gestion_de_pacientes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.app_gestion_de_pacientes.entities.Especialidad;
import com.sise.app_gestion_de_pacientes.repositories.EspecialidadRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EspecialidadViewModel extends ViewModel {
    private MutableLiveData<List<Especialidad>> especialidades = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<List<Especialidad>> getEspecialidades() {
        return especialidades;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void cargarEspecialidades() {
        EspecialidadRepository.obtenerEspecialidades(new EspecialidadRepository.EspecialidadCallback() {
            @Override
            public void onSuccess(List<Especialidad> lista) {
                especialidades.postValue(lista);
            }

            @Override
            public void onError(String mensajeError) {
                error.postValue(mensajeError);
            }
        });
    }
}