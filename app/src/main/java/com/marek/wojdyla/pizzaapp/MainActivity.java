package com.marek.wojdyla.pizzaapp;

import android.content.Intent;
import android.os.Bundle;

import com.marek.wojdyla.pizzaapp.order.list.OrderListActivity;
import com.marek.wojdyla.pizzaapp.restaurant.RestaurantListActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.main_delivery).setOnClickListener(v -> showList(true));
        findViewById(R.id.main_pickup).setOnClickListener(v -> showList(false));
        findViewById(R.id.main_myOrders).setOnClickListener(v -> showMyOrders());
    }

    private void showMyOrders() {
        startActivity(new Intent(this, OrderListActivity.class));
    }

    private void showList(boolean isDelivery) {
        startActivity(RestaurantListActivity.IntentFactory.create(this, isDelivery));
    }
}
