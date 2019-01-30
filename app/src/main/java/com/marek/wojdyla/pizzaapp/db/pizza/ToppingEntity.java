package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "topping"
)
public class ToppingEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "topping__id")
    public long id;
    @ColumnInfo(name = "topping__name")
    public String name;
    @ColumnInfo(name = "topping__price")
    public float price;
}
