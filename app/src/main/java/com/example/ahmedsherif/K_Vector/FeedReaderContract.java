package com.example.ahmedsherif.K_Vector;

import android.provider.BaseColumns;

public class FeedReaderContract {
    //this class is made to initialize some variables to be used in all the program without declaring them
    private FeedReaderContract(){}
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "K";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
        public static final String DATABASE_NAME = "H.db";

        public static final String TABLE_NAME_2="Magazine";
        public static final String ID="Id";
        public static final String TEXT="Txt";
    }
}
