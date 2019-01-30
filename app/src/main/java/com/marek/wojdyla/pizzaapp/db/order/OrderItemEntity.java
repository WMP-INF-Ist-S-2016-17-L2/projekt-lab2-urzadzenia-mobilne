package com.marek.wojdyla.pizzaapp.db.order;

import com.marek.wojdyla.pizzaapp.db.pizza.PizzaEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.ToppingEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;


@Entity(
        tableName = "order_item",
        foreignKeys = {
                @ForeignKey(
                        entity = OrderEntity.class,
                        parentColumns = "order_id",
                        childColumns = "order_item__order_id"
                ),
                @ForeignKey(
                        entity = PizzaEntity.class,
                        parentColumns = "pizza_id",
                        childColumns = "order_item__pizza_id"
                ),
                @ForeignKey(
                        entity = ToppingEntity.class,
                        parentColumns = "topping_id",
                        childColumns = "order_item__topping_id"
                )
        },
        indices = {
                @Index("order_item__order_id"),
                @Index("order_item__pizza_id"),
                @Index("order_item__topping_id"),
        }
)
public class OrderItemEntity {
    @ColumnInfo(name = "order_item__id")
    public long id;
    @ColumnInfo(name = "order_item__order_id")
    public long orderId;
    @ColumnInfo(name = "order_item__pizza_id")
    public long pizzaId;
    @ColumnInfo(name = "order_item__topping_id")
    public long toppingId;
}
