package com.master.newsapi.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;

    public UserDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
    }

    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(DataBaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DataBaseHelper.COLUMN_PASSWORD, hashPassword(user.getPassword()));
        return database.insert(DataBaseHelper.TABLE_USERS, null, values);
    }

    public User getUser(String email) {
        User user = null;
        Cursor cursor = database.query(DataBaseHelper.TABLE_USERS,
                new String[]{DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_USERNAME,
                        DataBaseHelper.COLUMN_EMAIL, DataBaseHelper.COLUMN_PASSWORD},
                DataBaseHelper.COLUMN_EMAIL + "= ?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
            user.setId(cursor.getInt(0));
            cursor.close();
        }
        return user;
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    @SuppressLint("Range")
    public User getUserByEmail(String email) {
        User user = null;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataBaseHelper.TABLE_USERS,
                new String[]{DataBaseHelper.COLUMN_USERNAME, DataBaseHelper.COLUMN_EMAIL}, // Add all columns you need
        DataBaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Create a User object from cursor data
            user = new User(
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PASSWORD)) // Or other fields as needed
            );
            cursor.close();
        }

        db.close();
        return user;
    }

}
