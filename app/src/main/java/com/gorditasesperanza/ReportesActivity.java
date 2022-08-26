package com.gorditasesperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportesActivity extends AppCompatActivity {

    Button btnCorte,btnPedidos,btnUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        btnCorte = findViewById(R.id.btn_reporte_corte);
        btnPedidos = findViewById(R.id.btn_reporte_pedidos);
        btnUsuarios = findViewById(R.id.btn_reporte_usuarios);


        btnCorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    irAreporteDeCorte();
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAreporteDePedidos();
            }
        });

        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAreporteDeUsuarios();
            }
        });

    }

    public void irAreporteDeCorte(){
        Intent i = new Intent(this,ReporteCorteActivity.class);
        startActivity(i);
    }

    public void irAreporteDePedidos(){
        Intent i = new Intent(this,ReporteDePedidosActivity.class);
        startActivity(i);
    }

    public void irAreporteDeUsuarios(){
        Intent i = new Intent(this,UsuariosAcitivity.class);
        startActivity(i);
    }



}