package com.example.mediarm.ImgStorage;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediarm.Login;
import com.example.mediarm.MainActivityAdmi;
import com.example.mediarm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

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

                        final String nombre = getItem(position).getNombre();
                        final String imagen = getItem(position).getImg();
                        final String concepto = getItem(position).getConcepto();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ImgConceptos.this);
                        String[] opciones = {"Eliminar"};
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0){
                                    EliminarImagen(nombre, concepto, imagen);
                                }
                                if (i==1){
                                    Intent intent = new Intent(ImgConceptos.this, AgregarConceptos.class);
                                    intent.putExtra("NombreEnviado", nombre);
                                    intent.putExtra("ImagenEnviada", imagen);
                                    intent.putExtra("ConceptoEnviado", concepto);
                                    startActivity(intent);

                                }
                            }
                        });
                        builder.create().show();
                    }
                });
                return viewHolderConceptos;
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(ImgConceptos.this, 2));
        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void EliminarImagen(final String NombreActual, final String DescripcionActual, final String ImagenActual){
        AlertDialog.Builder builder = new AlertDialog.Builder(ImgConceptos.this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Desea eliminar la imagen?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query query = mRef.orderByChild("nombre").equalTo(NombreActual);
                Query query1 = mRef.orderByChild("concepto").equalTo(DescripcionActual);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(ImgConceptos.this, "La imagen ha sido eliminada", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ImgConceptos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                StorageReference ImagenSeleccionada = getInstance().getReferenceFromUrl(ImagenActual);
                ImagenSeleccionada.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ImgConceptos.this, "Eliminado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ImgConceptos.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ImgConceptos.this, "Cancelado por el administrador", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
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
        menuInflater.inflate(R.menu.menu_agregar, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Vista){
            startActivity(new Intent(ImgConceptos.this, MainActivityAdmi.class));
        }if (item.getItemId() == R.id.Agregar) {
            startActivity(new Intent(ImgConceptos.this, AgregarConceptos.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}