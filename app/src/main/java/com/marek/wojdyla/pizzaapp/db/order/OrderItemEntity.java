package com.marek.wojdyla.pizzaapp.db.order;

import com.marek.wojdyla.pizzaapp.db.pizza.PizzaEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "order_item",
        foreignKeys = {
                @ForeignKey(
                        entity = OrderEntity.class,
                        parentColumns = "order__id",
                        childColumns = "order_item__order_id"
                ),
                @ForeignKey(
                        entity = PizzaEntity.class,
                        parentColumns = "pizza__id",
                        childColumns = "order_item__pizza_id"
                )
        },
        indices = {
                @Index("order_item__order_id"),
                @Index("order_item__pizza_id"),
        }
)
public class OrderItemEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_item__id")
    public long id;
    @ColumnInfo(name = "order_item__order_id")
    public long orderId;
    @ColumnInfo(name = "order_item__pizza_id")
    public long pizzaId;

    public OrderItemEntity() {
    }

    @Ignore
    public OrderItemEntity(long orderId, long pizzaId) {
        this.orderId = orderId;
        this.pizzaId = pizzaId;
    }
}
