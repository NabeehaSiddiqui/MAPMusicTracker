package com.example.mapfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapfinalproject.Artist;


import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> albums;
    private FavAlbumDao albumDao;
    private Executor executor;

    public AlbumAdapter(List<Album> albums, FavAlbumDao albumDao, Executor executor) {
        this.albums = albums;
        this.albumDao = albumDao;
        this.executor = executor;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_list_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.bind(album);
        holder.albumTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add to Favorites")
                        .setMessage("Do you want to add " + album.getAlbumName() + " to favorites?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        albumDao.insertAlbum(album);
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
        return albums.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private TextView albumTitleTextView, releaseDateTextView, albumScoreTextView, artistNameTextView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            albumTitleTextView = itemView.findViewById(R.id.albumTitle);
            releaseDateTextView = itemView.findViewById(R.id.releaseDate);
            albumScoreTextView = itemView.findViewById(R.id.albumRating);
        }

        public void bind(Album album) {
            albumTitleTextView.setText(album.getAlbumName());
            albumScoreTextView.setText(String.valueOf(album.getRating()));
            releaseDateTextView.setText(album.getReleaseDate());
        }
    }
}
