package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.Embedded;

public class PizzaWithPrice {
    @Embedded
    public PizzaEntity pizza;

    public double price;
}
