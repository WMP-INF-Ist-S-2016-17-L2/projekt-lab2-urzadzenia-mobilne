package com.marek.wojdyla.pizzaapp.db.order;

import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OrderDao {

    @Query("SELECT " +
            "pizza.*, " +
            "(" +
            "SELECT SUM(topping.topping__price) " +
            "FROM pizza_with_topping " +
            "LEFT JOIN topping " +
            "ON topping.topping__id = pizza_with_topping.pizza_with_topping__topping_id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = pizza.pizza__id " +
            ") AS price " +
            "FROM pizza " +
            "LEFT JOIN order_item " +
            "ON order_item.order_item__pizza_id = pizza.pizza__id " +
            "WHERE order_item.order_item__order_id = :orderId")
    LiveData<List<PizzaWithPrice>> getPizzasForOrder(long orderId);

    @Query("SELECT * FROM `order` " +
            "LEFT JOIN restaurant ON restaurant.restaurant__id = order__restaurant_id " +
            "WHERE order__id = :orderId")
    LiveData<Order> getOrder(long orderId);

    @Query("SELECT * FROM `order` " +
            "LEFT JOIN restaurant ON restaurant.restaurant__id = order__restaurant_id ")
    LiveData<List<Order>> getOrders();

    @Query("UPDATE `order` SET order__is_paid = 1 WHERE order__id = :orderId")
    void payForOrder(long orderId);

    @Insert
    long createOrder(OrderEntity orderEntity);

    @Insert
    void insert(OrderItemEntity item);
}
