package com.example.sistemas.casalinda;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderBusca extends RecyclerView.ViewHolder {
    private TextView codigo, nombre;

    public ViewHolderBusca( View iteemView){
        super(iteemView);
        codigo=iteemView.findViewById(R.id.txtCodigo);
    }
}
