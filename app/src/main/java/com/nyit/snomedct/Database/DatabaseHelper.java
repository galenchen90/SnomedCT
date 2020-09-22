package com.nyit.snomedct.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context mContext;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "data.db";

    public static abstract class HistoryTable {

        public static final String TABLE_HISTORY = "historytable";
        public static final String COL_TIME = "time";
        public static final String COL_KEYWORD = "keyword";
    }

    public static abstract class FavoriteTable{
        public static final String TABLE_FAVORITE="favoritetable";
        public static final String COL_NAME="name";
        public static final String COL_ID="id";
        public static final String COL_URL="url";
        public static final String COL_STATE="state";
    }


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private static final String SQL_CREATE_HISTORY_TABLE =
            "CREATE TABLE historytable ("
                    + HistoryTable.COL_KEYWORD + " TEXT PRIMARY KEY,"
                    + HistoryTable.COL_TIME + " TEXT)";

    public static final String SQL_CREATE_FAVORITE_TABLE =
            "CREATE TABLE favoritetable ("
                    + FavoriteTable.COL_ID + " TEXT PRIMARY KEY,"
                    + FavoriteTable.COL_NAME + " TEXT,"
                    + FavoriteTable.COL_URL + " TEXT,"
                    + FavoriteTable.COL_STATE + " TEXT"
                    +")";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_HISTORY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_CREATE_HISTORY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
        onCreate(sqLiteDatabase);
    }


    public void insertKeyword(String time, String keyword) {


        SQLiteDatabase db = getWritableDatabase();

        String sql0 = "SELECT * FROM "+ HistoryTable.TABLE_HISTORY + " where "+HistoryTable.COL_KEYWORD
                + " = '" + keyword + "'";

        Cursor cursor = db.rawQuery(sql0,null );
        if (cursor.getCount() != 0) {

            String sql = " UPDATE " + HistoryTable.TABLE_HISTORY + " SET " + HistoryTable.COL_TIME +
                    " = '" + time + "' WHERE " + HistoryTable.COL_KEYWORD + " = '" + keyword + "'";
            db.execSQL(sql);
            db.close();

        }
        else {



        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(HistoryTable.COL_TIME, time);
        values.put(HistoryTable.COL_KEYWORD, keyword);

        db.insert(HistoryTable.TABLE_HISTORY, null, values);


        db.close();
        }


    }

    public void insertFavorite (String id, String name,String url,String state) {

        SQLiteDatabase db = getWritableDatabase();

        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(FavoriteTable.COL_ID, id);
        values.put(FavoriteTable.COL_NAME, name);
        values.put(FavoriteTable.COL_URL, url);
        values.put(FavoriteTable.COL_STATE, state);

        db.insert(FavoriteTable.TABLE_FAVORITE, null, values);
        db.close();


    }



    public List<SearchObjects> getHistory() {
        List<SearchObjects> searchObjects = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

//        String sql ="SELECT "+ HistoryTable.COL_TIME
//                + " AND " + HistoryTable.COL_KEYWORD
//                +" FROM " +HistoryTable.TABLE_HISTORY;
        String sql = "SELECT * " + "FROM " + HistoryTable.TABLE_HISTORY;

        Cursor cur = db.rawQuery(sql, null);

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                String keyword = cur.getString(0);
                String time = cur.getString(1);

                searchObjects.add(new SearchObjects(time, keyword));

            } while (cur.moveToNext());
        }


        return searchObjects;
    }

    public List<FavoriteObject> getFavorite(){

        List<FavoriteObject> favoriteObjects = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT * " + "FROM " + FavoriteTable.TABLE_FAVORITE;

        Cursor cur = db.rawQuery(sql, null);

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                String id = cur.getString(0);
                String name = cur.getString(1);
                String url = cur.getString(2);
                String state = cur.getString(3);

                favoriteObjects.add(new FavoriteObject(id,name,url,state));

            } while (cur.moveToNext());
        }


        return favoriteObjects;
    }


    public void updateHistoryTable(){
        long currenttime= System.currentTimeMillis();
    //    long goodtime = currenttime - 604800000;
        long goodtime = currenttime - 100000;


        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT "+HistoryTable.COL_TIME + " FROM " + HistoryTable.TABLE_HISTORY;

        Cursor cur = db.rawQuery(sql, null);

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                String time = cur.getString(0);
                Long timel = Long.parseLong(time);

                if (timel<goodtime) {
                    db.delete(HistoryTable.TABLE_HISTORY, HistoryTable.COL_TIME + " = " + timel,null );

                }


            } while (cur.moveToNext());
        }







    }






}
