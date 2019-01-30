package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.Embedded;

public class Pizza {
    @Embedded
    public PizzaEntity pizza;
    @Embedded
    public BaseEntity pizzaBase;
}
