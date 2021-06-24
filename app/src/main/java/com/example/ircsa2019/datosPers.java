package com.example.ircsa2019;

public class datosPers {

    String tipPers,nombre, apePat,apeMat, rfc, curp, fecNac, calle, colonia,municipio,estado,numero,cod;

  public datosPers(){

    }
    //solo para nombre y direccion
    public datosPers(String tipPers, String nombre, String apePat, String apeMat, String calle, String colonia, String municipio, String estado) {
        this.tipPers = tipPers;
        this.nombre = nombre;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.calle = calle;
        this.colonia = colonia;
        this.municipio = municipio;
        this.estado = estado;
    }

    public String getTipPers() {
        return tipPers;
    }

    public void setTipPers(String tipPers) {
        this.tipPers = tipPers;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   /* public datosPers(String tipPers, String nombre, String apePat, String apeMat, String rfc, String curp, String fecNac, String calle, String colonia, String municipio, String estado, String numero, String cod) {
        this.tipPers = tipPers;
        this.nombre = nombre;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.rfc = rfc;
        this.curp = curp;
        this.fecNac = fecNac;
        this.calle = calle;
        this.colonia = colonia;
        this.municipio = municipio;
        this.estado = estado;
        this.numero = numero;
        this.cod = cod;
    }*/


}
