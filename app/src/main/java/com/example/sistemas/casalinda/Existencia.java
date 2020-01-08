package com.example.sistemas.casalinda;

import android.media.MediaRouter;

public class Existencia {
    private String c_bodega;
    private String n_bodega;
    private String existencia;
    private String ubicacion;

    public String getC_bodega() {
        return c_bodega;
    }

    public void setC_bodega(String c_bodega) {
        this.c_bodega = c_bodega;
    }

    public String getN_bodega() {
        return n_bodega;
    }

    public void setN_bodega(String n_bodega) {
        this.n_bodega = n_bodega;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Existencia (String c_bodega, String n_bodega, String existencia, String ubicacion){
        this.c_bodega=c_bodega;
        this.n_bodega=n_bodega;
        this.existencia=existencia;
        this.ubicacion=ubicacion;

    }
    public Existencia(){}
}
