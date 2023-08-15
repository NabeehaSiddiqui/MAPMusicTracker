package com.example.mapfinalproject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumInfo {
    @SerializedName("release-groups")
    private List<Album> albums;

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
