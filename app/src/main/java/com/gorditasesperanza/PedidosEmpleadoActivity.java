package com.gorditasesperanza;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PedidosEmpleadoActivity extends AppCompatActivity {

    ArrayList<Pedidos> listaPedidos;

    RecyclerView recyclerPedidos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AlertDialog dialog;
    int dia,mes,ano;
    int hora,minutos,segundos;
    LocalDate fechaDeEntrega;
    String horaSeleccionada;

    Spinner op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_empleado);

        listaPedidos=new ArrayList<>();
        recyclerPedidos=findViewById(R.id.RecyclerPedidosEmpleado);
        recyclerPedidos.setHasFixedSize(true);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        Context context = this;
        TextView sinPedidos;
        sinPedidos = findViewById(R.id.nohayPedidos);



        op = findViewById(R.id.spinnerID);

        ArrayList<String> estados = new ArrayList<>();
        estados.add("En preparación");
        estados.add("Cancelado");
        estados.add("Completado");

        ArrayAdapter<String> adap = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,estados);






        db.collection("pedidos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(!documentSnapshot.getString("estado").equals("Completado")){
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
                    }else{

                        sinPedidos.setText("No hay pedidos pendientes");

                    }
                }

                ImageView imageView = (ImageView) findViewById(R.id.idImagenPedidos);
                AdaptadorPedidos adapter = new AdaptadorPedidos(context,listaPedidos,imageView);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Resumen del pedido");

                        View view1 = getLayoutInflater().inflate(R.layout.resumen_pedido_empleado_dialog,null);
                        Button botonGuardar,botonOk;
                        Spinner estados;
                        TextView nombreCliente,direccion,nombre,cantidad,precio,comentarios,fechaSolicitud;
                        Button fechaEntrega,horaEntrega;
                        nombreCliente = view1.findViewById(R.id.nombreClienteResumenPedido);
                        nombreCliente.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getNombreDelCliente());
                        direccion = view1.findViewById(R.id.direccionResumenPedido);
                        String auxdireccion = "";
                        auxdireccion += listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getCalle()+" ";
                        auxdireccion += listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getNumeroCasa()+" ";
                        auxdireccion += listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getColonia()+" ";
                        auxdireccion += " referencia: "+listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getReferencias();
                        direccion.setText(auxdireccion);

                        nombre = view1.findViewById(R.id.nombrePedidoResumenEmp);
                        nombre.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPlatillo());
                        cantidad = view1.findViewById(R.id.cantidadResumenEmp);
                        cantidad.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getCantidad());
                        precio = view1.findViewById(R.id.precioResumenEmp);
                        precio.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPrecio());
                        comentarios = view1.findViewById(R.id.comentariosResumen);
                        comentarios.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getComentarios());
                        fechaSolicitud = view1.findViewById(R.id.fechaSolicitudResumenEmp);
                        fechaSolicitud.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeSolicitud());
                        fechaEntrega = view1.findViewById(R.id.botonFechaEntrega);
                        horaEntrega = view1.findViewById(R.id.botonHoraDeEntrega);
                        botonGuardar = view1.findViewById(R.id.botonGuardarResumen);
                        botonOk = view1.findViewById(R.id.botonOKempleado);
                        estados = view1.findViewById(R.id.spinnerID);

                        String id = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getIdPedido();
                        String nomCliente = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getNombreDelCliente();
                        String fechaSol = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeSolicitud();
                        String fechaEn = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeEntrega();
                        String estado = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getEstado();
                        String platillo = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPlatillo();
                        String ingredientes = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getIngredientes();
                        String price = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPrecio();
                        String desc = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getDescripcion();
                        String cant = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getCantidad();
                        String comen = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getComentarios();
                        String mail = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getCorreo();
                        String img = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getImagen();



                        estados.setAdapter(adap);

                        fechaEntrega.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Calendar c = Calendar.getInstance();
                                dia = c.get(Calendar.DAY_OF_MONTH);
                                mes = c.get(Calendar.MONTH);
                                ano = c.get(Calendar.YEAR);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        fechaDeEntrega = LocalDate.of(year,month+1,day);
                                        fechaEntrega.setText(fechaDeEntrega.toString());
                                    }
                                },2022,mes, dia);
                                datePickerDialog.show();




                            }
                        });

                        horaEntrega.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Calendar c = Calendar.getInstance();
                                hora = c.get(Calendar.HOUR_OF_DAY);
                                minutos = c.get(Calendar.MINUTE);
                                segundos = c.get(Calendar.SECOND);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                                        horaSeleccionada = hour+":"+minute;
                                        horaEntrega.setText(horaSeleccionada.toString());

                                    }
                                },hora,minutos,false);
                                timePickerDialog.show();

                            }
                        });

                        botonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        botonGuardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String fEntrega = fechaDeEntrega+" "+horaSeleccionada;


                                if (fEntrega!=null){

                                    Map<String, Object> pedido = new HashMap<>();

                                    pedido.put("idPedido",id);
                                    pedido.put("nombre del cliente",nomCliente);
                                    pedido.put("fecha de solicitud",fechaSol);
                                    pedido.put("fecha de entrega",fEntrega);
                                    pedido.put("estado",estados.getSelectedItem());
                                    pedido.put("platillo",platillo);
                                    pedido.put("ingredientes",ingredientes);
                                    pedido.put("precio",price);
                                    pedido.put("descripcion",desc);
                                    pedido.put("cantidad",cant);
                                    pedido.put("comentarios",comen);
                                    pedido.put("correo",mail);
                                    pedido.put("imagen",img);

                                    db.collection("pedidos").document(id).set(pedido);

                                    Toast.makeText(context, "Se actualizó con exito", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();



                                }else{
                                    Toast.makeText(context, "Por favor seleccione la fecha y hora de entrega", Toast.LENGTH_SHORT).show();
                                }
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