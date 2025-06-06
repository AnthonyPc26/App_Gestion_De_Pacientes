package com.sise.app_gestion_de_pacientes.shared;

public interface Callback<T> {
    void onSuccess(T result);
    void onFailure();
}