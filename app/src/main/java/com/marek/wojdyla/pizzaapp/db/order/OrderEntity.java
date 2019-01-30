package com.marek.wojdyla.pizzaapp.db.order;

import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "order",
        foreignKeys = @ForeignKey(
                entity = RestaurantEntity.class,
                parentColumns = "restaurant__id",
                childColumns = "order__restaurant_id"
        ),
        indices = @Index("restaurant_id")
)
public class OrderEntity {
    @ColumnInfo(name = "order__id")
    long id;
    @ColumnInfo(name = "order__restaurant_id")
    long restaurantId;
    @ColumnInfo(name = "order__is_delivery")
    boolean isDelivery;
    @ColumnInfo(name = "order__total")
    double total;

    public OrderEntity() {
    }

    public OrderEntity(long restaurantId, boolean isDelivery) {
        this.restaurantId = restaurantId;
        this.isDelivery = isDelivery;
    }
}
