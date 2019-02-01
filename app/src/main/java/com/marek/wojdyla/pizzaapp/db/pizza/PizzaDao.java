package com.marek.wojdyla.pizzaapp.db.pizza;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PizzaDao {

    @Query("SELECT " +
            "pizza.*," +
            "(" +
            "SELECT SUM(topping.topping__price) " +
            "FROM pizza_with_topping " +
            "LEFT JOIN topping " +
            "ON topping.topping__id = pizza_with_topping.pizza_with_topping__topping_id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = pizza.pizza__id" +
            ") AS price " +
            "FROM pizza " +
            "WHERE pizza.pizza__is_deleted = 0")
    /**
     * Bierze wszystkie pizza toppings i dodaje wszystkie skladniki razem aby dostac total
     */
    LiveData<List<PizzaWithPrice>> getPizzaList();

    @Query("SELECT " +
            "pizza.*, base.*, " +
            "(" +
            "SELECT SUM(topping.topping__price) " +
            "FROM pizza_with_topping " +
            "LEFT JOIN topping " +
            "ON topping.topping__id = pizza_with_topping.pizza_with_topping__topping_id " +
            "WHERE pizza_with_topping.pizza_with_topping__pizza_id = pizza.pizza__id" +
            ") AS price " +
            "FROM pizza " +
            "LEFT JOIN base ON base.base__id = pizza__base_id " +
            "WHERE pizza__id = :id ")
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

    @Query("UPDATE pizza SET pizza__is_deleted = 1 WHERE pizza__id=:id")
    void deletePizza(long id);
}
