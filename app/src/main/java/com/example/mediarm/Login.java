package com.example.mediarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button registrarte;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrarte = findViewById(R.id.registrarte);
        login = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            userLogin();
        });

        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

    }//End onCreate

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }// End openRegisterActivity

    public void userLogin(){
        String mail = email.getText().toString();
        String paassword = password.getText().toString();

        if (TextUtils.isEmpty(mail)){
            email.setError("Ingrese un correo");
            email.requestFocus();
        }else if (TextUtils.isEmpty(paassword)){
            Toast.makeText(Login.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else{

            mAuth.signInWithEmailAndPassword(mail, paassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Principal.class));
                    }else {
                        Log.w("TAG", "Error:", task.getException());
                    }
                }
            });

        }

    }

}