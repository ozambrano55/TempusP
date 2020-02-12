package com.example.sistemas.casalinda.entidades;

public class Precio {
    private String tipo;
    private String precio;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Precio(String tipo, String precio){
        this.tipo=tipo;
        this.precio=precio;
    }
    public Precio(){}
}
