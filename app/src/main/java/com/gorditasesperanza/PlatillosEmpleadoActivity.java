package com.gorditasesperanza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlatillosEmpleadoActivity extends AppCompatActivity {

    ArrayList<Platillos>listaPlatillos;

    RecyclerView recyclerPlatillos;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference mstorage;
    static final int GALLERY_INTENT=1;
    Intent intentImage;

    TextView tvNombre,tvIngredientes,tvPrecio,tvDescripcion;
    Button guardarCambios;
    AlertDialog dialog;
    String url;
    String urlOriginal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platillos_empleado);
        tvNombre = findViewById(R.id.modNombre);
        tvIngredientes = findViewById(R.id.modIngredientes);
        tvPrecio = findViewById(R.id.modPrecio);
        tvDescripcion = findViewById(R.id.modDescripcion);
        guardarCambios = findViewById(R.id.botonGuardarMod);


        listaPlatillos=new ArrayList<>();
        recyclerPlatillos=findViewById(R.id.RecyclerId);
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
                        builder.setTitle("Modificar platillo");

                        String nombreOriginal = listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getNombre();

                        urlOriginal = listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getImagen();
                        url = urlOriginal;

                        View view1 = getLayoutInflater().inflate(R.layout.modificarplatillo_dialog,null);
                        EditText eNombre,eIngredientes,ePrecio,eDescripcion;
                        eNombre = view1.findViewById(R.id.modNombre);
                        eNombre.setText(listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getNombre());
                        eIngredientes = view1.findViewById(R.id.modIngredientes);
                        eIngredientes.setText(listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getIngredientes());
                        ePrecio = view1.findViewById(R.id.modPrecio);
                        ePrecio.setText(listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getPrecio());
                        eDescripcion = view1.findViewById(R.id.modDescripcion);
                        eDescripcion.setText(listaPlatillos.get(recyclerPlatillos.getChildAdapterPosition(view)).getDescripcion());
                        Button save = view1.findViewById(R.id.botonGuardarMod);
                        Button cambiarImagen = view1.findViewById(R.id.botonActualizarImagen);
                        mstorage = FirebaseStorage.getInstance().getReference();

                        cambiarImagen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                intentImage=intent;
                                startActivityForResult(intentImage,GALLERY_INTENT);

                            }
                        });

                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (eNombre.getText()!=null && eIngredientes.getText()!=null && ePrecio.getText()!=null && eDescripcion.getText()!=null){

                                    Map<String, Object> platillo = new HashMap<>();

                                    platillo.put("imagen",url);
                                    platillo.put("nombre",eNombre.getText().toString());
                                    platillo.put("ingredientes",eIngredientes.getText().toString());
                                    platillo.put("precio",ePrecio.getText().toString());
                                    platillo.put("descripcion",eDescripcion.getText().toString());

                                    db.collection("platillos").document(nombreOriginal).set(platillo);

                                    Toast.makeText(context, "¡Platillo modificado con exito!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();


                                }else{
                                    Toast.makeText(context, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        builder.setView(view1);
                        dialog = builder.create();
                        dialog.show();

                    }
                });

                recyclerPlatillos.setAdapter(adapter);

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

                    Toast.makeText(PlatillosEmpleadoActivity.this, "Se guardo correctamente la imagen", Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(PlatillosEmpleadoActivity.this, "No se pudo obtener la url", Toast.LENGTH_SHORT).show();
                        url=urlOriginal;
                    }

                }
            });


        }else{
            Toast.makeText(this, "No se pudo subir la imagen, contacte al administrador", Toast.LENGTH_SHORT).show();
            url=urlOriginal;
        }
    }



    public void irAagregarPlatillo(View view) {
        Intent i = new Intent(this,AgregarPlatilloActivity.class);
        startActivity(i);
    }
}