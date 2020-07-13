package com.proyectoandroid.Modelo;

public class MaquinasEjercicios {

    private int ID_RutaRecomendada;
    private String nombre;
    private String descripcion;

    public MaquinasEjercicios(int ID_RutaRecomendada, String nombre, String descripcion) {
        this.ID_RutaRecomendada = ID_RutaRecomendada;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getID_RutaRecomendada() {
        return ID_RutaRecomendada;
    }

    public void setID_RutaRecomendada(int ID_RutaRecomendada) {
        this.ID_RutaRecomendada = ID_RutaRecomendada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
