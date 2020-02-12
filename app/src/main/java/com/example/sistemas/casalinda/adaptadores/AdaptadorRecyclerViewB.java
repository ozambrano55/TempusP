package com.example.sistemas.casalinda.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.R;
import com.example.sistemas.casalinda.Utilidades.claseGlobal;
import com.example.sistemas.casalinda.entidades.Busca;
import com.example.sistemas.casalinda.holder.ViewHolderBusca;
import com.example.sistemas.casalinda.interfaz.InterfazClickRecyclerViewB;

import java.util.List;

public class AdaptadorRecyclerViewB extends  RecyclerView.Adapter<ViewHolderBusca> {
    private List <Busca>buscar;
    private InterfazClickRecyclerViewB interfazClickRecyclerViewB;
    public  List <Busca>getBusca(){return buscar;}

    public void agregarBusca(Busca busca){
        this.buscar.add(busca);
        this.notifyItemInserted(this.buscar.size());
    }

    @NonNull
    @Override
    public ViewHolderBusca onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_recyclerb,parent,false);
        final ViewHolderBusca viewHolder=new ViewHolderBusca(vista);
        vista.setOnClickListener(v ->  {

                interfazClickRecyclerViewB.onClick(v,buscar.get(viewHolder.getAdapterPosition()));
                Context context=v.getContext();
                int position=viewHolder.getAdapterPosition();
                Busca busca=buscar.get(position);

                try{
                    claseGlobal objEscritura=new claseGlobal();
                    objEscritura.setCodigo(busca.getCodigo());

            }catch (Exception e){}
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBusca holder, int position) {
        Busca busca =this.buscar.get(position);
        holder.getCodigo().setText(String.valueOf(busca.getCodigo()));
        holder.getNombre().setText(String.valueOf(busca.getNombre()));
    }

    @Override
    public int getItemCount() {
        return this.buscar.size();
    }
}
