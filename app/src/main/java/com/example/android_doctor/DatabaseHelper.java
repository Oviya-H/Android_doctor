package com.example.android_doctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="Events.db";
    private static final String DB_TABLE ="Events_Table";
    private static final String ID="ID";
    private static final String NAME="NAME";
    private static final String DATE="DATE";
    private static final String TIME="TIME";
    private static final String CREATE_TABLE="CREATE TABLE "+ DB_TABLE+ " ("+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME+" TEXT,"+
            DATE+" TEXT,"+
            TIME+" TEXT"+ ")";
    public DatabaseHelper(Context context){
        super(context, DB_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,event.getName());
        contentValues.put(DATE,event.getDate());
        contentValues.put(TIME,event.getTime());
        long result = db.insert(DB_TABLE,null,contentValues);
        return result != -1;
    }
    public Cursor getAllData(){
        ArrayList<Event> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+DB_TABLE,null);

        return cursor;
    }
}

