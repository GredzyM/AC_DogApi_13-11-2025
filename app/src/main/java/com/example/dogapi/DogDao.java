package com.example.dogapi;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DogDao {

    @Insert
    void insert(Dog dog);

    @Update
    void update(Dog dog);

    @Delete
    void delete(Dog dog);

    @Query("SELECT * FROM dogs")
    List<Dog> getAllDogs();
}
