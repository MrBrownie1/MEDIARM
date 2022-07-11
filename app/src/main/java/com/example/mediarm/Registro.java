package com.example.mediarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    Button registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }
        Button btn_register;
        EditText name, correo, contrasena;
        FirebaseFirestore mFirestore;
        FirebaseAuth mAuth;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro);

            mFirestore = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();

            name = findViewById(R.id.nombre);
            correo = findViewById(R.id.email);
            contrasena = findViewById(R.id.password);
            btn_register = findViewById(R.id.btn_registro);

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    String nameUser = name.getText().toString().trim();
                    String corrUser = correo.getText().toString().trim();
                    String contraUser = contrasena.getText().toString().trim();

                    if (nameUser.isEmpty() && corrUser.isEmpty() && contraUser.isEmpty()){
                        Toast.makeText(Registro.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        registerUser(nameUser, corrUser, contraUser);
                    }
                }
            });

        }

        private void registerUser(String nameUser, String corrUser, String contraUser) {
            mAuth.createUserWithEmailAndPassword(corrUser, contraUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("name", nameUser);
                    map.put("correo", corrUser);
                    map.put("contrasena", contraUser);

                    mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            startActivity(new Intent(Registro.this, MainActivity.class));
                            Toast.makeText(Registro.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registro.this, "Error al guardar", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Registro.this, "Error al registrar", Toast.LENGTH_SHORT).show();

                }
            });
        }
        @Override
        public boolean onSupportNavigateUp(){
            onBackPressed();
            return false;
        }
    }
