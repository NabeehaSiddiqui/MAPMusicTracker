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


public class AlbumSearch extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText artistName, albumTitle;
    String inputName, inputTitle;
    Button search, back, favorites;
    Executor executor;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumsearch);

        recyclerView = findViewById(R.id.listView);
        artistName = findViewById(R.id.artistName);
        albumTitle = findViewById(R.id.albumTitle);
        back = findViewById(R.id.back);
        search = findViewById(R.id.searchButton);
        favorites = findViewById(R.id.favs);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "FavAlbums-DB")
                .fallbackToDestructiveMigration()
                .build();
        executor = Executors.newSingleThreadExecutor();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName = artistName.getText().toString();
                inputTitle = albumTitle.getText().toString();

                if (!inputName.isEmpty() && !inputTitle.isEmpty()) {
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

        String query = "artist:" + inputName + " AND title:" + inputTitle + " AND primarytype:album";

        Call<AlbumInfo> result = response.getAlbums(
                "application/json",
                "MAPFinalProject/1.0.0 (nabeeha.siddiqui@gmail.com)",
                query,
                8,
                "json"
        );

        result.enqueue(new Callback<AlbumInfo>() {
            @Override
            public void onResponse(Call<AlbumInfo> call, Response<AlbumInfo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Unsuccessful - Please retry", Toast.LENGTH_LONG).show();
                    return;
                } else {
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
                                List<Album> albumList = response.body().getAlbums();
                                AlbumAdapter artistAdapter = new AlbumAdapter(albumList, appDatabase.favoriteAlbumDao(), executor);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlbumAdapter albumAdapter = new AlbumAdapter(albumList, appDatabase.favoriteAlbumDao(), executor);
                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<AlbumInfo> call, Throwable t) {
                Log.e("Album Search", "API Call Failed: " + t.getMessage());
            }
        });
    }

    private void observeFavorites() {
        FavoritesViewModel viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        viewModel.init(appDatabase.favoriteArtistDao(), appDatabase.favoriteAlbumDao());

        viewModel.getFavoriteAlbumsLiveData().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> favAlbums) {
                AlbumAdapter albumAdapter = new AlbumAdapter(favAlbums, appDatabase.favoriteAlbumDao(), executor);
                recyclerView.setAdapter(albumAdapter);
            }
        });
    }
}