package com.marek.wojdyla.pizzaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        PizzaInfoViewModel viewModel = ViewModelProviders
//                .of(this, new PizzaViewModelFactory(getApplication(), 1))
//                .get(PizzaInfoViewModel.class);
//
//        viewModel.getPizza().observe(this, pizza -> {
//            int x =23;
//            int y = x;
//        });
    }
}
