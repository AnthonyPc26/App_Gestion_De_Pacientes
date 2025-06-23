package com.sise.app_gestion_de_pacientes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.app_gestion_de_pacientes.entities.Medico;
import com.sise.app_gestion_de_pacientes.repositories.MedicoRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MedicoViewModel extends ViewModel {

    private final MutableLiveData<Boolean> insertarMedicoStatus = new MutableLiveData<>();
    private final MedicoRepository medicoRepository = new MedicoRepository();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<Boolean> getInsertarMedicoStatus() {
        return insertarMedicoStatus;
    }

    public void insertarMedico(Medico medico) {
        executorService.execute(() -> {
            medicoRepository.insertarMedico(medico, new Callback<Medico>() {
                @Override
                public void onSuccess(Medico result) {
                    insertarMedicoStatus.postValue(true);
                }

                @Override
                public void onFailure() {
                    insertarMedicoStatus.postValue(false);
                }
            });
        });
    }
}
