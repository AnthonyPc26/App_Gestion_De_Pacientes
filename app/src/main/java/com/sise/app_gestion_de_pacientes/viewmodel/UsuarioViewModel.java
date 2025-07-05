package com.sise.app_gestion_de_pacientes.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel;

import com.sise.app_gestion_de_pacientes.entities.Usuario;
import com.sise.app_gestion_de_pacientes.repositories.UsuarioRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsuarioViewModel extends AndroidViewModel {

    private final UsuarioRepository usuarioRepository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Usuario> usuarioLoginLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> insertarUsuarioStatus = new MutableLiveData<>();
    private final MutableLiveData<Usuario> usuarioValidadoLiveData = new MutableLiveData<>();

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository();
    }

    public LiveData<Usuario> getUsuarioLoginLiveData() {
        return usuarioLoginLiveData;
    }

    public LiveData<Boolean> getInsertarUsuarioStatus() {
        return insertarUsuarioStatus;
    }

    public LiveData<Usuario> getUsuarioValidadoLiveData() {
        return usuarioValidadoLiveData;
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
            usuarioRepository.loginUsuario(usuario, clave, rol, getApplication(), new Callback<Usuario>() {
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

    public void validarUsuarioLogueado() {
        executorService.execute(() -> {
            usuarioRepository.validarUsuarioLogueado(getApplication(), new Callback<Usuario>() {
                @Override
                public void onSuccess(Usuario result) {
                    usuarioValidadoLiveData.postValue(result);
                }

                @Override
                public void onFailure() {
                    usuarioValidadoLiveData.postValue(null);
                }
            });
        });
    }
}

