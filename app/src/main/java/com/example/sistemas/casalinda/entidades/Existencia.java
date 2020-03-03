package com.example.sistemas.casalinda.entidades;

public class Existencia {
    private String c_bodega;
    private String n_bodega;
    private String existencia;
    private String ubicacion;

    public Existencia (String c_bodega, String n_bodega, String existencia, String ubicacion){
        this.c_bodega=c_bodega;
        this.n_bodega=n_bodega;
        this.existencia=existencia;
        this.ubicacion=ubicacion;

    }
    public String getC_bodega() {
        return c_bodega;
    }
    public String getN_bodega() {
        return n_bodega;
    }
    public String getExistencia() {
        return existencia;
    }
    public String getUbicacion() {
        return ubicacion;
    }


    public Existencia(){}

    @Override
    public  String toString (){
        return "Existencia{" +
                "c_bodega='" + c_bodega + '\'' +
                ", n_bodega=" + n_bodega + '\'' +
                ", existencia=" + existencia + '\'' +
                ", ubicacion=" + ubicacion +
                '}';
    }
}
