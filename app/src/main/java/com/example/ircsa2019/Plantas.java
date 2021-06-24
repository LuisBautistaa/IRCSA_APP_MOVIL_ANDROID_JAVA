package com.example.ircsa2019;

public class Plantas {
    private String nombre, caracteristicas, cuidados;


    public Plantas(){

    }

    public Plantas(String nombre, String caracteristicas, String cuidados){
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
        this.cuidados = cuidados;

     }

    public  String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public  String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public  String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }
}
