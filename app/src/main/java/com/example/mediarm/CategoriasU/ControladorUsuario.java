package com.example.mediarm.CategoriasU;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediarm.R;

public class ControladorUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador_usuario);

        String CategoriaRecuperada = getIntent().getStringExtra("categoria");

        if (CategoriaRecuperada.equals("Conceptos")){
            startActivity(new Intent(ControladorUsuario.this, ConceptosUsuario.class));

        }
    }
}