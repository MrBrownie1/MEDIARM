package com.example.mediarm.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediarm.CategoriasU.CategoriaU;
import com.example.mediarm.CategoriasU.ConceptosUsuario;
import com.example.mediarm.CategoriasU.ViewHolderCU;
import com.example.mediarm.ImgStorage.ImgConceptos;
import com.example.mediarm.Login;
import com.example.mediarm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class InicioCliente extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<CategoriaU, ViewHolderCU> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<CategoriaU> optionsU;

    TextView perfil, textview_email;
    FirebaseAuth mAuth;
    private String idUser;
    FirebaseFirestore mFirestore;
    Button btnCerrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_cliente);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CATEGORIA");

        perfil = findViewById(R.id.textview_fullname);
        textview_email = findViewById(R.id.textview_email);
        btnCerrar = findViewById(R.id.btnCerrarUs);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        idUser = mAuth.getCurrentUser().getUid();

        textview_email.setText(user.getEmail());

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioCliente.this, Login.class));
                Toast.makeText(InicioCliente.this, "Ha cerrado sesion", Toast.LENGTH_SHORT).show();
            }
        });



        linearLayoutManager = new LinearLayoutManager(InicioCliente.this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recyclerViewCIU);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        verCategorias();

        DocumentReference documentReference = mFirestore.collection("users").document(idUser);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot == null || mAuth.getCurrentUser() == null) return;
                perfil.setText(documentSnapshot.getString("Nombre"));
            }
        });

    }

    private void verCategorias(){
        optionsU = new FirebaseRecyclerOptions.Builder<CategoriaU>().setQuery(databaseReference, CategoriaU.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CategoriaU, ViewHolderCU>(optionsU) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCU holder, int position, @NonNull CategoriaU model) {
                holder.StorageCU(
                        getApplicationContext(),
                        model.getCategoria(),
                        model.getImg()

                );
            }

            @NonNull
            @Override
            public ViewHolderCU onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorias_dispositivo,parent, false);
                ViewHolderCU viewHolderCU =new ViewHolderCU(itemView);

                viewHolderCU.setOnClickListener(new ViewHolderCU.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String categoria = getItem(position).getCategoria();

                        Intent intent = new Intent(view.getContext(), ConceptosUsuario.class);
                        intent.putExtra("categoria",categoria);
                        startActivity(intent);
                        Toast.makeText(InicioCliente.this, categoria, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolderCU;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();
        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
    }
}