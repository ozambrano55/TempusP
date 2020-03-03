package com.example.sistemas.casalinda.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.R;

public class ViewHolderPedido extends RecyclerView.ViewHolder
{
   private TextView txtCodigo, txtNombre, txtCantidad,txtUnitario, txtTotal;

   public ViewHolderPedido(@NonNull View itemView){
       super(itemView);
       txtCodigo=itemView.findViewById(R.id.txtCodigo);
       txtNombre=itemView.findViewById(R.id.txtNombre);
       txtCantidad=itemView.findViewById(R.id.txtCantidad);
       txtUnitario=itemView.findViewById(R.id.txtUnitario);
       txtTotal=itemView.findViewById(R.id.txtTotal);
   }

    public TextView getTextViewProducto() {
        return txtCodigo;
    }
    public TextView getTextViewNombre(){
       return txtNombre;
    }

    public TextView getTextViewCantidad() {
        return txtCantidad;
    }

    public TextView getTextViewUnitario() {
        return txtUnitario;
    }

    public TextView getTextViewTotal() {
        return txtTotal;
    }


}
