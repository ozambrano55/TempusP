package com.example.sistemas.casalinda.entidades;

public class Busca {
    private String codigo;
    private String nombre;
    private String cantidad;
    private String bodega;

    public Busca(String codigo, String nombre, String cantidad, String bodega) {
        this.codigo=codigo;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.bodega=bodega;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCantidad(){return cantidad;}
    public String getBodega(){return bodega;}

    public Busca (){}
    @Override
    public String toString(){
        return "Busca{" +
                "codigo='" + codigo + '\'' +
                "nombre='" + nombre + '\'' +
                "cantidad='" + cantidad + '\'' +
                ", bodega=" + bodega +
                '}';
    }
}

