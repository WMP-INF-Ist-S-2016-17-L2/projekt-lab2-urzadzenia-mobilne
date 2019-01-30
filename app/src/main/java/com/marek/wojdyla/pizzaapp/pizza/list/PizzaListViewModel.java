package com.marek.wojdyla.pizzaapp.pizza.list;

import android.app.Application;

import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PizzaListViewModel extends AndroidViewModel {

    private final LiveData<List<PizzaWithPrice>> mPizzaList;

    public PizzaListViewModel(@NonNull Application application) {
        super(application);
        PizzaDao pizzaDao = PizzaDatabase.getDatabase(application).getPizzaBaseDao();
        mPizzaList = pizzaDao.getPizzaList();
    }

    public LiveData<List<PizzaWithPrice>> getPizzaList() {
        return mPizzaList;
    }
}
