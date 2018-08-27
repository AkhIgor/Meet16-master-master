package com.example.meet16;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by Игорь on 22.07.2018.
 */
@Dao
public interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Calculator calc);

    @Delete
    void delete(Calculator calc);

    @Query("SELECT * FROM Calculator WHERE id = 1")
    Calculator getLast();
}
