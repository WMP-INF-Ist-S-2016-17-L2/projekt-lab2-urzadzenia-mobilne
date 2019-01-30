package com.marek.wojdyla.pizzaapp.db.order;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "order__order_item",
        foreignKeys = {
                @ForeignKey(
                        entity = OrderEntity.class,
                        parentColumns = "order_id",
                        childColumns = "order_with_order_item__order_id"
                ),
                @ForeignKey(
                        entity = OrderItemEntity.class,
                        parentColumns = "order_item_id",
                        childColumns = "order_with_order_item__order_item_id"
                )
        },
        indices = {
                @Index("order_with_order_item__order_id"),
                @Index("order_with_order_item__order_item_id")
        }
)
public class OrderWithOrderItemEntity {
    @ColumnInfo(name = "order_with_order_item__order_id")
    long orderId;
    @ColumnInfo(name = "order_with_order_item__order_item_id")
    long orderItemId;
}
