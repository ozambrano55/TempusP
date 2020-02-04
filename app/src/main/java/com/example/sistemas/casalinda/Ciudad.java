package com.example.sistemas.casalinda;

public class Ciudad {
    String  c_ciudad,n_ciudad;



    public String getC_ciudad() {
        return c_ciudad;
    }

    public void setC_ciudad(String c_ciudad) {
        this.c_ciudad = c_ciudad;
    }

    public String getN_ciudad() {
        return n_ciudad;
    }

    public void setN_ciudad(String n_ciudad) {
        this.n_ciudad = n_ciudad;
    }


    public Ciudad ( String c_Ciudad,String n_Ciudad){

        this.c_ciudad=c_Ciudad;
        this.n_ciudad=n_Ciudad;
    }

    public  Ciudad (){}
}
