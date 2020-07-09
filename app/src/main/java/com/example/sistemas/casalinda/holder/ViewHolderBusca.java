package com.example.sistemas.casalinda.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.R;

public class ViewHolderBusca extends RecyclerView.ViewHolder {
    private TextView codigo, nombre,cantidad;

    public TextView getCodigo() {
        return codigo;
    }

    public TextView getNombre() {
        return nombre;
    }
    public TextView getCantidad(){return cantidad;}

    public ViewHolderBusca(View iteemView){
        super(iteemView);
        codigo=iteemView.findViewById(R.id.txtCodigo);
        nombre =iteemView.findViewById(R.id.txtNombre);
        cantidad=iteemView.findViewById(R.id.txtCantidad);
    }
}
