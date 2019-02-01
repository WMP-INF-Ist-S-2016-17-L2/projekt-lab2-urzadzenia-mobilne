package com.marek.wojdyla.pizzaapp.order.info;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.order.Order;
import com.marek.wojdyla.pizzaapp.db.order.OrderDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OrderViewModel extends AndroidViewModel {

    private final long mOrderId;
    private final OrderDao mOrderDao;
    private final LiveData<List<PizzaWithPrice>> mPizzas;
    private final LiveData<Order> mOrder;

    public OrderViewModel(@NonNull Application application, long orderId) {
        super(application);
        mOrderId = orderId;
        mOrderDao = PizzaDatabase.getDatabase(application).getOrderDao();
        mPizzas = mOrderDao.getPizzasForOrder(orderId);
        mOrder = mOrderDao.getOrder(orderId);
    }

    public LiveData<Order> getOrder() {
        return mOrder;
    }

    public LiveData<List<PizzaWithPrice>> getPizzas() {
        return mPizzas;
    }

    @SuppressLint("StaticFieldLeak")
    public void pay() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                mOrderDao.payForOrder(mOrderId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
}
