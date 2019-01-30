package com.marek.wojdyla.pizzaapp.db.pizza;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "pizza_with_topping",
        foreignKeys = {
                @ForeignKey(
                        entity = PizzaEntity.class,
                        parentColumns = "pizza__id",
                        childColumns = "pizza_with_topping__pizza_id"
                ),
                @ForeignKey(
                        entity = ToppingEntity.class,
                        parentColumns = "topping__id",
                        childColumns = "pizza_with_topping__topping_id"
                )
        },
        indices = {
                @Index("pizza_with_topping__pizza_id"),
                @Index("pizza_with_topping__topping_id"),
        }
)
public class PizzaWithToppingEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pizza_with_topping__id")
    public long id;
    @ColumnInfo(name = "pizza_with_topping__pizza_id")
    public long pizzaId;
    @ColumnInfo(name = "pizza_with_topping__topping_id")
    public long toppingId;
}
