package com.example.sistemas.casalinda;

public class Pedido {
    private String codigo, cantidad,unitario, total;

    public Pedido(String codigo, String cantidad, String unitario, String total){
        this.codigo=codigo;
        this.cantidad=cantidad;
        this.unitario=unitario;
        this.total=total;
    }

    public String getCodigo() {
        return codigo;
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
                ", cantidad=" + cantidad + '\'' +
                ", unitario=" + unitario + '\'' +
                ", total=" + total +
                '}';
    }
}
