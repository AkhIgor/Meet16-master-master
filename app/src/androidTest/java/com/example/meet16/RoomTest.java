package com.example.meet16;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Игорь on 25.07.2018.
 */

@RunWith(JUnit4.class)
public class RoomTest {

    private CalculatorDao calculatorDao;
    private MyDataBase myDataBase;

    @Before
    public void createDb() throws Exception {

        Context context = InstrumentationRegistry.getTargetContext();
        myDataBase = Room.inMemoryDatabaseBuilder(context, MyDataBase.class).build();
        calculatorDao = myDataBase.calculatorDao();
    }


    @After
    public void closeDb() throws IOException {
        myDataBase.close();
    }

    @Test
    public void writeRead() throws Exception {
        Calculator calculator = new Calculator(1, 75689, "subtraction");
        calculatorDao.insert(calculator);
        List<Calculator> listCalc = Collections.singletonList(calculatorDao.getLast());

        assertThat(listCalc.get(0), equalTo(calculator));
    }
}