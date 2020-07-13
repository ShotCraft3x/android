package com.proyectoandroid.Modelo;

public class Retos {

    private String nombre;
    private String descripcion;
    private int id_tipobeneficio;
    private int id_tiposeguridad;

    public Retos() {
    }

    public Retos(String nombre, String descripcion, int id_tipobeneficio, int id_tiposeguridad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_tipobeneficio = id_tipobeneficio;
        this.id_tiposeguridad = id_tiposeguridad;
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

    public int getId_tipobeneficio() {
        return id_tipobeneficio;
    }

    public void setId_tipobeneficio(int id_tipobeneficio) {
        this.id_tipobeneficio = id_tipobeneficio;
    }

    public int getId_tiposeguridad() {
        return id_tiposeguridad;
    }

    public void setId_tiposeguridad(int id_tiposeguridad) {
        this.id_tiposeguridad = id_tiposeguridad;
    }
}
