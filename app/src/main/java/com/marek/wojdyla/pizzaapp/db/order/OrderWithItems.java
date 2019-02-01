package com.marek.wojdyla.pizzaapp.db.order;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class OrderWithItems {
    @Embedded
    OrderEntity orderEntity;
    @Relation(
            entity = OrderItemEntity.class,
            parentColumn = "order_item__order_id",
            entityColumn = "order__id"
    )
    List<OrderItemEntity> items;
}
