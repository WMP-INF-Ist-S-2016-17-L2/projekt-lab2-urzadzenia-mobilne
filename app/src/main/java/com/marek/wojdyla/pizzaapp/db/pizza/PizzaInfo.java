package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.Embedded;

public class PizzaInfo {
    @Embedded
    public PizzaEntity pizza;
    @Embedded
    public BaseEntity pizzaBase;
}
