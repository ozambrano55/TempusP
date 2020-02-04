package com.example.sistemas.casalinda;

public class TipoId {
    String c_tipo_identi, d_tipo_identi;

    public String getC_tipo_identi() {
        return c_tipo_identi;
    }

    public void setC_tipo_identi(String c_tipo_identi) {
        this.c_tipo_identi = c_tipo_identi;
    }

    public String getD_tipo_identi() {
        return d_tipo_identi;
    }

    public void setD_tipo_identi(String d_tipo_identi) {
        this.d_tipo_identi = d_tipo_identi;
    }
    public TipoId (String c_tipo_identi, String d_tipo_identi){
        this.c_tipo_identi=c_tipo_identi;
        this.d_tipo_identi=d_tipo_identi;
    }
    public TipoId(){}
}
