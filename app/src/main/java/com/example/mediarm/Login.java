package com.example.mediarm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediarm.Cliente.InicioCliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btnRegistro, btnLogin;
    EditText password, email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtEmailL);
        password = findViewById(R.id.txtPasswordL);
        btnRegistro = findViewById(R.id.registrarte);
        btnLogin = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            userLogin();
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

    }//End onCreate

    public void openRegisterActivity() {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }// End openRegisterActivity

    public void userLogin(){
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(mail)){
            email.setError("Ingrese un correo");
            email.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            password.setError("Ingrese una contraseña");
            password.requestFocus();
        }else{

            mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, InicioCliente.class));
                    }else {
                        Log.w("TAG", "Error:", task.getException());
                    }
                }
            });

        }

    }

}