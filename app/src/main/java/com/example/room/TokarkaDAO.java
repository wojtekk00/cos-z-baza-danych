package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TokarkaDAO {
    @Insert
    void wstawTokarke(Tokarka tokarka);
    @Delete
    void usunTokarke(Tokarka tokarka);
    @Update
    void zmienParametryTokarki(Tokarka tokarka);
    @Query("SELECT * FROM tokareczki")
    List<Tokarka> zwrocWszystkieTokarki();

    @Query("SELECT model FROM tokareczki WHERE moc_silnika > :moc")
    List<String> zwrocNazwyTokarekOMocy(int moc);
}
