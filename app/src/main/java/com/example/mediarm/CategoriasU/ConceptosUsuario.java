package com.example.mediarm.CategoriasU;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediarm.Cliente.InicioCliente;
import com.example.mediarm.ImgStorage.AgregarConceptos;
import com.example.mediarm.ImgStorage.Conceptos;
import com.example.mediarm.ImgStorage.ImgConceptos;
import com.example.mediarm.ImgStorage.ViewHolderConceptos;
import com.example.mediarm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConceptosUsuario extends AppCompatActivity {

    RecyclerView recyclerViewC;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Conceptos, ViewHolderConceptos> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Conceptos> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceptos_usuario);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Imagenes");

        recyclerViewC= findViewById(R.id.recyclerViewC);
        recyclerViewC.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("CONCEPTOS");

        ListarImagenes();
    }
    private void ListarImagenes(){
        options = new FirebaseRecyclerOptions.Builder<Conceptos>().setQuery(mRef, Conceptos.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Conceptos, ViewHolderConceptos>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderConceptos viewHolderConceptos, int i, @NonNull Conceptos conceptos) {
                viewHolderConceptos.StorageConcepto(
                        getApplicationContext(),
                        conceptos.getImg(),
                        conceptos.getNombre(),
                        conceptos.getConcepto()

                );
            }

            @NonNull
            @Override
            public ViewHolderConceptos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_concepto,parent, false);

                ViewHolderConceptos viewHolderConceptos= new ViewHolderConceptos(itemView);

                viewHolderConceptos.setOnClickListener(new ViewHolderConceptos.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(ConceptosUsuario.this,"Item Click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnItemLongClick(View view, int position) {
                    }
                });
                return viewHolderConceptos;
            }
        };
        recyclerViewC.setLayoutManager(new GridLayoutManager(ConceptosUsuario.this, 2));
        firebaseRecyclerAdapter.startListening();

        recyclerViewC.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_vista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Vista) {
            startActivity(new Intent(ConceptosUsuario.this, InicioCliente.class));
            Toast.makeText(this, "Listar Imagenes", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}