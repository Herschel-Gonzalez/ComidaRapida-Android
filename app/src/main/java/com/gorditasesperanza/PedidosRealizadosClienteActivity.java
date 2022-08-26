package com.gorditasesperanza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PedidosRealizadosClienteActivity extends AppCompatActivity {

    ArrayList<Pedidos> listaPedidos;

    RecyclerView recyclerPedidos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AlertDialog dialog;

    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_realizados_cliente);
        listaPedidos=new ArrayList<>();
        recyclerPedidos = findViewById(R.id.RecyclerPedidosRealizados);
        recyclerPedidos.setHasFixedSize(true);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        Context context=this;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        mail = user.getEmail();

        db.collection("pedidos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(documentSnapshot.getString("correo").equals(mail)){
                        String cantidad = documentSnapshot.getString("cantidad");
                        String comentarios = documentSnapshot.getString("comentarios");
                        String correo = documentSnapshot.getString("correo");
                        String descripcion = documentSnapshot.getString("descripcion");
                        String estado = documentSnapshot.getString("estado");
                        String fechaDeEntrega = documentSnapshot.getString("fecha de entrega");
                        String fechaDeSolicitud = documentSnapshot.getString("fecha de solicitud");
                        String idPedido = documentSnapshot.getString("idPedido");
                        String imagen = documentSnapshot.getString("imagen");
                        String ingredientes = documentSnapshot.getString("ingredientes");
                        String nomCliente = documentSnapshot.getString("nombre del cliente");
                        String platillo = documentSnapshot.getString("platillo");
                        String precio = documentSnapshot.getString("precio");
                        String calle = documentSnapshot.getString("calle");
                        String colonia = documentSnapshot.getString("colonia");
                        String numcasa = documentSnapshot.getString("numcasa");
                        String referencias = documentSnapshot.getString("referencias");

                        listaPedidos.add(new Pedidos(cantidad,comentarios,correo,descripcion,estado,fechaDeEntrega,fechaDeSolicitud,idPedido,imagen,ingredientes,nomCliente,platillo,precio,calle,colonia,numcasa,referencias));

                    }
                }

                ImageView imageView = (ImageView) findViewById(R.id.idImagenPedidos);
                AdaptadorPedidos adapter = new AdaptadorPedidos(context,listaPedidos,imageView);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Resumen del pedido");
                        View view1 = getLayoutInflater().inflate(R.layout.resumen_pedido_dialog,null);
                        Button ok = view1.findViewById(R.id.botonOK);
                        TextView nombre,cantidad,precio,fechaSolicitud,fechaEntrega,estado;
                        nombre = view1.findViewById(R.id.nombrePedidoResumen);
                        nombre.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPlatillo());
                        cantidad = view1.findViewById(R.id.cantidadResumen);
                        cantidad.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getCantidad());
                        precio = view1.findViewById(R.id.precioResumen);
                        precio.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPrecio());
                        fechaSolicitud = view1.findViewById(R.id.fechaSolicitudResumen);
                        fechaSolicitud.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeSolicitud());
                        fechaEntrega = view1.findViewById(R.id.fechaEntregaResumen);
                        fechaEntrega.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeEntrega());
                        estado = view1.findViewById(R.id.estadoPedidoResumen);
                        estado.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getEstado());

                        ok.setOnClickListener(new View.OnClickListener() {
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

                recyclerPedidos.setAdapter(adapter);

            }
        });




    }
}