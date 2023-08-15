package com.example.mapfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button artistSearch, albumSearch;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        setClickListeners();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setClickListeners();
    }

    private void setClickListeners() {
        artistSearch = findViewById(R.id.artistSearch);
        albumSearch = findViewById(R.id.albumSearch);

        artistSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ArtistSearch.class);
                startActivity(intent);
            }
        });

        albumSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlbumSearch.class); // Change to the appropriate activity class
                startActivity(intent);
            }
        });
    }
}