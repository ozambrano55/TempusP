package com.example.sistemas.casalinda.entidades;

public class Punto {
    private String c_punto;
    private String punto;

    public String getC_punto() {
        return c_punto;
    }

    public void setC_punto(String c_punto) {
        this.c_punto = c_punto;
    }

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }

    public Punto(String c_punto, String punto){
        this.c_punto=c_punto;
        this.punto=punto;

    }
    public Punto(){}

}
