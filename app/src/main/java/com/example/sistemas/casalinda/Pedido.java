package com.example.sistemas.casalinda;

public class Pedido {
    private String codigo, nombre, cantidad,unitario, total;

    public Pedido(String codigo, String nombre, String cantidad, String unitario, String total){
        this.codigo=codigo;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.unitario=unitario;
        this.total=total;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getUnitario() {
        return unitario;
    }

    public String getTotal() {
        return total;
    }
    @Override
    public  String toString (){
        return "Pedido{" +
                "producto='" + codigo + '\'' +
                ", nombre=" + nombre + '\'' +
                ", cantidad=" + cantidad + '\'' +
                ", unitario=" + unitario + '\'' +
                ", total=" + total +
                '}';
    }
}
