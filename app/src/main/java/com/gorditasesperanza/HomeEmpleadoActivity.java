package com.gorditasesperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeEmpleadoActivity extends AppCompatActivity {

    Button botonCerrarSesion,btnPedidos,btnReportes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empleado);

        botonCerrarSesion = findViewById(R.id.butonCerrarSesion);
        btnPedidos = findViewById(R.id.btn_pedidos);
        btnReportes = findViewById(R.id.btn_reportes);


        botonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeEmpleadoActivity.this, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
                irAlogin();
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irApedidos();
            }
        });
        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAreportes();
            }
        });




    }

    private void irAlogin() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void irAplatillos(View vista){
        Intent i = new Intent(this, PlatillosEmpleadoActivity.class);
        startActivity(i);

    }

    public void irApedidos(){
        Intent i = new Intent(this, PedidosEmpleadoActivity.class);
        startActivity(i);

    }


    public void irAreportes(){
        Intent i = new Intent(this, ReportesActivity.class);
        startActivity(i);

    }


}