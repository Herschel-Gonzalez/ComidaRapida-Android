package com.gorditasesperanza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AgregarPlatilloActivity extends AppCompatActivity {

    EditText edNombre,edIngredientes,edPrecio,edDescripcion;
    Button btnSubirImagen,btnRegistrar;
    StorageReference mstorage;
    static final int GALLERY_INTENT=1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String imagen = "";
    Intent intentImage;
    String url;
    StorageReference mStorageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_platillo);

        edNombre = findViewById(R.id.etNombrePla);
        edIngredientes = findViewById(R.id.etIngredientesPla);
        edPrecio = findViewById(R.id.etPrecioPla);
        edDescripcion = findViewById(R.id.etDescripcionPla);

        btnRegistrar = findViewById(R.id.botonRegistrarPlatillo);

        mstorage = FirebaseStorage.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        btnSubirImagen = (Button) findViewById(R.id.botonSubirIamgen);

        btnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intentImage=intent;
                startActivityForResult(intentImage,GALLERY_INTENT);

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edNombre.getText()!=null && edDescripcion.getText()!=null && edIngredientes.getText()!=null && edPrecio.getText()!=null){

                    String nombre = edNombre.getText().toString();
                    String ingredientes = edIngredientes.getText().toString();
                    String precio = edPrecio.getText().toString();
                    String descripcion = edDescripcion.getText().toString();



                    Map<String, Object> platillo = new HashMap<>();

                    platillo.put("imagen",url);
                    platillo.put("nombre",nombre);
                    platillo.put("ingredientes",ingredientes);
                    platillo.put("precio",precio);
                    platillo.put("descripcion",descripcion);

                    db.collection("platillos").document(nombre).set(platillo);

                    Toast.makeText(AgregarPlatilloActivity.this, "¡Registro con exito!", Toast.LENGTH_SHORT).show();
                    finish();


                }else{
                    Toast.makeText(AgregarPlatilloActivity.this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            Uri uri = data.getData();
            StorageReference filepath = mstorage.child("imagenes").child(uri.getLastPathSegment());
          Task uploadTask= filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(AgregarPlatilloActivity.this, "Se guardo correctamente la imagen", Toast.LENGTH_SHORT).show();


                }
            });

          Task <Uri> urlTask = uploadTask.continueWithTask(new Continuation() {
              @Override
              public Object then(@NonNull Task task) throws Exception {
                  if (!task.isSuccessful()) {
                      throw task.getException();
                  }

                  // Continue with the task to get the download URL
                  return filepath.getDownloadUrl();

              }
          }).addOnCompleteListener(new OnCompleteListener() {
              @Override
              public void onComplete(@NonNull Task task) {
                  if (task.isSuccessful()) {
                      Uri downloadUri = (Uri) task.getResult();
                      url=downloadUri.toString();
                  } else {
                      // Handle failures
                      // ...
                      Toast.makeText(AgregarPlatilloActivity.this, "No se pudo obtener la url", Toast.LENGTH_SHORT).show();
                  }

              }
          });


        }else{
            Toast.makeText(this, "No se pudo subir la imagen, contacte al administrador", Toast.LENGTH_SHORT).show();
        }
    }
}