package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.Embedded;

public class PizzaInfo extends PizzaWithPrice {
    @Embedded
    public BaseEntity pizzaBase;
}
