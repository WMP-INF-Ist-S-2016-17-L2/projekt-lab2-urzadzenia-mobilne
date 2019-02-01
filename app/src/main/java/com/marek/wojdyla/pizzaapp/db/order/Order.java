package com.marek.wojdyla.pizzaapp.db.order;

import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantEntity;

import androidx.room.Embedded;

public class Order {
    @Embedded
    public OrderEntity order;

    @Embedded
    public RestaurantEntity restaurant;
}
