package com.example.mapfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class ArtistSearch extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText userSearch;
    String userInput;
    Button search, back, favorites;
    Executor executor;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_artistsearch);

        recyclerView = findViewById(R.id.listView);
        userSearch = findViewById(R.id.searchEditText);
        back = findViewById(R.id.back);
        search = findViewById(R.id.searchButton);
        favorites = findViewById(R.id.favs);
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "FavArtist-DB")
                .fallbackToDestructiveMigration()
                .build();
        executor = Executors.newSingleThreadExecutor();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = userSearch.getText().toString();
                if (!userInput.isEmpty()) {
                    onSearchButtonClick(v);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Unsuccessful - Please enter valid values", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observeFavorites();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onSearchButtonClick(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ResponseAPI response = retrofit.create(ResponseAPI.class);

        Call<ArtistInfo> result = response.getArtist(
                "application/json",
                "MAPFinalProject/1.0.0 (nabeeha.siddiqui@gmail.com)",
                userInput,
                5,
                "json"
        );

        result.enqueue(new Callback<ArtistInfo>() {
            @Override
            public void onResponse(Call<ArtistInfo> call, Response<ArtistInfo> response) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Unsuccessful - Please retry", Toast.LENGTH_LONG).show();
                                }
                            });
                            return;
                        } else {
                            List<Artist> artistList = response.body().getArtists();
                            ArtistAdapter artistAdapter = new ArtistAdapter(artistList, appDatabase.favoriteArtistDao(), executor);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(artistAdapter);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<ArtistInfo> call, Throwable t) {
                Log.e("Artist Search", "API Call Failed: " + t.getMessage());
            }
        });
    }

    private void observeFavorites() {
        FavoritesViewModel viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        viewModel.init(appDatabase.favoriteArtistDao(), appDatabase.favoriteAlbumDao());

        viewModel.getFavoriteArtistsLiveData().observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> favArtists) {
                ArtistAdapter artistAdapter = new ArtistAdapter(favArtists, appDatabase.favoriteArtistDao(), executor);
                recyclerView.setAdapter(artistAdapter);
            }
        });
    }
}