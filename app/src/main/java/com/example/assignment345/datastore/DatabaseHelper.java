package com.example.assignment345.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.assignment345.datastore.models.Meal;
import com.example.assignment345.datastore.models.Order;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, "tomatoes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MEALS ( id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price DECIMAL)");
        db.execSQL("CREATE TABLE ORDERS ( id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "                     totalprice DECIMAL, \n" +
                "                     meal INTEGER,\n" +
                "                     quantity Integer,\n" +
                "                     tip DECIMAL,\n" +
                "                     FOREIGN KEY(meal) REFERENCES MEALS(id)\n" +
                "                    ); ");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Shrimp with Vermicelli and Garlic', '15');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Dumplings', '16');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES (' Chow Mein', '17');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Peking Roasted Duck', '18');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Steamed Vermicelli Rolls', '19');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Fried Shrimp with Cashew Nuts', '25');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Ma Po Tofu', '24');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Wontons', '21');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Spring Rolls', '23');");
        db.execSQL("INSERT INTO MEALS (name,price) VALUES ('Yangchow Fried Rice', '30');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT O.id, totalprice, meal, M.name, quantity, tip FROM ORDERS O JOIN MEALS M WHERE O.meal = M.id ", null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Order o = new Order(
                            cursor.getInt(0),
                            cursor.getDouble(1),
                            cursor.getInt(2),
                            cursor.getInt(4),
                            cursor.getDouble(5)
                    );
                    o.setMeal_name(cursor.getString(3));
                    orders.add(o);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DbHelper", "Error while trying to get orders from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return orders;
    }

    public List<Meal> getAllMeals() {
        List<Meal> meals = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MEALS", null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Meal meal = new Meal(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getDouble(cursor.getColumnIndex("price"))
                    );
                    meals.add(meal);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DbHelper", "Error while trying to get meals from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return meals;
    }

    public long createOrder(Order order) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cvs = new ContentValues();
        cvs.put("quantity", order.getQuantity());
        cvs.put("meal", order.getMeal_id());
        cvs.put("totalprice", order.getTotalPrice());
        cvs.put("tip", order.getTip());
        Log.i("DbHelper", "createOrder: Execution here");
        return db.insert("ORDERS", null, cvs);
    }
}
