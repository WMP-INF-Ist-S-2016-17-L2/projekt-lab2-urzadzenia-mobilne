package com.marek.wojdyla.pizzaapp.pizza.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;
import com.marek.wojdyla.pizzaapp.pizza.creator.CreateOwnActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PizzaListActivity extends AppCompatActivity {

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
    }

    private void showCreateOwn() {
        startActivity(new Intent(PizzaListActivity.this, CreateOwnActivity.class));
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

    private static class ViewHolder extends RecyclerView.ViewHolder {

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
//            v.getContext().startActivity(PizzaListActivity.IntentFactory.create(
//                    v.getContext(),
//                    mEntity.id,
//                    mEntity.hasDelivery
//            ));
        }

        public void bind(PizzaWithPrice entity) {
            name.setText(entity.pizza.name);
            price.setText(String.valueOf(entity.price));
            mEntity = entity;
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

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
