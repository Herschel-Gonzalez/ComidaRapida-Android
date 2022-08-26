package com.gorditasesperanza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UsuariosAcitivity extends AppCompatActivity {

    ArrayList<Usuarios> listaUsuarios;

    RecyclerView recyclerUsuarios;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_acitivity);

        listaUsuarios = new ArrayList<>();
        recyclerUsuarios=findViewById(R.id.RecyclerListaUsuarios);
        recyclerUsuarios.setHasFixedSize(true);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        Context context = this;


        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String apellidos = documentSnapshot.getString("apellidos");
                    String calle = documentSnapshot.getString("calle");
                    String colonia = documentSnapshot.getString("colonia");
                    String correo = documentSnapshot.getString("correo");
                    String nombre = documentSnapshot.getString("nombre");
                    String numeroDeCasa = documentSnapshot.getString("numcasa");
                    String referencias = documentSnapshot.getString("referencias");
                    String tipo = documentSnapshot.getString("tipo");
                    listaUsuarios.add(new Usuarios(apellidos,calle,colonia,correo,nombre,numeroDeCasa,referencias,tipo));

                }

                AdaptadorUsuarios adapter = new AdaptadorUsuarios(context,listaUsuarios);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Información del usuario");

                        View view1 = getLayoutInflater().inflate(R.layout.info_usuario_dialog,null);

                        TextView nombre,correo,tipo,calle,numCasa,colonia,referencias;
                        Button okboton;

                        nombre = view1.findViewById(R.id.nombreUsuario);
                        nombre.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getNombre());
                        correo = view1.findViewById(R.id.correoUsuario);
                        correo.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getCorreo());
                        tipo = view1.findViewById(R.id.tipoUsuario);
                        tipo.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getTipo());
                        calle = view1.findViewById(R.id.calleUsuario);
                        calle.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getCalle());
                        numCasa = view1.findViewById(R.id.numeroDeCasaUsuario);
                        numCasa.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getNumeroDeCasa());
                        colonia = view1.findViewById(R.id.coloniaUsuario);
                        colonia.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getColonia());
                        referencias = view1.findViewById(R.id.referenciasUsuario);
                        referencias.setText(listaUsuarios.get(recyclerUsuarios.getChildAdapterPosition(view)).getReferencias());
                        okboton = view1.findViewById(R.id.botonOKUsuarios);

                        okboton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });


                        builder.setView(view1);
                        dialog = builder.create();
                        dialog.show();

                    }
                });

                recyclerUsuarios.setAdapter(adapter);

            }
        });


    }
}