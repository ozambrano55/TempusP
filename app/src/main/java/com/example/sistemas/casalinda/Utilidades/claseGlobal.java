package com.example.sistemas.casalinda.Utilidades;

import android.app.Application;

public class claseGlobal extends Application {
    private String c_punto_venta;
    private String c_funcionario;
    private String c_bodega;
    private String cod_pedidos;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCod_pedidos() {
        return cod_pedidos;
    }

    public void setCod_pedidos(String cod_pedidos) {
        this.cod_pedidos = cod_pedidos;
    }

    private String funcionario;
    private String punto;
    private Integer pos;

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    private String tip;
    private String col;
    private String codigo;
    private String nombre;
    private Double cantidad;
    private Double unitario;
    private Double total;
    private Double pvp;
    private String cuv;

    public Double getInventario() {
        return inventario;
    }

    public void setInventario(Double inventario) {
        this.inventario = inventario;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public Double getPvp() {
        return pvp;
    }

    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    public String getCuv() {
        return cuv;
    }

    public void setCuv(String cuv) {
        this.cuv = cuv;
    }

    private Double inventario;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    private Double proforma;

    public Double getProforma() {
        return proforma;
    }

    public void setProforma(Double proforma) {
        this.proforma = proforma;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getUnitario() {
        return unitario;
    }

    public void setUnitario(Double unitario) {
        this.unitario = unitario;
    }

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getC_bodega() {
        return c_bodega;
    }

    public void setC_bodega(String c_bodega) {
        this.c_bodega = c_bodega;
    }

    public String getC_punto_venta() {
        return c_punto_venta;
    }

    public void setC_punto_venta(String c_punto_venta) {
        this.c_punto_venta = c_punto_venta;
    }

    public String getC_funcionario() {
        return c_funcionario;
    }

    public void setC_funcionario(String c_funcionario) {
        this.c_funcionario = c_funcionario;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
