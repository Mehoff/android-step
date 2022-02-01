package com.step.androidstep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int counter;
    public MainActivity(){
        counter = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView counterTextView = findViewById(R.id.textView_counter);
        Button incrementButton = findViewById(R.id.button_increment);
        Button decrementButton = findViewById(R.id.button_decrement);

        incrementButton.setOnClickListener(e -> { incrementCounter(); updateTextViewText(counterTextView); });
        decrementButton.setOnClickListener(e -> { decrementCounter(); updateTextViewText(counterTextView); });
    }

    private void incrementCounter(){
        this.counter += 1;
    }

    private void decrementCounter(){
        this.counter -= 1;
    }

    public void updateTextViewText(TextView view){
        try{
            view.setText(this.counter + "");
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
    }

}