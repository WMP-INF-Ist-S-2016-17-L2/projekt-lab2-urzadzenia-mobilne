package com.marek.wojdyla.pizzaapp.db.restaurant;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "restaurant")
public class Restaurant {
    @ColumnInfo(name = "restaurant__id")
    long id;
    @ColumnInfo(name = "restaurant__name")
    String name;
    @ColumnInfo(name = "restaurant__has_delivery")
    boolean hasDelivery;
    @ColumnInfo(name = "restaurant__city")
    boolean city;

}
