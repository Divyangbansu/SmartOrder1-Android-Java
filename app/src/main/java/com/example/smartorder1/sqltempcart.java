package com.example.smartorder1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class sqltempcart extends SQLiteOpenHelper {
    public sqltempcart(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void querydata(String sql)
    {
        SQLiteDatabase dataTempcart=getWritableDatabase();
        dataTempcart.execSQL(sql);
    }

    public void insertData(String name,String price,Long quan,byte[] img)
    {
        SQLiteDatabase dataTempcart=getWritableDatabase();
        String sql="insert into Tempcart values(NULL, ?,?,?,?)";
        SQLiteStatement st=dataTempcart.compileStatement(sql);
        st.clearBindings();

        Double price1=Double.parseDouble(price);
        st.bindString(1,name);
        st.bindDouble(2,price1);
        st.bindLong(3,quan);
        st.bindBlob(4,img);
        st.executeInsert();
    }

    public Cursor getdata(String sql) {

        SQLiteDatabase dataTempcart = getReadableDatabase();
        return dataTempcart.rawQuery(sql,null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
