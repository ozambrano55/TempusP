package com.example.sistemas.casalinda;

public class Provincia {
    private String c_region,n_region;

    public String getC_region() {
        return c_region;
    }

    public void setC_region(String c_region) {
        this.c_region = c_region;
    }

    public String getN_region() {
        return n_region;
    }

    public void setN_region(String n_region) {
        this.n_region = n_region;
    }
    public Provincia (String c_region,String n_region){this.c_region=c_region;this.n_region=n_region;}
    public Provincia(){}
}
