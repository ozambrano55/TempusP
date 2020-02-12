package com.example.sistemas.casalinda.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.R;

public class ViewHolderExistencia extends RecyclerView.ViewHolder {
    private TextView tvBodega, tvNombreB,tvExistencia,tvUbicacion;

    public ViewHolderExistencia(@NonNull View itemView){
        super(itemView);
        tvBodega=itemView.findViewById(R.id.tvBodega);
        tvNombreB=itemView.findViewById(R.id.tvNombre);
        tvExistencia=itemView.findViewById(R.id.tvExistencia);
        tvUbicacion=itemView.findViewById(R.id.tvUbicacion);

    }

    public TextView getTvBodega() {
        return tvBodega;
    }

    public TextView getTvNombreB() {
        return tvNombreB;
    }

    public TextView getTvExistencia() {
        return tvExistencia;
    }

    public TextView getTvUbicacion() {
        return tvUbicacion;
    }
}
