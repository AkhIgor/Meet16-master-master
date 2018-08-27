package com.example.meet16;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button OneButton, TwoButton, ThreeButton, FourButton, FiveButton, SixButton, SevenButton, EightButton, NineButton, ZeroButton, AddButton, DivideButton, MultiButton, SubButtton, ClearButton, EquallyButton;
    private TextView ResultText;

    private boolean DB = false;
    private boolean New = true;
    private boolean MathOp = true;

    private static long Result;
    private CalculatorDao calc;

    private String Operation;
    private long Previous;
    private MyHandler myHandler;

    private static final int WRITE_RESULT = 0;
    private static final int READ_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDataBase db = Room.databaseBuilder
                        (getApplicationContext(),
                                MyDataBase.class,
                                "Calculator_db")
                        .fallbackToDestructiveMigration()
                        .build();

                calc = db.calculatorDao();

                if (calc.getLast() != null)
                    calc.delete(calc.getLast());
            }
        }).start();

        myHandler = new MyHandler();

        ResultText = (TextView) findViewById(R.id.resultView);

        OneButton = (Button) findViewById(R.id.button);
        TwoButton = (Button) findViewById(R.id.button2);
        ThreeButton = (Button) findViewById(R.id.button3);
        FourButton = (Button) findViewById(R.id.button5);
        FiveButton = (Button) findViewById(R.id.button6);
        SixButton = (Button) findViewById(R.id.button7);
        SevenButton = (Button) findViewById(R.id.button8);
        EightButton = (Button) findViewById(R.id.button9);
        NineButton = (Button) findViewById(R.id.button10);
        ZeroButton = (Button) findViewById(R.id.button4);

        AddButton = (Button) findViewById(R.id.button11);
        SubButtton = (Button) findViewById(R.id.button13);
        MultiButton = (Button) findViewById(R.id.button12);
        DivideButton = (Button) findViewById(R.id.button14);

        ClearButton = (Button) findViewById(R.id.button15);
        EquallyButton = (Button) findViewById(R.id.button17);
    }

    public void addition(View view) {
        if (!MathOp) {
            mathOperation("addition");
            MathOp = true;
        }
    }

    public void subtraction(View view) {
        if (!MathOp) {
            mathOperation("subtraction");
            MathOp = true;
        }
    }

    public void multiplication(View view) {
        if (!MathOp) {
            mathOperation("multiplication");
            MathOp = true;
        }
    }

    public void divide(View view) {
        if (!MathOp) {
            mathOperation("divide");
            MathOp = true;
        }
    }

    @SuppressLint("SetTextI18n")
    private void mathOperation(String operation) {
        Operation = operation;
        Result = Long.parseLong(ResultText.getText().toString());
        if (DB) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    switch (calc.getLast().operation) {
                        case "addition": {
                            Result = calc.getLast().result + Result;
                            break;
                        }
                        case "subtraction": {
                            Result = calc.getLast().result - Result;
                            break;
                        }
                        case "multiplication": {
                            Result = calc.getLast().result * Result;
                            break;
                        }
                        case "divide": {
                            Result = calc.getLast().result / Result;
                            break;
                        }
                    }

                    myHandler.sendEmptyMessage(WRITE_RESULT);

                    Calculator calculator = new Calculator(1, Result, Operation);
                    calc.delete(calc.getLast());
                    calc.insert(calculator);
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Calculator calculator = new Calculator(1, Result, Operation);
                    calc.insert(calculator);
                }
            }).start();
        }
        DB = true;
        New = true;
    }

    public void FirstDigit(View view) {
        write("1");
        MathOp = false;
    }

    public void SecondDigit(View view) {
        write("2");
        MathOp = false;
    }

    public void ThirdDigit(View view) {
        write("3");
        MathOp = false;
    }

    public void FourthDigit(View view) {
        write("4");
        MathOp = false;
    }

    public void FifthDigit(View view) {
        write("5");
        MathOp = false;
    }

    public void SixthDigit(View view) {
        write("6");
        MathOp = false;
    }

    public void SeventhDigit(View view) {
        write("7");
        MathOp = false;
    }

    public void EighthDigit(View view) {
        write("8");
        MathOp = false;
    }

    public void NinthDigit(View view) {
        write("9");
        MathOp = false;
    }

    public void nullDigit(View view) {
        write("0");
        MathOp = false;
    }

    private void write(String digit) {
        if (!New) {
            String newString = ResultText.getText().toString() + digit;
            ResultText.setText(newString);
        } else {
            New = false;
            ResultText.setText(digit);
        }
    }

    public void clear(View view) {
        if (DB) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    calc.delete(calc.getLast());
                    DB = false;
                }
            }).start();
        }
        ResultText.setText("");
        New = true;
        MathOp = true;
    }

    @SuppressLint("SetTextI18n")
    public void equally(View view) {

        if (DB) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myHandler.sendEmptyMessage(READ_RESULT);

                    switch (calc.getLast().operation) {
                        case "addition": {
                            Result = calc.getLast().result + Result;
                            break;
                        }
                        case "subtraction": {
                            Result = calc.getLast().result - Result;
                            break;
                        }
                        case "multiplication": {
                            Result = calc.getLast().result * Result;
                            break;
                        }
                        case "divide": {
                            Result = calc.getLast().result / Result;
                            break;
                        }
                    }

                    myHandler.sendEmptyMessage(WRITE_RESULT);

                    calc.delete(calc.getLast());
                    DB = false;
                }
            }).start();
        }

        New = true;
        MathOp = true;
    }

    class MyHandler extends Handler {

        //@SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WRITE_RESULT)
                ResultText.setText(Long.toString(Result));
            if (msg.what == READ_RESULT)
                Result = Long.parseLong(ResultText.getText().toString());
        }
    }
}
