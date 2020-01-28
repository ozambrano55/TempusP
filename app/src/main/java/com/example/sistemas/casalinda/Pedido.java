package com.example.sistemas.casalinda;

public class Pedido {
    private String tipo, color, codigo, nombre, cantidad,unitario, total,pvp, cuv, bod,pon;


    public String getPon() {
        return pon;
    }

    public Pedido(String tipo, String color, String  codigo, String nombre, String cantidad, String unitario, String total, String pvp, String cuv, String bod, String pon){
        this.tipo=tipo;
        this.color=color;
        this.codigo=codigo;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.unitario=unitario;
        this.total=total;
        this.pvp=pvp;
        this.cuv=cuv;
        this.bod=bod;
        this.pon=pon;
    }

    public String getTipo() {
        return tipo;
    }

    public String getBod() {
        return bod;
    }

    public String getColor() {
        return color;
    }

    public String getPvp() {
        return pvp;
    }

    public String getCuv() {
        return cuv;
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
                "tipo='" + tipo + '\'' +
                "color='" + color + '\'' +
                "producto='" + codigo + '\'' +
                ", nombre=" + nombre + '\'' +
                ", cantidad=" + cantidad + '\'' +
                ", unitario=" + unitario + '\'' +
                ", total=" + total + '\'' +
                ", pvp=" + pvp + '\'' +
                ", cuv=" + cuv + '\'' +
                ", cuv=" + bod + '\'' +
                ", bod=" + pon +
                '}';
    }
}
