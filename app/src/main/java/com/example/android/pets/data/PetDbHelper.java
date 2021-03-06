package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.pets.data.PetContract.PetEntry.TABLE_NAME;

/**
 * Created by Delight on 11/04/2018.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION= 1;
    public  static final String DATABASE_NAME = TABLE_NAME;

    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table pets(id int, gender int, name String etc
        //create a string variable for creating the sqlite table using the sqlite syntax
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                PetContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PetContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetContract.PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetContract.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
