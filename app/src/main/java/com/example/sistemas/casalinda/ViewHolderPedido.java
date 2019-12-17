package com.example.sistemas.casalinda;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

class ViewHolderPedido extends RecyclerView.ViewHolder
{
   private EditText editTextCodigo, editTextCantidad,editTextUnitario, editTextTotal;

   ViewHolderPedido(@NonNull View itemView){
       super(itemView);
       editTextCodigo=itemView.findViewById(R.id.edtCodigo);
       editTextCantidad=itemView.findViewById(R.id.edtCantidad);
       editTextUnitario=itemView.findViewById(R.id.edtUnitario);
       editTextTotal=itemView.findViewById(R.id.edtTotal);
   }

    public TextView getTextViewProducto() {
        return editTextCodigo;
    }

    public TextView getTextViewCantidad() {
        return editTextCantidad;
    }

    public TextView getTextViewUnitario() {
        return editTextUnitario;
    }

    public TextView getTextViewTotal() {
        return editTextTotal;
    }
}
