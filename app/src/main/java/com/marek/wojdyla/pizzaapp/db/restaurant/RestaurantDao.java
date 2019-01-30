package com.marek.wojdyla.pizzaapp.db.restaurant;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM restaurant WHERE restaurant__has_delivery = 1")
    LiveData<Restaurant> getDeliveryRestaurants();

    @Query("SELECT * FROM restaurant WHERE restaurant__has_delivery = 0")
    LiveData<Restaurant> getPickupRestaurants();

}
