package com.example.smartorder1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class sqlfood extends SQLiteOpenHelper {


    public sqlfood(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void querydata(String sql)
    {
        SQLiteDatabase dataFood=getWritableDatabase();
        dataFood.execSQL(sql);
    }
    public void insertData(String name,String price,String gram,byte[] img,int cid,int tid)
    {
        SQLiteDatabase dataFood=getWritableDatabase();
        String sql="insert into FoodItem values(NULL,?,?,?,?,?,?)";
        SQLiteStatement st=dataFood.compileStatement(sql);
        st.clearBindings();

        int price1=Integer.parseInt(price);
        double gram1=Double.parseDouble(gram);
        st.bindString(1,name);
        st.bindLong(2,price1);
        st.bindDouble(3,gram1);
        st.bindBlob(4,img);
        st.bindLong(5,cid);
        st.bindLong(6,tid);
        st.executeInsert();
    }
    public Cursor getdata(String sql) {

        SQLiteDatabase dataFood = getReadableDatabase();
        return dataFood.rawQuery(sql,null);
    }
}
