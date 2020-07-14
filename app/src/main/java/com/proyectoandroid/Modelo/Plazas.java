package com.proyectoandroid.Modelo;

public class Plazas {

    private String nombre;
    private String descripcion;
    private String imagen;
    private String latitud;
    private String longitud;
    private String iddocumento;
    private int ID_TipoLugar;

    public Plazas() {
    }

    public Plazas(String nombre, String descripcion, String imagen, String latitud, String longitud, String iddocumento, int ID_TipoLugar) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.iddocumento = iddocumento;
        this.ID_TipoLugar = ID_TipoLugar;
    }

    public int getID_TipoLugar() {
        return ID_TipoLugar;
    }

    public void setID_TipoLugar(int ID_TipoLugar) {
        this.ID_TipoLugar = ID_TipoLugar;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }
}
