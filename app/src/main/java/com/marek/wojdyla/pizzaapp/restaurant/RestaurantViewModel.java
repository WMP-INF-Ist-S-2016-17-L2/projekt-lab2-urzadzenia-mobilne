package com.marek.wojdyla.pizzaapp.restaurant;

import android.app.Application;

import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantDao;
import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RestaurantViewModel extends AndroidViewModel {

    private LiveData<List<RestaurantEntity>> mData;

    public RestaurantViewModel(@NonNull Application application, boolean isDelivery) {
        super(application);

        RestaurantDao restaurantDao = PizzaDatabase.getDatabase(application).getRestaurantDao();
        if (isDelivery) mData = restaurantDao.getDeliveryRestaurants();
        else mData = restaurantDao.getPickupRestaurants();
    }

    public LiveData<List<RestaurantEntity>> getData() {
        return mData;
    }
}
