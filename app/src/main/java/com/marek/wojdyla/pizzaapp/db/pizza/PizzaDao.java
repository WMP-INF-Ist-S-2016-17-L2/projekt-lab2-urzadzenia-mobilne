package com.marek.wojdyla.pizzaapp.db.pizza;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface PizzaDao {

    @Query("SELECT " +
            "pizza.*, " +
            "(SELECT " +
            "SUM(topping.topping__price)" +
            "FROM topping " +
            "INNER JOIN pizza_with_topping " +
            "ON pizza_with_topping.pizza_with_topping__id = topping.topping__id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = pizza.pizza__id " +
            ") AS price FROM pizza"
    )
    /**
     * Bierze wszystkie pizza toppings i dodaje wszystkie skladniki razem aby dostac total
     */
    LiveData<List<PizzaWithPrice>> getPizzaList();

    @Query("SELECT * FROM pizza " +
            "INNER JOIN base ON base.base__id = pizza.pizza__base_id " +
            "WHERE pizza.pizza__id = :id")
    LiveData<PizzaInfo> getPizza(long id);

    @Query("SELECT " +
            "topping.topping__id, " +
            "topping.topping__name, " +
            "topping.topping__price " +
            "FROM topping " +
            "INNER JOIN pizza_with_topping ON " +
            "pizza_with_topping.pizza_with_topping__topping_id = topping.topping__id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = :pizzaId")
    LiveData<List<ToppingEntity>> getPizzaToppings(long pizzaId);
}
