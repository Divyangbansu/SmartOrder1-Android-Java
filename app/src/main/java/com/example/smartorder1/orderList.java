package com.example.smartorder1;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class orderList extends AppCompatActivity {

    GridView gridView;
    ArrayList<order> list;
    orderadeptor adeptor=null;
    public static sqlpermcart sqlp3;
    public static sqlfood sqlf5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        sqlp3=new sqlpermcart(this,"permcartDB.sqlite",null,1);
        sqlp3.querydata("CREATE TABLE IF NOT EXISTS Permcart (PCID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, price DOUBLE, quan INTEGER)");

        sqlf5=new sqlfood(getApplicationContext(),"FoodDB.sqlite",null,1);
        sqlf5.querydata("CREATE TABLE IF NOT EXISTS FoodItem (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,  price INTEGER, gram DOUBLE, img BLOG , cid INTEGER  , tid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID) , FOREIGN KEY (tid) REFERENCES Type(TID))");


        gridView =(GridView) findViewById(R.id.grid_order);
        list=new ArrayList<>();
        adeptor=new orderadeptor(this,R.layout.order_item_layout,list);
        gridView.setAdapter(adeptor);

        Cursor cursor = sqlp3.getdata("SELECT * FROM Permcart");
        list.clear();
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            Cursor cursor1 = sqlf5.getdata("SELECT price FROM FoodItem where name ='"+name +"';");
            double price=0;
            while (cursor1.moveToNext())
            {
                price=cursor1.getDouble(0);

            }
            long quan=cursor.getLong(3);
            list.add(new order(id,name,price,quan));
        }
        adeptor.notifyDataSetChanged();


    }
}
