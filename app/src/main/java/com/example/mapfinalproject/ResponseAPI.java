package com.example.mapfinalproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ResponseAPI {
    @GET("artist")
    Call<ArtistInfo> getArtist(@Header("Accept") String acceptHeader,
                               @Header("User-Agent") String userAgent,
                               @Query("query") String artistQuery,
                               @Query("limit") int limit,
                               @Query("fmt") String format);

    @GET("release-group")
    Call<AlbumInfo> getAlbums(@Header("Accept") String acceptHeader,
                              @Header("User-Agent") String userAgent,
                              @Query("query") String albumQuery,
                              @Query("limit") int limit,
                              @Query("fmt") String format);
}
