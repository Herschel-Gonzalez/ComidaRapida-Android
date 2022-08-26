package com.gorditasesperanza;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    EditText etNombre,etApellidos,etCalle,etNumeroCasa,etColonia,etReferencias,
    etCorreo,etContrasenia;
    Button btnRegistrar;

    AwesomeValidation awesomeValidation;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseAuth =  FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.etCorreo, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.etContrasenia,".{6,}",R.string.invalid_password);



        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etCalle = findViewById(R.id.etCalle);
        etNumeroCasa = findViewById(R.id.etNumeroCasa);
        etColonia = findViewById(R.id.etColonia);
        etReferencias = findViewById(R.id.etReferencias);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasenia = findViewById(R.id.etContrasenia);

        btnRegistrar = findViewById(R.id.botonRegistrarse);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = etCorreo.getText().toString();
                String password = etContrasenia.getText().toString();
                String nombre = etNombre.getText().toString();
                String apellidos = etApellidos.getText().toString();
                String calle = etCalle.getText().toString();
                String numcasa = etNumeroCasa.getText().toString();
                String colonia = etColonia.getText().toString();
                String referencias = etReferencias.getText().toString();


                if (awesomeValidation.validate()){


                    //REGISTRAR CREDENCIALES DE INICIO DE SESION
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                //REGISTRAR DATOS PERSONALES

                                Map<String, Object> user = new HashMap<>();

                                user.put("nombre",nombre);
                                user.put("apellidos",apellidos);
                                user.put("calle",calle);
                                user.put("numcasa",numcasa);
                                user.put("colonia",colonia);
                                user.put("referencias",referencias);
                                user.put("correo",mail);
                                user.put("contrasenia",password);
                                user.put("tipo","cliente");

                                db.collection("users").document(mail).set(user);


                                Toast.makeText(RegistroActivity.this, "¡Registro con exito!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{

                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);

                            }

                        }
                    });
                }else{
                    Toast.makeText(RegistroActivity.this, "Completa los datos de forma correcta", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(RegistroActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(RegistroActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(RegistroActivity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(RegistroActivity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                etCorreo.setError("La dirección de correo electrónico está mal formateada.");
                etCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(RegistroActivity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                etContrasenia.setError("la contraseña es incorrecta ");
                etContrasenia.requestFocus();
                etContrasenia.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(RegistroActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(RegistroActivity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(RegistroActivity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegistroActivity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                etCorreo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                etCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(RegistroActivity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(RegistroActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(RegistroActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(RegistroActivity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(RegistroActivity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(RegistroActivity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegistroActivity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                etContrasenia.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                etContrasenia.requestFocus();
                break;

        }

    }

}