package com.marek.wojdyla.pizzaapp.pizza.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.order.OrderDao;
import com.marek.wojdyla.pizzaapp.db.order.OrderEntity;
import com.marek.wojdyla.pizzaapp.db.order.OrderItemEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;
import com.marek.wojdyla.pizzaapp.order.OrderInfoActivity;
import com.marek.wojdyla.pizzaapp.pizza.creator.CreateOwnActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PizzaListActivity extends AppCompatActivity {

    private List<PizzaWithPrice> mPizzasInBasket = new ArrayList<>();

    BasketWithCounter mBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PizzaListViewModel viewModel = ViewModelProviders
                .of(this)
                .get(PizzaListViewModel.class);

        Adapter adapter = new Adapter();
        RecyclerView recyclerView = findViewById(R.id.pizzaList_recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getPizzaList().observe(this, adapter::setData);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showCreateOwn());

        mBasket = findViewById(R.id.pizzaList_basket);
        mBasket.setOnClickListener(this::createOrder);
    }

    @SuppressLint("StaticFieldLeak")
    private void createOrder(View view) {
        final long restaurantId = IntentFactory.restaurantId(getIntent());
        final boolean isDelivery = IntentFactory.isDelivery(getIntent());
        final List<PizzaWithPrice> pizzas = new ArrayList<>(mPizzasInBasket);
        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... voids) {
                OrderDao orderDao = PizzaDatabase.getDatabase(getApplication()).getOrderDao();

                double total = mPizzasInBasket.stream().mapToDouble(value -> value.price).sum();

                long orderId = orderDao.createOrder(new OrderEntity(restaurantId, isDelivery, total));
                for (PizzaWithPrice pizza : pizzas) {
                    orderDao.insert(new OrderItemEntity(orderId, pizza.pizza.id));
                }
                return orderId;
            }

            @Override
            protected void onPostExecute(Long orderId) {
                super.onPostExecute(orderId);
                startActivity(
                        OrderInfoActivity
                                .IntentFactory
                                .create(PizzaListActivity.this, orderId)
                );
            }
        }.execute();
    }

    private void showCreateOwn() {
        startActivity(new Intent(PizzaListActivity.this, CreateOwnActivity.class));
    }

    void onAddToBasket(PizzaWithPrice entity) {
        mPizzasInBasket.add(entity);
        mBasket.setCounter(mPizzasInBasket.size());
    }

    public static class IntentFactory {
        private static final String RESTAURANT_ID = IntentFactory.class.getSimpleName() + "RESTAURANT_ID";
        private static final String IS_DELIVERY = IntentFactory.class.getSimpleName() + "IS_DELIVERY";

        public static Intent create(Context context, long restaurantId, boolean isDelivery) {
            Intent intent = new Intent(context, PizzaListActivity.class);
            intent.putExtra(RESTAURANT_ID, restaurantId);
            intent.putExtra(IS_DELIVERY, isDelivery);
            return intent;
        }

        private static boolean isDelivery(Intent intent) {
            return intent.getBooleanExtra(IS_DELIVERY, false);
        }

        private static long restaurantId(Intent intent) {
            return intent.getLongExtra(RESTAURANT_ID, -1);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private final DecimalFormat mFormat = new DecimalFormat("0.00");
        private PizzaWithPrice mEntity;
        private final TextView name;
        private final TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pizzaListItem_name);
            price = itemView.findViewById(R.id.pizzaListItem_price);

            itemView.setOnClickListener(this::onClick);
        }

        private void onClick(View v) {
            if (mEntity == null) return;
            onAddToBasket(mEntity);
        }

        public void bind(PizzaWithPrice entity) {
            name.setText(entity.pizza.name);
            price.setText(mFormat.format(entity.price));
            mEntity = entity;
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<PizzaWithPrice> mData = new ArrayList<>();

        public Adapter() {
            setHasStableIds(true);
        }

        public void setData(List<PizzaWithPrice> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.pizza_list_item, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).pizza.id;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


}
