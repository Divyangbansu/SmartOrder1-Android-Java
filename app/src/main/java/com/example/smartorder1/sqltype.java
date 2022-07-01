package com.example.smartorder1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class sqltype extends SQLiteOpenHelper {

    public sqltype(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void querydata(String sql)
    {
        SQLiteDatabase dataCategory=getWritableDatabase();
        dataCategory.execSQL(sql);
    }
    public void insertData(String name,int id)
    {
        SQLiteDatabase dataCategory=getWritableDatabase();
        String sql="insert into Type values(NULL, ?, ?)";
        SQLiteStatement st=dataCategory.compileStatement(sql);
        st.clearBindings();

        st.bindString(1,name);
        st.bindLong(2,id);
        st.executeInsert();
    }

    public Cursor getdata(String sql) {

        SQLiteDatabase dataCategory = getReadableDatabase();
        return dataCategory.rawQuery(sql,null);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
