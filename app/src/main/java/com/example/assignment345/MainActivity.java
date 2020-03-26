package com.example.assignment345;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.assignment345.datastore.DatabaseHelper;
import com.example.assignment345.datastore.models.Meal;
import com.example.assignment345.datastore.models.Order;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    int[] images = {
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3,
            R.drawable.b4,
            R.drawable.b5,
            R.drawable.b6,
            R.drawable.b7,
            R.drawable.b8,
            R.drawable.b9,
            R.drawable.b10
    };
    Spinner meal;
    EditText txtPrice;
    SeekBar seekQuantity;
    EditText totalPrice;
    ImageView imageView;
    ToggleButton toggleButton2;
    RadioGroup tipGroup;
    RadioButton rb10;
    RadioButton rb15;
    RadioButton rb20;
    Double tipRate = 0.0;
    Double price = 0.0;
    Double totalPriceValue = 0.0;
    int quantity = 0;
    DatabaseHelper dbHelper;
    int meal_id = 0;
    Double tax = 0.13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meal = findViewById(R.id.meal);
        txtPrice = findViewById(R.id.txtPrice);
        seekQuantity = findViewById(R.id.seekQuantity);
        totalPrice = findViewById(R.id.totalPrice);
        imageView = findViewById(R.id.imageView);
        toggleButton2 = findViewById(R.id.toggleButton2);
        tipGroup = findViewById(R.id.tipGroup);
        rb10 = findViewById(R.id.rb10);
        rb15 = findViewById(R.id.rb15);
        rb20 = findViewById(R.id.rb20);
        //Filling spinner with meals from db
        dbHelper = DatabaseHelper.getInstance(this);
        List<Meal> meals = dbHelper.getAllMeals();
        ArrayAdapter<Meal> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                meals
        );
        meal.setAdapter(adapter);
        meal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Meal selMeal = (Meal) meal.getSelectedItem();
                meal_id = selMeal.getId();
                price = selMeal.getPrice();
                imageView.setImageResource(images[(int) meal.getSelectedItemId()]);
                calculateAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tipGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb10.isChecked()){
                    tipRate = 0.10;
                } else if (rb15.isChecked()) {
                    tipRate = 0.15;
                } else if (rb20.isChecked()) {
                    tipRate = 0.20;
                } else {
                    tipRate = 0.0;
                }
                calculateAll();
            }
        });
        seekQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantity = progress;
                calculateAll();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void calculateAll () {
        txtPrice.setText("$ "+ price);
        Double priceWOTip = price * quantity;
        totalPriceValue = priceWOTip + priceWOTip * tipRate;
        totalPriceValue = totalPriceValue + totalPriceValue * tax;
        totalPrice.setText("$ "+ totalPriceValue);
        Log.i("MAIN", "Calculated "+ price + " " + quantity + " " + tipRate);
    }

    private void resetAll() {
        totalPriceValue = 0.0;
        meal_id = 0;
        quantity =0;
        tipRate = 0.0;
        txtPrice.setText("");
        totalPrice.setText("");
        seekQuantity.setProgress(0);
        tipGroup.clearCheck();
        toggleButton2.setChecked(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resetAll();
    }

    public void order(View view) {
        if (tipRate == 0.0 || price == 0.0 || quantity == 0 || toggleButton2.getText().toString().toUpperCase().equals("DECLINE")) {
            Toast.makeText(this, "Please make sure all the mandatory fields are filled", Toast.LENGTH_SHORT).show();
        } else {
            Order order = new Order(0, totalPriceValue, meal_id, quantity, tipRate);
            Log.i("DbHelper", "createOrder: Execution here");
            Toast.makeText(this, "All good!! Saving 3.. 2.. 1..", Toast.LENGTH_SHORT).show();
            long i = dbHelper.createOrder(order);
            if( i != -1) {
                Intent intent = new Intent(this, ListOrderActivity.class);
                this.startActivity(intent);
            } else {
                Toast.makeText(this, "not saved"+ i, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
