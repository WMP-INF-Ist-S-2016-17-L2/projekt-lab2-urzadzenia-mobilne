package com.marek.wojdyla.pizzaapp.restaurant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RestaurantViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private final Application mApplication;
    private final boolean isDelivery;

    public RestaurantViewModelFactory(@NonNull Application application, boolean isDelivery) {
        super(application);
        mApplication = application;
        this.isDelivery = isDelivery;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RestaurantViewModel(mApplication, isDelivery);
    }
}
