package com.example.sistemas.casalinda.entidades;

public class Busca {
    String codigo,nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Busca(String codigo, String nombre) {
        this.codigo=codigo;
        this.nombre=nombre;
    }
    public Busca (){}
}
