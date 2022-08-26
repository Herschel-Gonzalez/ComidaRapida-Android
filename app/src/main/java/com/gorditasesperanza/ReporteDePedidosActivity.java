package com.gorditasesperanza;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class ReporteDePedidosActivity extends AppCompatActivity {

    ArrayList<Pedidos> listaPedidos;

    RecyclerView recyclerPedidos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AlertDialog dialog;
    int dia,mes,ano;
    LocalDate primeraFecha,segundaFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_de_pedidos);

        listaPedidos=new ArrayList<>();
        recyclerPedidos=findViewById(R.id.RecyclerReporteDePedidos);
        recyclerPedidos.setHasFixedSize(true);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        Context context = this;
        TextView sinPedidos;
        Button fechaInicio,fechaFin,buscar;
        sinPedidos = findViewById(R.id.sinPedidosReporte);
        fechaInicio = findViewById(R.id.fechaDeInicioReportePedidos);
        fechaFin = findViewById(R.id.fechaFinReportePedidos);
        buscar = findViewById(R.id.botonBuscarPedidos);




        fechaInicio.setOnClickListener(new View.OnClickListener() {
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

                        primeraFecha = LocalDate.of(year,month+1,day);
                        fechaInicio.setText(primeraFecha.toString());

                    }
                },2022,mes, dia);
                datePickerDialog.show();
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
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
                        segundaFecha = LocalDate.of(year,month+1,day);

                        fechaFin.setText(segundaFecha.toString());
                    }
                },2022,mes, dia);
                datePickerDialog.show();


            }
        });


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("pedidos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (primeraFecha!=null && segundaFecha!=null){


                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                                // LocalDateTime fsoli = LocalDateTime.

                                String auxFentrega = documentSnapshot.getString("fecha de solicitud");

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime fechaTiempo = LocalDateTime.parse(auxFentrega, formatter);

                                LocalDate fentrega = fechaTiempo.toLocalDate();

                                if(fentrega.compareTo(primeraFecha)>0 && fentrega.compareTo(segundaFecha)<0){

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

                                sinPedidos.setText("No hay pedidos en ese intervalo de fechas"+fentrega);



                            }

                            ImageView imageView = (ImageView) findViewById(R.id.idImagenPedidos);
                            AdaptadorPedidos adapter = new AdaptadorPedidos(context,listaPedidos,imageView);

                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Resumen del pedido");

                                    View view1 = getLayoutInflater().inflate(R.layout.resumen_pedido_reportes_dialog,null);
                                    Button botonOk;
                                    TextView nombre,cantidad,precio,fechaSolicitud,fechaEntrega,estado;
                                    nombre = view1.findViewById(R.id.nombrePedidoResumenEmp);
                                    nombre.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPlatillo());
                                    cantidad = view1.findViewById(R.id.cantidadResumenEmp);
                                    cantidad.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getCantidad());
                                    precio = view1.findViewById(R.id.precioResumenEmp);
                                    precio.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getPrecio());
                                    fechaSolicitud = view1.findViewById(R.id.fechaSolicitudResumenEmp);
                                    fechaSolicitud.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeSolicitud());
                                    fechaEntrega = view1.findViewById(R.id.fEntregaPedidoReportes);
                                    fechaEntrega.setText(listaPedidos.get(recyclerPedidos.getChildAdapterPosition(view)).getFechaDeEntrega());
                                    botonOk = view1.findViewById(R.id.botonOKempleado);
                                    botonOk.setOnClickListener(new View.OnClickListener() {
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



                        }else{
                            Toast.makeText(context, "Por favor selecciona un intervalo de fechas", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });





    }
}