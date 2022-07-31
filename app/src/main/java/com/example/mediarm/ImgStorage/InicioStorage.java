package com.example.mediarm.ImgStorage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediarm.R;

public class InicioStorage extends AppCompatActivity {

    Button btnMedicina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_storage);

        btnMedicina = findViewById(R.id.btnMB);

        btnMedicina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioStorage.this, ImgConceptos.class));
            }
        });
    }
}