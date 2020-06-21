package com.proyectoandroid.safety;

public class Contactos {

    private String nombre;
    private String numero;

    public Contactos() {
    }

    public Contactos(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public Contactos(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
