package com.example.sistemas.casalinda.entidades;

public class Busca {
    private String codigo;
    private String nombre;
    private String cantidad;

    public Busca(String codigo, String nombre, String cantidad) {
        this.codigo=codigo;
        this.nombre=nombre;
        this.cantidad=cantidad;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCantidad(){return cantidad;}

    public Busca (){}
    @Override
    public String toString(){
        return "Busca{" +
                "codigo='" + codigo + '\'' +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}

