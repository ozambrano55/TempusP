package com.example.sistemas.casalinda;

public class Precio {
    private String tipo;
    private Double precio;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Precio(String tipo, Double Precio){
        this.tipo=tipo;
        this.precio=precio;
    }
    public Precio(){}
}
