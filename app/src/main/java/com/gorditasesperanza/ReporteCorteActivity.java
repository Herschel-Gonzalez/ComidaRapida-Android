package com.gorditasesperanza;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ReporteCorteActivity extends AppCompatActivity {

    Button botonFechaInicio,botonFechaFin,botonBuscar;
    int dia,mes,ano;
    LocalDate fechaInicio,fechaFin;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tvtotalPedidos,tvtotalPesos,tvVacio;
    int totalAcumulado,totalPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_corte);

        Context context=this;

        botonFechaInicio = findViewById(R.id.btn_fechaInicioReporteCorte);
        botonFechaFin = findViewById(R.id.btn_fechaFinReporteCorte);
        botonBuscar = findViewById(R.id.btn_buscarReporteCorte);
        tvtotalPedidos = findViewById(R.id.totalPedidosrc);
        tvtotalPesos = findViewById(R.id.totalPesosrc);
        tvVacio = findViewById(R.id.vacio);

        botonFechaInicio.setOnClickListener(new View.OnClickListener() {
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
                        fechaInicio = LocalDate.of(year,month+1,day);
                        botonFechaInicio.setText(fechaInicio.toString());
                    }
                },2022,mes, dia);
                datePickerDialog.show();
            }
        });

        botonFechaFin.setOnClickListener(new View.OnClickListener() {
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
                        fechaFin = LocalDate.of(year,month+1,day);
                        botonFechaFin.setText(fechaFin.toString());
                    }
                },2022,mes, dia);
                datePickerDialog.show();
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fechaInicio!=null && fechaFin!=null){

                    db.collection("pedidos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                             totalAcumulado = 0;
                             totalPedidos = 0;
                            boolean vacio = true;
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                String auxFentrega = documentSnapshot.getString("fecha de solicitud");

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime fechaTiempo = LocalDateTime.parse(auxFentrega, formatter);

                                LocalDate fentrega = fechaTiempo.toLocalDate();

                                if (fentrega.compareTo(fechaInicio)>=0 && fentrega.compareTo(fechaFin)<=0){

                                    String precio = documentSnapshot.getString("precio");
                                    String cantidad = documentSnapshot.getString("cantidad");

                                    int auxPrecio = Integer.parseInt(precio);
                                    int auxCantidad = Integer.parseInt(cantidad);

                                    int auxTotalpedido = auxPrecio*auxCantidad;

                                    totalAcumulado+=auxTotalpedido;

                                    totalPedidos+=1;
                                    vacio = false;





                                }


                            }

                            if(vacio==true){
                                tvVacio.setText("No se encontraron resultados");
                            }



                            tvtotalPedidos.setText(totalPedidos+"");
                            tvtotalPesos.setText(totalAcumulado+"");




                        }
                    });


                }else{
                    Toast.makeText(context, "Por favor seleccione un filtro de fechas", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}