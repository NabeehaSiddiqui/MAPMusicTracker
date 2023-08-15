package com.example.mapfinalproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private LiveData<List<Artist>> favoriteArtistsLiveData;
    private LiveData<List<Album>> favoriteAlbumsLiveData;

    public void init(FavArtistDao favArtistDao, FavAlbumDao favAlbumDao) {
        favoriteArtistsLiveData = favArtistDao.getAllArtists();
        favoriteAlbumsLiveData = favAlbumDao.getAllAlbums();
    }

    public LiveData<List<Artist>> getFavoriteArtistsLiveData() {
        return favoriteArtistsLiveData;
    }

    public LiveData<List<Album>> getFavoriteAlbumsLiveData() {
        return favoriteAlbumsLiveData;
    }
}
