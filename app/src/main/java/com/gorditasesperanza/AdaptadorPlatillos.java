package com.gorditasesperanza;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorPlatillos extends RecyclerView.Adapter<AdaptadorPlatillos.ViewHolderPlatillos> implements View.OnClickListener{
    Context context;
    static Context imgContext;
    ArrayList<Platillos> listaPlatillos;
    ImageView imageView;
    private View.OnClickListener listener;
   // private View.OnClickListener listener;

    public AdaptadorPlatillos(Context context,ArrayList<Platillos> listaPlatillos,ImageView imageView) {
        this.context = context;
        this.listaPlatillos = listaPlatillos;
        this.imageView = imageView;
       // Toast.makeText(context, "size= "+listaPlatillos.size(), Toast.LENGTH_SHORT).show();



    }

    @NonNull
    @Override
    public ViewHolderPlatillos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listas,parent,false);

        view.setOnClickListener(this);

        return new ViewHolderPlatillos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlatillos holder, int position) {

        Uri myuri;
        holder.etNombre.setText(listaPlatillos.get(position).getNombre());
        holder.etDescripcion.setText(listaPlatillos.get(position).getDescripcion());
        myuri= Uri.parse(listaPlatillos.get(position).getImagen());
        holder.imagen.setImageURI(myuri);
        Glide.with(imgContext).load(myuri).into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return listaPlatillos.size();
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


    public static class ViewHolderPlatillos extends RecyclerView.ViewHolder {

        TextView etNombre,etDescripcion;
        ImageView imagen;

        public ViewHolderPlatillos(@NonNull View itemView) {
            super(itemView);
            etNombre=itemView.findViewById(R.id.idNombre);
            etDescripcion=itemView.findViewById(R.id.idInfo);
            imagen=itemView.findViewById(R.id.idImagen);
            imgContext =imagen.getContext();
        }
    }
}
