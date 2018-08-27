package com.example.meet16;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Игорь on 22.07.2018.
 */
@Database(entities = {Calculator.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract CalculatorDao calculatorDao();
}
