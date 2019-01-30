package com.marek.wojdyla.pizzaapp.vm.pizza.info;

import android.app.Application;

import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaInfo;
import com.marek.wojdyla.pizzaapp.db.pizza.ToppingEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PizzaInfoViewModel extends AndroidViewModel {

    private final LiveData<PizzaInfo> mPizza;
    private final LiveData<List<ToppingEntity>> mToppings;

    public PizzaInfoViewModel(@NonNull Application application, long pizzaId) {
        super(application);
        PizzaDao pizzaDao = PizzaDatabase.getDatabase(application).getPizzaBaseDao();
        mPizza = pizzaDao.getPizza(pizzaId);
        mToppings = pizzaDao.getPizzaToppings(pizzaId);
    }

    public LiveData<PizzaInfo> getPizza() {
        return mPizza;
    }

    public LiveData<List<ToppingEntity>> getToppings() {
        return mToppings;
    }
}
