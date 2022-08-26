package com.gorditasesperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ModificarInformacionPersonalActivity extends AppCompatActivity {

    EditText ednombre,edapellidos,edcalle,ednumeroDeCasa, edcolonia,edreferencias;
    Button btnActualizarDatos;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_informacion_personal);

        firebaseAuth =  FirebaseAuth.getInstance();

        ednombre = findViewById(R.id.actNombre);
        edapellidos = findViewById(R.id.actApellidos);
        edcalle = findViewById(R.id.actCalle);
       ednumeroDeCasa = findViewById(R.id.actNumeroCasa);
        edcolonia = findViewById(R.id.actColonia);
        edreferencias = findViewById(R.id.actReferencias);
        btnActualizarDatos = findViewById(R.id.botonActualizarDatos);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String mail = user.getEmail();



        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(mail.equals(documentSnapshot.getString("correo"))){
                        String nombre = documentSnapshot.getString("nombre");
                        ednombre.setText(nombre);
                        String apellidos = documentSnapshot.getString("apellidos");
                        edapellidos.setText(apellidos);
                        String calle = documentSnapshot.getString("calle");
                        edcalle.setText(calle);
                        String numeroDeCasa = documentSnapshot.getString("numcasa");
                        ednumeroDeCasa.setText(numeroDeCasa);
                        String colonia = documentSnapshot.getString("colonia");
                        edcolonia.setText(colonia);
                        String refrencias = documentSnapshot.getString("referencias");
                        edreferencias.setText(refrencias);
                        pass = documentSnapshot.getString("contrasenia");
                    }

                }

            }
        });
        
        btnActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ednombre.getText()!=null && edapellidos.getText()!=null && edcalle.getText()!=null && ednumeroDeCasa.getText()!=null && edcolonia.getText()!=null && edreferencias.getText()!=null){
                    Map<String, Object> user = new HashMap<>();
                    user.put("nombre",ednombre.getText().toString());
                    user.put("apellidos",edapellidos.getText().toString());
                    user.put("calle",edcalle.getText().toString());
                    user.put("numcasa",ednumeroDeCasa.getText().toString());
                    user.put("colonia",edcolonia.getText().toString());
                    user.put("referencias",edreferencias.getText().toString());
                    user.put("correo",mail);
                    user.put("contrasenia",pass);
                    user.put("tipo","cliente");





                    db.collection("users").document(mail).set(user);

                    Toast.makeText(ModificarInformacionPersonalActivity.this, "¡Se actualizaron los datos con exito!", Toast.LENGTH_SHORT).show();
                    
                }else{
                    Toast.makeText(ModificarInformacionPersonalActivity.this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}