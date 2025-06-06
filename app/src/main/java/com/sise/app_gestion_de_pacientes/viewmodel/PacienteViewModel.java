package com.sise.app_gestion_de_pacientes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.app_gestion_de_pacientes.entities.Paciente;
import com.sise.app_gestion_de_pacientes.repositories.PacienteRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;

public class PacienteViewModel extends ViewModel {
    private MutableLiveData<Boolean> insertarPacienteStatus;
    private PacienteRepository pacienteRepository;

    public PacienteViewModel() {
        insertarPacienteStatus = new MutableLiveData<>();
        pacienteRepository = new PacienteRepository();
    }

    public LiveData<Boolean> getInsertarPacienteStatus() {
        return insertarPacienteStatus;
    }

    public void insertarPaciente(Paciente paciente) {
        pacienteRepository.insertarPaciente(paciente, new Callback<Paciente>() {
            @Override
            public void onSuccess(Paciente result) {
                insertarPacienteStatus.setValue(true);
            }

            @Override
            public void onFailure() {
                insertarPacienteStatus.setValue(false);
            }
        });
    }
}
