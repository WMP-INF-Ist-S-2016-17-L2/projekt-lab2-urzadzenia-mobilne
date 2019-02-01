package com.marek.wojdyla.pizzaapp.order.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.order.Order;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithPrice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderInfoActivity extends AppCompatActivity {

    private TextView mRestaurantName;
    private TextView mRestaurantCity;
    private TextView mDeliveryInfo;
    private TextView mOrderTotal;
    private Button mPay;

    private final DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        Adapter adapter = new Adapter();
        RecyclerView recyclerView = findViewById(R.id.orderInfo_pizzaList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRestaurantName = findViewById(R.id.orderInfo_restaurantName);
        mRestaurantCity = findViewById(R.id.orderInfo_restaurantCity);
        mDeliveryInfo = findViewById(R.id.orderInfo_deliveryInformation);
        mOrderTotal = findViewById(R.id.orderInfo_total);
        mPay = findViewById(R.id.orderInfo_pay);

        long orderId = IntentFactory.getOrderId(getIntent());

        OrderViewModel viewModel = ViewModelProviders
                .of(this, new OrderViewModelFactory(getApplication(), orderId))
                .get(OrderViewModel.class);

        viewModel.getPizzas().observe(this, adapter::setData);
        viewModel.getOrder().observe(this, this::populateOrder);

        mPay.setOnClickListener(v -> viewModel.pay());
    }

    private void populateOrder(Order order) {
        mRestaurantName.setText(order.restaurant.name);
        mRestaurantCity.setText(order.restaurant.city);
        mDeliveryInfo.setText(order.order.isDelivery ? "Dostawa" : "Odbior wlasny");
        mOrderTotal.setText(mDecimalFormat.format(order.order.total));
        if (order.order.isPaid) {
            mPay.setText("Zapłacone");
            mPay.setEnabled(false);
        } else {
            mPay.setText("Zapłać teraz");
            mPay.setEnabled(true);
        }
    }

    public static class IntentFactory {
        private static final String ORDER_ID = IntentFactory.class.getSimpleName() + "ORDER_ID";

        public static Intent create(Context context, long orderId) {
            Intent intent = new Intent(context, OrderInfoActivity.class);
            intent.putExtra(ORDER_ID, orderId);
            return intent;
        }

        private static long getOrderId(Intent intent) {
            return intent.getLongExtra(ORDER_ID, -1);
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
