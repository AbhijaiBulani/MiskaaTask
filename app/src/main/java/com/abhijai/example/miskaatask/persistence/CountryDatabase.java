package com.abhijai.example.miskaatask.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abhijai.example.miskaatask.models.CountryResponse;

@Database(entities = CountryResponse.class,version = 2,exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "country_db";
    private static CountryDatabase countryDatabaseInstance;
    public static CountryDatabase getDbInstance(Context context){
        if (countryDatabaseInstance == null){
            countryDatabaseInstance = Room.databaseBuilder(context,CountryDatabase.class,DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return countryDatabaseInstance;
    }
    public abstract CountryDAO getCountryDAO();
}
