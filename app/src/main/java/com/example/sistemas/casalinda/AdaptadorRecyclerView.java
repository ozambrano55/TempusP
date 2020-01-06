package com.example.sistemas.casalinda;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorRecyclerView  extends RecyclerView.Adapter<ViewHolderPedido>  {
    private List<Pedido> pedidos;
    private InterfazClickRecyclerView interfazClickRecyclerView;
    public Double tot,un;
    public String cod;
    public String totString="0";
    EditText can,to;
    //DecimalFormat formater = new DecimalFormat("#.##");

    public AdaptadorRecyclerView(List <Pedido>pedidos){
        this.pedidos=pedidos;
    }

    public void setPedidos (List<Pedido>pedidos){
        this.pedidos=pedidos;
        this.notifyDataSetChanged();
    }

    public List<Pedido>getPedidos(){
        return pedidos;
    }

    public AdaptadorRecyclerView(InterfazClickRecyclerView interfazClickRecyclerView){
        this.interfazClickRecyclerView=interfazClickRecyclerView;
        this.pedidos=new ArrayList<>();

    }
    public void agregarPedido (Pedido pedido){
        this.pedidos.add(pedido);


        un=Double.parseDouble(pedido.getUnitario()) ;

        this.notifyItemInserted(this.pedidos.size()-1);
         tot=0.00;
        for (Pedido data:pedidos){
            tot+=Double.parseDouble(data.getTotal());
        }

        totString=String.valueOf(tot);

    }
    public void eliminar(int indice){
        if (indice <0 || indice >=this.getItemCount())return;
        this.pedidos.remove(indice);
        this.notifyItemRemoved(indice);
    }
    @NonNull
    @Override

    public ViewHolderPedido onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //Esta es la vista del layout que muestra los detalles del peddio (layout_recycler.xml)
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler,parent,false);
        //Crear el viewholder a partir de esta visa. Mira la calse ViewHolderPedido si quieres
        final ViewHolderPedido viewHolder=new ViewHolderPedido(vista);
        //En el clic de la vista (el pedido en general) invocamos a nuestra interfaz personalizada pasandole la vista y el pedido
        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfazClickRecyclerView.onClick(v,pedidos.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPedido holder, int position){
        //Dibujar la fila del pedido con los datos de pedido actualmente solicitadao segun la variable position
        Pedido pedido= this.pedidos.get(position);
        holder.getTextViewProducto().setText(String.valueOf(pedido.getCodigo()));
        holder.getTextViewNombre().setText(String.valueOf(pedido.getNombre()));
        holder.getTextViewCantidad().setText(String.valueOf(pedido.getCantidad()));
        holder.getTextViewUnitario().setText(String.valueOf(pedido.getUnitario()));
        holder.getTextViewTotal().setText(String.valueOf(pedido.getTotal()));

    /*   can=(EditText)can.findViewById(R.id.edtCantidad);
        to=(EditText)to.findViewById(R.id.edtTotal);
        can.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               canti= Double.parseDouble(String.valueOf(can.getText()));
            }
        });
*/
    }
    @Override
    public int getItemCount(){
            return this.pedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context=v.getContext();
            int position=getAdapterPosition();
            Pedido pedido=pedidos.get(position);

            Intent intent =new Intent(v.getContext(),PedidoEditar.class);
            //intent.putExtra();
        }
    }
}
