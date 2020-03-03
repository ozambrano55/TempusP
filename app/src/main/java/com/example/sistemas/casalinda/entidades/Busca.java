package com.example.sistemas.casalinda.entidades;

public class Busca {
    private String codigo;
    private String nombre;

    public Busca(String codigo, String nombre) {
        this.codigo=codigo;
        this.nombre=nombre;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }

    public Busca (){}
@Override
    public String toString(){
    return "Busca{" +
            "codigo='" + codigo + '\'' +
            ", nombre=" + nombre +
            '}';
    }
}

