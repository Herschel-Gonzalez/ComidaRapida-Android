package com.gorditasesperanza;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolderPedidos>implements View.OnClickListener{
    Context context;
    static Context imgContext;
    ArrayList<Pedidos> listaPedidos;
    ImageView imageView;
    private View.OnClickListener listener;

    public AdaptadorPedidos(Context context, ArrayList<Pedidos> listaPedidos, ImageView imageView) {
        this.context = context;
        this.listaPedidos = listaPedidos;
        this.imageView = imageView;
    }




    @NonNull
    @Override
    public AdaptadorPedidos.ViewHolderPedidos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_pedidos,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderPedidos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPedidos.ViewHolderPedidos holder, int position) {
        Uri myuri;
        holder.platillo.setText(listaPedidos.get(position).getPlatillo());
        holder.cantidad.setText(listaPedidos.get(position).getCantidad());
        holder.fechaSolicitud.setText(listaPedidos.get(position).getFechaDeSolicitud());
        holder.precio.setText(listaPedidos.get(position).getPrecio());
        myuri = Uri.parse(listaPedidos.get(position).getImagen());
        holder.imagen.setImageURI(myuri);
        Glide.with(imgContext).load(myuri).into(holder.imagen);


    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {

        if (listener!=null){
            listener.onClick(view);
        }

    }

    public static class ViewHolderPedidos extends RecyclerView.ViewHolder{
        TextView platillo, cantidad,fechaSolicitud,precio;
        ImageView imagen;
        public ViewHolderPedidos(@NonNull View itemView) {
            super(itemView);
            platillo=itemView.findViewById(R.id.platillo);
            cantidad=itemView.findViewById(R.id.cantidad);
            fechaSolicitud=itemView.findViewById(R.id.fechaSolicitud);
            precio=itemView.findViewById(R.id.precioListaPedidos);
            imagen=itemView.findViewById(R.id.idImagenPedidos);
            imgContext=imagen.getContext();

        }
    }
}
