package com.gorditasesperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeClienteActivity extends AppCompatActivity {

    Button btnCerrarSesion,btnPlatillos,btnModInfPersonal,botonPedidosCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        btnCerrarSesion = findViewById(R.id.btn_cerrarSesion);
        btnPlatillos = findViewById(R.id.btn_platillos);
        btnModInfPersonal = findViewById(R.id.btn_modinfopersonal);
        botonPedidosCliente = findViewById(R.id.btn_pedidosCliente);

        btnPlatillos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPlatillos();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeClienteActivity.this, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
                irAlogin();
            }
        });

        btnModInfPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAModInformacion();
            }
        });

        botonPedidosCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irApedidos();
            }
        });



    }

    private void irAlogin() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }



    private void irPlatillos() {
        Intent i = new Intent(this,PlatillosClienteActivity.class);
        startActivity(i);
    }

    private void irAModInformacion() {
        Intent i = new Intent(this,ModificarInformacionPersonalActivity.class);
        startActivity(i);
    }

    private void irApedidos() {
        Intent i = new Intent(this,PedidosRealizadosClienteActivity.class);
        startActivity(i);
    }

}