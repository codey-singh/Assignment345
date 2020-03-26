package com.example.assignment345;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.assignment345.adapters.OrdersArrayAdapter;
import com.example.assignment345.datastore.DatabaseHelper;
import com.example.assignment345.datastore.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ListOrderActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        dbHelper = DatabaseHelper.getInstance(this);
        listView = findViewById(R.id.orders_list);
        ArrayList<Order> orders = (ArrayList<Order>) dbHelper.getAllOrders();
        OrdersArrayAdapter ordersArrayAdapter = new OrdersArrayAdapter(orders, this);
        listView.setAdapter(ordersArrayAdapter);
    }
}
