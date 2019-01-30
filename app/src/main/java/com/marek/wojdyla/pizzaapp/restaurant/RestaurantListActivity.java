package com.marek.wojdyla.pizzaapp.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantEntity;
import com.marek.wojdyla.pizzaapp.pizza.list.PizzaListActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RestaurantViewModel viewModel = ViewModelProviders
                .of(this, new RestaurantViewModelFactory(
                        getApplication(),
                        IntentFactory.isDelivery(getIntent())))
                .get(RestaurantViewModel.class);

        Adapter adapter = new Adapter();
        RecyclerView recyclerView = findViewById(R.id.restaurantList_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getData().observe(this, adapter::setData);
    }

    public static class IntentFactory {
        private static final String IS_DELIVERY = IntentFactory.class.getSimpleName() + "IS_DELIVERY";

        public static Intent create(Context context, boolean isDelivery) {
            Intent intent = new Intent(context, RestaurantListActivity.class);
            intent.putExtra(IS_DELIVERY, isDelivery);
            return intent;
        }

        private static boolean isDelivery(Intent intent) {
            return intent.getBooleanExtra(IS_DELIVERY, false);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private RestaurantEntity mEntity;
        private final TextView name;
        private final TextView city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restaurantItem_name);
            city = itemView.findViewById(R.id.restaurantItem_city);

            itemView.setOnClickListener(this::onClick);
        }

        private void onClick(View v) {
            if (mEntity == null) return;
            v.getContext().startActivity(PizzaListActivity.IntentFactory.create(
                    v.getContext(),
                    mEntity.id,
                    mEntity.hasDelivery
            ));
        }

        public void bind(RestaurantEntity restaurant) {
            name.setText(restaurant.name);
            city.setText(restaurant.city);
            mEntity = restaurant;
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<RestaurantEntity> mData = new ArrayList<>();

        public Adapter() {
            setHasStableIds(true);
        }

        public void setData(List<RestaurantEntity> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.restaurant_item, parent, false)
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
