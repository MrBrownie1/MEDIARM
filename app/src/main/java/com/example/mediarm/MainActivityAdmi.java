package com.example.mediarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediarm.ImgStorage.ImgConceptos;
import com.example.mediarm.ImgStorage.InicioStorage;
import com.example.mediarm.ui.main.user;
import com.example.mediarm.ui.main.userAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class MainActivityAdmi extends AppCompatActivity {

    RecyclerView mRecycler;
    userAdapter mAdapter;
    FirebaseFirestore mFirestore;
    Button btnImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admi);

        btnImg = findViewById(R.id.btnSubirImg);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));


        Query query = mFirestore.collection("users");



        FirestoreRecyclerOptions<user> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<user>().setQuery(query, user.class).build();

        mAdapter = new userAdapter(firestoreRecyclerOptions,  this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityAdmi.this, ImgConceptos.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.startListening();
    }
}