package com.example.mediarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btn_registro;
    EditText nombre,apellido,correo,password,numero;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        correo = findViewById(R.id.email);
        password = findViewById(R.id.password);
        numero = findViewById(R.id.numero);
        btn_registro = findViewById(R.id.btn_registro);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String nameUser = nombre.getText().toString().trim();
                String apeUser = apellido.getText().toString().trim();
                String contraUser = password.getText().toString().trim();
                String corrUser = correo.getText().toString().trim();
                String numUser = numero.getText().toString().trim();

                if (nameUser.isEmpty() && apeUser.isEmpty() && contraUser.isEmpty() && corrUser.isEmpty() && numUser.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(nameUser, apeUser, contraUser, corrUser, numUser);
                }
            }
        });

    }

    private void registerUser(String nameUser, String apeUser, String contraUser, String corrUser, String numUser) {
        mAuth.createUserWithEmailAndPassword(corrUser, contraUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("nombre", nameUser);
                map.put("apellido", apeUser);
                map.put("contrasena", contraUser);
                map.put("correo", corrUser);
                map.put("numero", numUser);


                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        Toast.makeText(RegisterActivity.this, "Usuario registrado éxitosamente", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return false;
    }
}