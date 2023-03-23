package com.example.midtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "simple_form_data";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }


    public boolean addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user_values = new ContentValues();

        user_values.put(KEY_EMAIL, user.getEmail());
        user_values.put(KEY_USERNAME, user.getUsername());
        user_values.put(KEY_PASSWORD, user.getPassword());

        Log.d(DATABASE_NAME, "addUser: Adding " + user + " to " + TABLE_USERS);
        long result = db.insert(TABLE_USERS, null, user_values);
        if (result == -1)
            return false;
        else
            return true;
    }


    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String select_query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setEmail(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));

                userList.add(user);

            } while (cursor.moveToNext());
        }
        return userList;
    }
}
