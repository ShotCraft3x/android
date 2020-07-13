package com.proyectoandroid.Modelo;

public class Usuario {

    private String id;
    private String username;
    private String email;
    private String pass;
    private String pin;

    public Usuario() {
    }

    public Usuario(String username, String email, String pass, String pin) {
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
