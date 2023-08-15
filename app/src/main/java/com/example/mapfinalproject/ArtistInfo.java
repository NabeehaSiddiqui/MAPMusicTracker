package com.example.mapfinalproject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.example.mapfinalproject.Artist;

public class ArtistInfo {
    @SerializedName("artists")
    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
