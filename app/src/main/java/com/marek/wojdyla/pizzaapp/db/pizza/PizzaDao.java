package com.marek.wojdyla.pizzaapp.db.pizza;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

@Dao
public interface PizzaDao {

    String SELECT_PIZZA_WITH_PRICE = "SELECT " +
            "pizza.*," +
            "(" +
            "SELECT SUM(topping.topping__price) " +
            "FROM pizza_with_topping " +
            "LEFT JOIN topping " +
            "ON topping.topping__id = pizza_with_topping.pizza_with_topping__topping_id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = pizza.pizza__id" +
            ") AS price " +
            "FROM pizza";

    @Query(SELECT_PIZZA_WITH_PRICE)
    /**
     * Bierze wszystkie pizza toppings i dodaje wszystkie skladniki razem aby dostac total
     */
    LiveData<List<PizzaWithPrice>> getPizzaList();

    @Query(SELECT_PIZZA_WITH_PRICE + " " +
            "INNER JOIN base ON base.base__id = pizza.pizza__base_id " +
            "WHERE pizza.pizza__id = :id"
    )
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<PizzaInfo> getPizza(long id);

    @Query("SELECT " +
            "topping.* " +
            "FROM topping " +
            "INNER JOIN pizza_with_topping ON " +
            "pizza_with_topping.pizza_with_topping__topping_id = topping.topping__id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = :pizzaId")
    LiveData<List<ToppingEntity>> getPizzaToppings(long pizzaId);

    @Query("SELECT * FROM topping")
    LiveData<List<ToppingEntity>> getAllToppings();

    @Query("SELECT * FROM base")
    LiveData<List<BaseEntity>> getAllBase();

    @Insert
    long insertPizza(PizzaEntity entity);

    @Insert
    void insertPizzaToppings(List<PizzaWithToppingEntity> entities);
}
