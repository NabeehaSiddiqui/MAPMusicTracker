package com.example.mapfinalproject;

import androidx.core.location.LocationRequestCompat;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavArtistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertArtist(Artist artist);

    @Query("select * from favorite_artists")
    LiveData<List<Artist>> getAllArtists();
}
