package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Tokarka.class}, version = 1)
public abstract class MaszynyDataBase extends RoomDatabase {
    public abstract TokarkaDAO zwrocTokarkaDAO();
    private static MaszynyDataBase instancja;
    public static MaszynyDataBase zwrocBazeDanych(Context context){
        if (instancja == null){
            instancja = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MaszynyDataBase.class,
                    "maszyny_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instancja;
    }
}
