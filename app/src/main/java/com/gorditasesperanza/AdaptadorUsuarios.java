package com.gorditasesperanza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolderUsuarios>implements View.OnClickListener {
    Context context;
    ArrayList<Usuarios> listaUsuarios;
    private View.OnClickListener listener;

    public AdaptadorUsuarios(Context context,ArrayList<Usuarios>listaUsuarios){
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }




    @Override
    public void onClick(View view) {

        if (listener!=null){
            listener.onClick(view);
        }

    }

    @NonNull
    @Override
    public ViewHolderUsuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listasusuarios,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderUsuarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuarios.ViewHolderUsuarios holder, int position) {
            holder.etNombre.setText(listaUsuarios.get(position).getNombre()+" "+listaUsuarios.get(position).getApellidos());
            holder.etCorreo.setText(listaUsuarios.get(position).getCorreo());
            holder.eTipo.setText(listaUsuarios.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



    public class ViewHolderUsuarios extends  RecyclerView.ViewHolder{
        TextView etNombre,etCorreo,eTipo;

        public ViewHolderUsuarios(@NonNull View itemView) {
            super(itemView);
            etNombre = itemView.findViewById(R.id.idNombreUser);
            etCorreo = itemView.findViewById(R.id.idCorreoUser);
            eTipo = itemView.findViewById(R.id.idTipoUsuarioUser);
        }
    }
}
