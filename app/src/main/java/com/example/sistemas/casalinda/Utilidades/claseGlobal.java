package com.example.sistemas.casalinda.Utilidades;

import android.app.Application;

public class claseGlobal extends Application {
    private String c_punto_venta;
    private String c_funcionario;
    private String c_bodega;

    private String funcionario;
    private String punto;

    private String codigo;
    private Double cantidad;
    private Double unitario;
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
}
