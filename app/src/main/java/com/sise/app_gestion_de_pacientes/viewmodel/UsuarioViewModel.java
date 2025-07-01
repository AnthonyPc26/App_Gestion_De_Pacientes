package com.sise.app_gestion_de_pacientes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.app_gestion_de_pacientes.entities.Usuario;
import com.sise.app_gestion_de_pacientes.repositories.UsuarioRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsuarioViewModel extends ViewModel {

    private final MutableLiveData<Boolean> insertarUsuarioStatus = new MutableLiveData<>();
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Usuario> usuarioLoginLiveData = new MutableLiveData<>();
    public LiveData<Usuario> getUsuarioLoginLiveData() {
        return usuarioLoginLiveData;
    }
    public LiveData<Boolean> getInsertarUsuarioStatus() {
        return insertarUsuarioStatus;
    }

    public void insertarUsuario(Usuario usuario) {
        executorService.execute(() -> {
            usuarioRepository.insertarUsuario(usuario, new Callback<Usuario>() {
                @Override
                public void onSuccess(Usuario result) {
                    insertarUsuarioStatus.postValue(true);
                }

                @Override
                public void onFailure() {
                    insertarUsuarioStatus.postValue(false);
                }
            });
        });
    }

    public void loginUsuario(String usuario, String clave, String rol) {
        executorService.execute(() -> {
            usuarioRepository.loginUsuario(usuario, clave, rol, new Callback<Usuario>() {
                @Override
                public void onSuccess(Usuario result) {
                    usuarioLoginLiveData.postValue(result);
                }

                @Override
                public void onFailure() {
                    usuarioLoginLiveData.postValue(null);
                }
            });
        });
    }
}
