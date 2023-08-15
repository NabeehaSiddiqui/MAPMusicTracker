package com.example.mapfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {
    private List<Artist> artists;
    private FavArtistDao artistDao;

    private Executor executor;

    public ArtistAdapter(List<Artist> artists, FavArtistDao artistDao, Executor executor) {
        this.artists = artists;
        this.artistDao = artistDao;
        this.executor = executor;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.bind(artist);
        holder.artistNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add to Favorites")
                        .setMessage("Do you want to add " + artist.getName() + " to favorites?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        artistDao.insertArtist(artist);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        private TextView artistNameTextView;
        private TextView artistCountryTextView;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistNameTextView = itemView.findViewById(R.id.artistName);
            artistCountryTextView = itemView.findViewById(R.id.artistCountry);
        }

        public void bind(Artist artist) {
            artistNameTextView.setText(artist.getName());
            artistCountryTextView.setText(artist.getCountry());
        }
    }
}
