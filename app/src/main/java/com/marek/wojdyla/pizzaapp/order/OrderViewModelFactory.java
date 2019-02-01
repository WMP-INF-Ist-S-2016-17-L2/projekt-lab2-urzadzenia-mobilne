package com.marek.wojdyla.pizzaapp.order;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OrderViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private final Application mApplication;
    private final long orderId;

    public OrderViewModelFactory(@NonNull Application application, long orderId) {
        super(application);
        mApplication = application;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OrderViewModel(mApplication, orderId);
    }
}
