package com.marek.wojdyla.pizzaapp.db.order;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OrderDao {

    @Insert
    LiveData<OrderEntity> createOrder(OrderEntity orderEntity);

    @Query("SELECT * FROM order_item WHERE order_item__order_id = :orderId")
    LiveData<OrderItemEntity> getItems(long orderId);

    @Insert
    void insert(OrderItemEntity item);

    @Delete
    void delete(OrderItemEntity item);
}
