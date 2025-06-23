package com.sise.app_gestion_de_pacientes.entities;
import com.google.gson.annotations.SerializedName;

public class Especialidad {

    @SerializedName("idEspecialidad")
    private int idEspecialidad;

    @SerializedName("nombreEspecialidad")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("estadoAuditoria")
    private String estadoAuditoria;

    @SerializedName("fechaRegistro")
    private String fechaRegistro;

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public String toString() {
        return nombre; // para mostrar en el Spinner
    }
}
