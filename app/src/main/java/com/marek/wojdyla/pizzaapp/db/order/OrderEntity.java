package com.marek.wojdyla.pizzaapp.db.order;

import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "order",
        foreignKeys = @ForeignKey(
                entity = RestaurantEntity.class,
                parentColumns = "restaurant__id",
                childColumns = "order__restaurant_id"
        ),
        indices = @Index("order__restaurant_id")
)
public class OrderEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order__id")
    public long id;
    @ColumnInfo(name = "order__restaurant_id")
    public long restaurantId;
    @ColumnInfo(name = "order__is_delivery")
    public boolean isDelivery;
    @ColumnInfo(name = "order__is_paid")
    public boolean isPaid;
    @ColumnInfo(name = "order__total")
    public double total;

    public OrderEntity() {
    }

    @Ignore
    public OrderEntity(long restaurantId, boolean isDelivery, double total) {
        this.restaurantId = restaurantId;
        this.isDelivery = isDelivery;
        this.total = total;
    }
}
