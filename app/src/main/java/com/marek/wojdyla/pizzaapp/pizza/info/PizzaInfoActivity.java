package com.marek.wojdyla.pizzaapp.pizza.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaInfo;
import com.marek.wojdyla.pizzaapp.db.pizza.ToppingEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PizzaInfoActivity extends AppCompatActivity {
    private final DecimalFormat mFormat = new DecimalFormat("0.00");
    private TextView mName;
    private TextView mPrice;
    private TextView mBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        long pizzaId = IntentFactory.getPizzaId(getIntent());

        Adapter adapter = new Adapter();
        RecyclerView recyclerView = findViewById(R.id.pizzaContent_pizzaContents);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mName = findViewById(R.id.pizzaContent_name);
        mPrice = findViewById(R.id.pizzaContent_price);
        mBase = findViewById(R.id.pizzaContent_base);

        PizzaDao pizzaDao = PizzaDatabase.getDatabase(this).getPizzaDao();
        pizzaDao.getPizza(pizzaId).observe(this, this::bindPizza);
        pizzaDao.getPizzaToppings(pizzaId).observe(this, adapter::setData);
    }

    private void bindPizza(PizzaInfo pizzaInfo) {
        mName.setText(pizzaInfo.pizza.name);
        mPrice.setText(mFormat.format(pizzaInfo.price));
        mBase.setText(pizzaInfo.pizzaBase.name);
    }

    public static class IntentFactory {
        private static final String PIZZA_ID = IntentFactory.class.getSimpleName() + "PIZZA_ID";

        public static Intent create(Context context, long pizzaId) {
            Intent intent = new Intent(context, PizzaInfoActivity.class);
            intent.putExtra(PIZZA_ID, pizzaId);
            return intent;
        }

        private static long getPizzaId(Intent intent) {
            return intent.getLongExtra(PIZZA_ID, -1);
        }
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pizzaToppingItem_name);
            price = itemView.findViewById(R.id.pizzaToppingItem_price);
        }

        public void bind(ToppingEntity entity) {
            name.setText(entity.name);
            price.setText(mFormat.format(entity.price));
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<ToppingEntity> mData = new ArrayList<>();

        public Adapter() {
            setHasStableIds(true);
        }

        public void setData(List<ToppingEntity> entity) {
            mData = entity;
            notifyDataSetChanged();
        }

        public List<ToppingEntity> getData() {
            return mData;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.pizza_topping_item, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).id;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
