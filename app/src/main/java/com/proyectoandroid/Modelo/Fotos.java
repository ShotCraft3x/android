package com.proyectoandroid.Modelo;

public class Fotos {

    private String titulo;
    private String foto;
    private String descripcion;
    private String idfoto;

    public Fotos() {
    }


    public Fotos(String titulo, String foto, String descripcion, String idfoto) {
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
        this.idfoto = idfoto;
    }

    public String getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(String idfoto) {
        this.idfoto = idfoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
