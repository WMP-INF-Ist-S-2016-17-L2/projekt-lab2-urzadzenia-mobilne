package com.marek.wojdyla.pizzaapp.pizza.info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PizzaViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private final Application mApplication;
    private final long pizzaId;

    public PizzaViewModelFactory(@NonNull Application application, long pizzaId) {
        super(application);
        mApplication = application;
        this.pizzaId = pizzaId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PizzaInfoViewModel(mApplication, pizzaId);
    }
}
