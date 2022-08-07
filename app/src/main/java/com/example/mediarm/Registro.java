package com.example.mediarm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registro extends AppCompatActivity {

    EditText nombre;
    EditText apellido;
    EditText email;
    EditText password;
    EditText numero;
    Button btn_registro;

    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        numero = findViewById(R.id.txtNumero);
        btn_registro = (Button)findViewById(R.id.btn_registro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_registro.setOnClickListener(view -> {
            createuser();
        });


    }//End onCreate

    public void createuser(){

        String name = nombre.getText().toString();
        String ape = apellido.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String phone = numero.getText().toString();

        if(TextUtils.isEmpty(name)){
            nombre.setError("Ingres tu nombre");
            nombre.requestFocus();
        }else if (TextUtils.isEmpty(ape)){
            apellido.setError("Ingrese un apellido");
            apellido.requestFocus();
        }else if(TextUtils.isEmpty(mail)){
            email.setError("Ingresa un email");
            email.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            password.setError("Ingrese una contraseña");
            password.requestFocus();
        }else if (TextUtils.isEmpty(phone)){
            numero.setError("Ingrese un numero");
            numero.requestFocus();
        }else {

            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        userID= Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                        DocumentReference documentReference = db.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Apellido", ape);
                        user.put("Email", mail);
                        user.put("Contraseña", pass);
                        user.put("Numero", phone);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Tag", "On Success: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText(Registro.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Registro.this, Login.class));
                    }else {
                        Toast.makeText(Registro.this, "Usuario no Registrado", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
