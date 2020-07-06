package com.proyectoandroid.fragments;

public class PlazaDetFragment {
    int image;
    String name;
    String descrip;

    public PlazaDetFragment(int image, String name, String descrip) {
        this.image = image;
        this.name = name;
        this.descrip= descrip;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescrip() {
        return descrip;
    }
}