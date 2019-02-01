package com.marek.wojdyla.pizzaapp.pizza.list;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PizzaListViewModel extends AndroidViewModel {

    private final LiveData<List<PizzaWithPrice>> mPizzaList;
    private final PizzaDao mPizzaDao;

    public PizzaListViewModel(@NonNull Application application) {
        super(application);
        mPizzaDao = PizzaDatabase.getDatabase(application).getPizzaDao();
        mPizzaList = mPizzaDao.getPizzaList();
    }

    public LiveData<List<PizzaWithPrice>> getPizzaList() {
        return mPizzaList;
    }

    @SuppressLint("StaticFieldLeak")
    public void deletePizza(long pizzaId) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                mPizzaDao.deletePizza(pizzaId);
                return null;
            }
        }.execute();
    }
}
