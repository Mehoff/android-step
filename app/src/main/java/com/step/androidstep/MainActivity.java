package com.step.androidstep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.step.androidstep.classes.Calculator;
import com.step.androidstep.enums.Action;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setupCalculator();
    }

    private void setupCalculator() {
        TextView calcExpression = findViewById(R.id.calc_expression);
        TextView calcResult = findViewById(R.id.calc_result);

        calculator = new Calculator(calcExpression, calcResult);

        Button button = findViewById(R.id.btn_0);
        calculator.addNumberButton(button, 0);

        button = findViewById(R.id.btn_1);
        calculator.addNumberButton(button, 1);

        button = findViewById(R.id.btn_2);
        calculator.addNumberButton(button, 2);

        button = findViewById(R.id.btn_3);
        calculator.addNumberButton(button, 3);

        button = findViewById(R.id.btn_4);
        calculator.addNumberButton(button, 4);

        button = findViewById(R.id.btn_5);
        calculator.addNumberButton(button, 5);

        button = findViewById(R.id.btn_6);
        calculator.addNumberButton(button, 6);

        button = findViewById(R.id.btn_7);
        calculator.addNumberButton(button, 7);

        button = findViewById(R.id.btn_8);
        calculator.addNumberButton(button, 8);

        button = findViewById(R.id.btn_9);
        calculator.addNumberButton(button, 9);

        button = findViewById(R.id.btn_equals);
        calculator.addActionButton(button, Action.EQUALS);

        button = findViewById(R.id.btn_plus);
        calculator.addActionButton(button, Action.PLUS);

        button = findViewById(R.id.btn_multiply);
        calculator.addActionButton(button, Action.MULTIPLY);

        button = findViewById(R.id.btn_minus);
        calculator.addActionButton(button, Action.MINUS);

        button = findViewById(R.id.btn_erase);
        calculator.addActionButton(button, Action.ERASE);

        button = findViewById(R.id.btn_divide);
        calculator.addActionButton(button, Action.DIVIDE);

        button = findViewById(R.id.btn_clear);
        calculator.addActionButton(button, Action.CLEAR);

        button = findViewById(R.id.btn_asterisk);
        calculator.addActionButton(button, Action.ASTERISK);
    }
}