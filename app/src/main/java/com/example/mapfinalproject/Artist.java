package com.example.mapfinalproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_artists")
public class Artist {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "artist_id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "artist_name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "artist_gender")
    @SerializedName("gender")
    private String gender;

    @ColumnInfo(name = "artist_country")
    @SerializedName("country")
    private String country = "Not Available";

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
