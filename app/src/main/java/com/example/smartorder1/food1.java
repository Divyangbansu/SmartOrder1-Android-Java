/*
package com.example.smartorder1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class food1 extends AppCompatActivity {



    public void fooddis()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_food1);
        gridView1 =(GridView) findViewById(R.id.gridView1);
        list1=new ArrayList<>();
        adeptor1=new foodadeptor(this,R.layout.customfood,list1);
        gridView1.setAdapter(adeptor1);
        sqlf1=new sqlfood(this,"FoodDB.sqlite",null,1);
        sqlf1.querydata("CREATE TABLE IF NOT EXISTS FoodItem (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,  price INTEGER, gram DOUBLE, img BLOG , cid INTEGER  , tid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID) , FOREIGN KEY (tid) REFERENCES Type(TID))");
        Cursor cursor = sqlf1.getdata("SELECT * FROM FoodItem");
        list1.clear();
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            int price=cursor.getInt(2);
            double gram=cursor.getDouble(3);
            byte[] image = cursor.getBlob(4);
            list1.add(new fooddisplay(id,name,price,gram,image));
        }
        adeptor1.notifyDataSetChanged();
    }
}
*/
