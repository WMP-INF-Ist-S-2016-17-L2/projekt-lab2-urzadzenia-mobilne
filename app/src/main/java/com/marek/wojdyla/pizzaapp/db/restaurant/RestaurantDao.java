package com.marek.wojdyla.pizzaapp.db.restaurant;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM restaurant WHERE restaurant__has_delivery = 1")
    LiveData<List<RestaurantEntity>> getDeliveryRestaurants();

    @Query("SELECT * FROM restaurant WHERE restaurant__has_delivery = 0")
    LiveData<List<RestaurantEntity>> getPickupRestaurants();

}
