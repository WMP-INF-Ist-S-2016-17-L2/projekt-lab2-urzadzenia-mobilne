package com.marek.wojdyla.pizzaapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.marek.wojdyla.pizzaapp.db.order.OrderDao;
import com.marek.wojdyla.pizzaapp.db.order.OrderEntity;
import com.marek.wojdyla.pizzaapp.db.order.OrderItemEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.BaseEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaDao;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.PizzaWithToppingEntity;
import com.marek.wojdyla.pizzaapp.db.pizza.ToppingEntity;
import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantDao;
import com.marek.wojdyla.pizzaapp.db.restaurant.RestaurantEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {
                PizzaEntity.class,
                BaseEntity.class,
                ToppingEntity.class,
                PizzaWithToppingEntity.class,
                RestaurantEntity.class,
                OrderEntity.class,
                OrderItemEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class PizzaDatabase extends RoomDatabase {

    private static volatile PizzaDatabase INSTANCE;

    public abstract OrderDao getOrderDao();

    public abstract PizzaDao getPizzaDao();

    public abstract RestaurantDao getRestaurantDao();

    public static PizzaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PizzaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PizzaDatabase.class, "pizza_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    PrePopulate.onCreate(db);
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PrePopulate {
        static void onCreate(SupportSQLiteDatabase db) {
            db.beginTransaction();

            long thin = insertBase(db, "Grube Ciasto");
            long thick = insertBase(db, "Cienkie Ciasto");

            long margarita = insertPizza(db, "Margarita", thin);
            long peperoni = insertPizza(db, "Peperoni", thick);

            long tomato = insertTopping(db, "pomidor", 1.2f);
            long pepper = insertTopping(db, "papryka", 1.6f);
            long mushroom = insertTopping(db, "pieczarki", 1.6f);

            insertPizzaIngredient(db, margarita, tomato);
            insertPizzaIngredient(db, peperoni, tomato);
            insertPizzaIngredient(db, peperoni, pepper);
            insertPizzaIngredient(db, peperoni, mushroom);

            insertRestaurant(db, "Rest (D)", "Warszawa", true);
            insertRestaurant(db, "Rest (D)", "Gdansk", true);
            insertRestaurant(db, "Rest (P)", "Rzeszow", false);
            insertRestaurant(db, "Rest (P)", "Warszawa", false);
            insertRestaurant(db, "Rest (P)", "Krakow", false);

            db.setTransactionSuccessful();
            db.endTransaction();
        }

        private static long insertBase(SupportSQLiteDatabase db, String name) {
            ContentValues values = new ContentValues();
            values.put("base__name", name);
            return db.insert("base", SQLiteDatabase.CONFLICT_REPLACE, values);
        }

        private static long insertPizza(SupportSQLiteDatabase db, String name, long baseId) {
            ContentValues values = new ContentValues();
            values.put("pizza__name", name);
            values.put("pizza__is_custom", false);
            values.put("pizza__base_id", baseId);
            return db.insert("pizza", SQLiteDatabase.CONFLICT_REPLACE, values);
        }

        private static long insertTopping(SupportSQLiteDatabase db, String name, float price) {
            ContentValues values = new ContentValues();
            values.put("topping__name", name);
            values.put("topping__price", price);
            return db.insert("topping", SQLiteDatabase.CONFLICT_REPLACE, values);
        }

        private static void insertPizzaIngredient(SupportSQLiteDatabase db, long pizzaId, long toppingId) {
            ContentValues values = new ContentValues();
            values.put("pizza_with_topping__pizza_id", pizzaId);
            values.put("pizza_with_topping__topping_id", toppingId);
            db.insert("pizza_with_topping", SQLiteDatabase.CONFLICT_REPLACE, values);
        }

        private static void insertRestaurant(SupportSQLiteDatabase db, String name, String city, boolean isDelivery) {
            ContentValues values = new ContentValues();
            values.put("restaurant__name", name);
            values.put("restaurant__city", city);
            values.put("restaurant__has_delivery", isDelivery);
            db.insert("restaurant", SQLiteDatabase.CONFLICT_REPLACE, values);
        }
    }
}
