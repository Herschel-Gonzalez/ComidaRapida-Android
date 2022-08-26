package com.gorditasesperanza;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlatillosClienteActivity extends AppCompatActivity {
    ArrayList<Platillos> listaPlatillos;

    RecyclerView recyclerPlatillos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AlertDialog dialog;
    String idPedido;
    String imgPlatillo;
    int idaux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platillos_cliente);
        listaPlatillos=new ArrayList<>();
        recyclerPlatillos=findViewById(R.id.RecyclerPlatillosCliente);
        recyclerPlatillos.setHasFixedSize(true);
        recyclerPlatillos.setLayoutManager(new LinearLayoutManager(this));
        Context context=this;

        db.collection("platillos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String nombre = documentSnapshot.getString("nombre");
                    String descripcion = documentSnapshot.getString("descripcion");
                    String ingredientes = documentSnapshot.getString("ingredientes");
                    String precio = documentSnapshot.getString("precio");
                    String imagen = documentSnapshot.getString("imagen");


                    listaPlatillos.add(new Platillos(nombre,ingredientes,descripcion,precio,imagen));

                }

                ImageView imageView = (ImageView) findViewById(R.id.idImagen);
                AdaptadorPlatillos adapter = new AdaptadorPlatillos(context,listaPlatillos,imageView);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Realizar pedido");

                        View view2 = getLayoutInflater().inflate(R.layout.realizarpedido_dialog,null);

                        TextView nombre,ingredientes,precio,descripcion;
                        EditText cantidad,comentarios;
                        Button hacerPedido;

                        imgPlatillo = listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getImagen();

                        hacerPedido = view2.findViewById(R.id.botonHacerPedido);

                        nombre = view2.findViewById(R.id.nombrePedido);
                        nombre.setText((listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getNombre()));
                        ingredientes = view2.findViewById(R.id.ingredientesPedido);
                        ingredientes.setText((listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getIngredientes()));
                        precio = view2.findViewById(R.id.precioPedido);
                        precio.setText((listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getPrecio()));
                        descripcion = view2.findViewById(R.id.descripcionPedido);
                        descripcion.setText((listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getDescripcion()));
                        cantidad = view2.findViewById(R.id.cantidadPedido);
                        String cant = cantidad.getText().toString();
                        comentarios = view2.findViewById(R.id.comentariosPedido);
                        String coment = comentarios.getText().toString();

                        hacerPedido.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View view) {

                                if (cant!=null){



                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    String mail = user.getEmail();



                                    db.collection("idPedido").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                                idPedido = documentSnapshot.getString("id");

                                            }
                                            idaux = Integer.parseInt(idPedido);
                                            idaux+=1;






                                        }
                                    });

                                    db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            ArrayList <String> nombres = new ArrayList<>();
                                            ArrayList <String> correos = new ArrayList<>();
                                            String correo,nombreCliente="",calle="",colonia="",numcasa="",referencias="";
                                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                                                correo =documentSnapshot.getString("correo");

                                                if (mail.equals(correo)){

                                                    correo =documentSnapshot.getString("correo");
                                                    nombreCliente = documentSnapshot.getString("nombre");
                                                    calle = documentSnapshot.getString("calle");
                                                    colonia = documentSnapshot.getString("colonia");
                                                    numcasa = documentSnapshot.getString("numcasa");
                                                    referencias = documentSnapshot.getString("referencias");


                                                }

                                            }




                                            Map<String, Object> pedido = new HashMap<>();
                                            Map<String, Object> idPedidos = new HashMap<>();

                                            String docId = idaux+"";


                                            idPedidos.put("id",docId);

                                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

                                            String fechaSolicitud = dtf.format(LocalDateTime.now(ZoneId.of("GMT-5")));

                                            pedido.put("idPedido",docId);
                                            pedido.put("nombre del cliente",nombreCliente);
                                            pedido.put("fecha de solicitud",fechaSolicitud);
                                            pedido.put("fecha de entrega","Por definir");
                                            pedido.put("estado","Pendiente");
                                            pedido.put("platillo",nombre.getText().toString());
                                            pedido.put("ingredientes",ingredientes.getText().toString());
                                            pedido.put("precio",precio.getText().toString());
                                            pedido.put("descripcion",descripcion.getText().toString());
                                            pedido.put("cantidad",cantidad.getText().toString());
                                            pedido.put("comentarios",comentarios.getText().toString());
                                            pedido.put("correo",mail);
                                            pedido.put("imagen",imgPlatillo);
                                            pedido.put("calle",calle);
                                            pedido.put("colonia",colonia);
                                            pedido.put("numcasa",numcasa);
                                            pedido.put("referencias",referencias);





                                            db.collection("pedidos").document(docId).set(pedido);
                                            db.collection("idPedido").document("numeroDePedidos").set(idPedidos);

                                            Toast.makeText(context, "¡Pedido realizado con exito!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();

                                        }
                                    });


                                }else{
                                    Toast.makeText(context, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        builder.setView(view2);
                        dialog = builder.create();
                        dialog.show();


                    }
                });

                recyclerPlatillos.setAdapter(adapter);

            }
        });


    }
}