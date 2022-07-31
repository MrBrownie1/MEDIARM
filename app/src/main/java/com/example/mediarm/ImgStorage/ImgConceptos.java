package com.example.mediarm.ImgStorage;

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

import com.example.mediarm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImgConceptos extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Conceptos, ViewHolderConceptos> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Conceptos> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_conceptos);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Imagenes");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

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
                        Toast.makeText(ImgConceptos.this,"Item Click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnItemLongClick(View view, int position) {
                        Toast.makeText(ImgConceptos.this, "Long click", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolderConceptos;
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(ImgConceptos.this, 2));
        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
        menuInflater.inflate(R.menu.menu_agregar, menu);
        menuInflater.inflate(R.menu.menu_vista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Agregar:
                startActivity(new Intent(ImgConceptos.this,AgregarConceptos.class));
                break;
            case R.id.Vista:
                Toast.makeText(this, "Listar Imagenes", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}