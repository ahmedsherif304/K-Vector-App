package com.example.ahmedsherif.K_Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.COLUMN_NAME_URL;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.DATABASE_NAME;
import static com.example.ahmedsherif.K_Vector.FeedReaderContract.FeedEntry.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    //this class is used to save the titles and the urls of the magazine in a Data Base called SQl
    private static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+TABLE_NAME+"("+
            _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NAME_TITLE+ " TEXT,"+
            COLUMN_NAME_URL+" TEXT);";
    private static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS "+TABLE_NAME;
    public DatabaseHelper(Context context) {
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
    public void renew(){
        //to prevent the duplicates i delete it then create it again before inserting
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public long insert(String Tit,String u){
        //Inserting the data into the data base i have created
        SQLiteDatabase db=this.getWritableDatabase();//getting the data base that is saved
        ContentValues values = new ContentValues();//some variable to content the values i want to insert
        values.put(COLUMN_NAME_TITLE, Tit);
        values.put(COLUMN_NAME_URL, u);//inserting the values
        return  db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        //putting the values into the data base
    }
    public Cursor GetData(){
        //getting the data to view it in the list
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT DISTINCT * FROM "+TABLE_NAME,null);
        //putting the data into a cursor to use it to organize the data
    }
}
