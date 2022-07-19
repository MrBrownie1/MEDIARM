package com.example.mediarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

import java.net.URL;

import kotlin.Suppress;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.Img1);

    }

}