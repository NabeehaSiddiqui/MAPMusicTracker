package com.example.mapfinalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavAlbumDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlbum(Album album);

    @Query("select * from favorite_albums")
    LiveData<List<Album>> getAllAlbums();
}
