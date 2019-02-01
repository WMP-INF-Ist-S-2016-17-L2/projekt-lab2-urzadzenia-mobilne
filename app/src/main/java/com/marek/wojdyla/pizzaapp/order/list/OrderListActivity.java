package com.marek.wojdyla.pizzaapp.order.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.order.Order;
import com.marek.wojdyla.pizzaapp.db.order.OrderDao;
import com.marek.wojdyla.pizzaapp.order.info.OrderInfoActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OrderDao orderDao = PizzaDatabase.getDatabase(getApplicationContext()).getOrderDao();


        Adapter adapter = new Adapter();
        RecyclerView recyclerView = findViewById(R.id.orderList_recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderDao.getOrders().observe(this, adapter::setData);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private final DecimalFormat mFormat = new DecimalFormat("0.00");
        private Order mEntity;
        private final TextView id;
        private final TextView restaurantName;
        private final TextView total;
        private final ImageView paymentAwaiting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.orderListItem_id);
            restaurantName = itemView.findViewById(R.id.orderListItem_restaurantName);
            total = itemView.findViewById(R.id.orderListItem_total);
            paymentAwaiting = itemView.findViewById(R.id.orderListItem_paymentAwaiting);

            itemView.setOnClickListener(this::onClick);
        }

        private void onClick(View v) {
            if (mEntity == null) return;
            startActivity(OrderInfoActivity
                    .IntentFactory
                    .create(OrderListActivity.this, mEntity.order.id));
        }

        public void bind(Order entity) {
            id.setText(String.valueOf(entity.order.id));
            restaurantName.setText(entity.restaurant.name);
            total.setText(mFormat.format(entity.order.total));
            mEntity = entity;
            paymentAwaiting.setImageResource(
                    entity.order.isPaid ?
                            R.drawable.ic_baseline_done_24px :
                            R.drawable.ic_baseline_payment_24px);
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Order> mData = new ArrayList<>();

        public Adapter() {
            setHasStableIds(true);
        }

        public void setData(List<Order> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.order_list_item, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).order.id;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
