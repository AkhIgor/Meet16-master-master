package com.example.meet16;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Игорь on 22.07.2018.
 */
@Entity
public class Calculator {

    @PrimaryKey
    public long ID;
    public long result;
    public String operation;

    public Calculator(long ID, long result, String operation) {
        this.ID = ID;
        this.result = result;
        this.operation = operation;
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;

        if(obj == null)
            return false;

        if(!(getClass() == obj.getClass()))
            return false;
        else
        {
            Calculator calc = (Calculator) obj;
            if(calc.ID == this.ID && calc.operation.equals(this.operation) && calc.result == this.result)
                return true;
            else
                return false;
        }
    }

}
