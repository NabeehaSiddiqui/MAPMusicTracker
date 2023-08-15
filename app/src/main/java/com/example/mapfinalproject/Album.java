package com.example.mapfinalproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "favorite_albums")
public class Album {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "album_id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "album_name")
    @SerializedName("title")
    private String albumName;

    @ColumnInfo(name = "release_date")
    @SerializedName("first-release-date")
    private String releaseDate;

    @ColumnInfo(name = "album_rating")
    @SerializedName("score")
    private int rating;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}

