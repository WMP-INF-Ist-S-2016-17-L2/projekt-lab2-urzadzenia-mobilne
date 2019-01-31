package com.marek.wojdyla.pizzaapp.pizza.creator;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marek.wojdyla.pizzaapp.R;
import com.marek.wojdyla.pizzaapp.db.PizzaDatabase;
import com.marek.wojdyla.pizzaapp.db.pizza.BaseEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithToppingEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.ToppingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateOwnActivity extends AppCompatActivity {

    private Spinner mBaseSpinner;
    private Spinner mToppingSpinner;
    private Adapter mAdapter;
    private TextView mPizzaName;
    private PizzaDao mPizzaBaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_own);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::savePizza);

        mBaseSpinner = findViewById(R.id.createOwn_baseType);
        mToppingSpinner = findViewById(R.id.createOwn_topping);
        mPizzaName = findViewById(R.id.createOwn_name);

        mPizzaBaseDao = PizzaDatabase.getDatabase(this).getPizzaBaseDao();

        mPizzaBaseDao.getAllBase().observe(this, this::populateSpinnerBase);
        mPizzaBaseDao.getAllToppings().observe(this, this::populateSpinnerTopping);

        mAdapter = new Adapter();
        RecyclerView recyclerView = findViewById(R.id.createOwn_pizzaToppingList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.createOwn_addTopping).setOnClickListener(this::onAddTopping);
    }

    private void populateSpinnerBase(List<BaseEntity> entityList) {
        List<SimpleBase> list = entityList
                .stream()
                .map(SimpleBase::new)
                .collect(Collectors.toList());
        ArrayAdapter<SimpleBase> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                list
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBaseSpinner.setAdapter(adapter);
    }

    private void populateSpinnerTopping(List<ToppingEntity> entityList) {
        List<SimpleTopping> list = entityList
                .stream()
                .map(SimpleTopping::new)
                .collect(Collectors.toList());
        ArrayAdapter<SimpleTopping> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                list
        );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mToppingSpinner.setAdapter(adapter);
    }

    private void onAddTopping(View v) {
        mAdapter.addTopping(((SimpleTopping) mToppingSpinner.getSelectedItem()).entity);
    }

    @SuppressLint("StaticFieldLeak")
    private void savePizza(View v) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                long pizzaId = mPizzaBaseDao.insertPizza(new PizzaEntity(getPizzaName(), getBaseId()));
                mPizzaBaseDao.insertPizzaToppings(getToppings(pizzaId));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
            }
        }.execute();
    }

    private String getPizzaName() {
        String name = mPizzaName.getText().toString();
        return name.isEmpty() ? "Bez Nazwy" : name;
    }

    private long getBaseId() {
        return ((SimpleBase) mBaseSpinner.getSelectedItem()).entity.id;
    }

    private List<PizzaWithToppingEntity> getToppings(long pizzaId) {
        return mAdapter
                .getData()
                .stream()
                .map(entity -> new PizzaWithToppingEntity(pizzaId, entity.id))
                .collect(Collectors.toList());
    }

    private static class SimpleBase {
        final BaseEntity entity;

        private SimpleBase(BaseEntity entity) {
            this.entity = entity;
        }

        @NonNull
        @Override
        public String toString() {
            return entity.name;
        }
    }


    private static class SimpleTopping {
        final ToppingEntity entity;

        private SimpleTopping(ToppingEntity entity) {
            this.entity = entity;
        }

        @NonNull
        @Override
        public String toString() {
            return entity.name;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pizzaToppingItem_name);
            price = itemView.findViewById(R.id.pizzaToppingItem_price);
        }

        public void bind(ToppingEntity entity) {
            name.setText(entity.name);
            price.setText(String.valueOf(entity.price));
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<ToppingEntity> mData = new ArrayList<>();

        public Adapter() {
            setHasStableIds(true);
        }

        public void addTopping(ToppingEntity entity) {
            mData.add(entity);
            notifyItemInserted(mData.size() - 1);
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
