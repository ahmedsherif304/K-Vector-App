package com.example.ahmedsherif.K_Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.TABLE_NAME_2;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.ID;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.TEXT;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    //this class is used to save the titles and the urls of the magazine in a Data Base called SQl
    private static final String DATABASE_NAME = "Reader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME_2 + " (" +
                    ID + " TEXT," +
                    TEXT + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_2;
    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insert (String id,String Text){
        //Inserting the data into the data base i have created
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(TEXT, Text);
        return  db.insert(TABLE_NAME_2, null, values);
    }
    public Cursor GetData(){
        //getting the data to view the article it self
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME_2,null);
    }
}
