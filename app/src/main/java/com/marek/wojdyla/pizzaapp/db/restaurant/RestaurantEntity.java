package com.marek.wojdyla.pizzaapp.db.restaurant;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant")
public class RestaurantEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "restaurant__id")
    public long id;
    @ColumnInfo(name = "restaurant__name")
    public String name;
    @ColumnInfo(name = "restaurant__has_delivery")
    public boolean hasDelivery;
    @ColumnInfo(name = "restaurant__city")
    public String city;

}
