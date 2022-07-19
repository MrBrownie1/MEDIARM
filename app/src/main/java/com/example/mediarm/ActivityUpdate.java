package com.example.mediarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityUpdate extends AppCompatActivity {

    Button btnU;
    EditText Nombre, Apellido, Numero;;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        String id = getIntent().getStringExtra("id_user");
        firebaseFirestore = FirebaseFirestore.getInstance();

        Numero=findViewById(R.id.txtNumeroU);
        Apellido=findViewById(R.id.txtApellidoU);
        Nombre=findViewById(R.id.txtNombreU);
        btnU=findViewById(R.id.btn_updateU);

        getUser(id);
        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Nombre.getText().toString().trim();
                String ApeP = Apellido.getText().toString().trim();
                String phone = Numero.getText().toString().trim();

                if (name.isEmpty() && ApeP.isEmpty() && phone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_LONG).show();
                }else{
                    updateUser(name,ApeP,phone,id);
                }

            }
        });
    }

    private void updateUser(String name, String apeP, String phone, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", name);
        map.put("Apellido", apeP);
        map.put("Numero", phone);;

        firebaseFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUser(String id){
        firebaseFirestore.collection("users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameU = documentSnapshot.getString("Nombre");
                String apePU = documentSnapshot.getString("Apellido");
                String phoneMU = documentSnapshot.getString("Numero");


                Nombre.setText(nameU);
                Apellido.setText(apePU);
                Numero.setText(phoneMU);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener datos", Toast.LENGTH_LONG).show();
            }
        });

    }

}