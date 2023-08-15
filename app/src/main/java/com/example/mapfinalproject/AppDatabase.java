package com.example.mapfinalproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Artist.class, Album.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavArtistDao favoriteArtistDao();

    public abstract FavAlbumDao favoriteAlbumDao();
}


