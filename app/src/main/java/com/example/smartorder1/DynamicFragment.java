package com.example.smartorder1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class DynamicFragment extends Fragment  {
    GridView gridView1;
    ArrayList<fooddisplay> list1;
    foodadeptor adeptor1=null;
    public static sqlfood sqlf1;
    public static sqlcategory sqlcat;
    public static sqltype sqlt5;
    int tempid=0;

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_food1, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
      //  startActivity(new Intent(getActivity(),food1.class));

        gridView1 =(GridView) view.findViewById(R.id.gridView1);
        list1=new ArrayList<>();
        adeptor1=new foodadeptor(getContext(),R.layout.customfood,list1);
        gridView1.setAdapter(adeptor1);

        sqlcat=new sqlcategory(getContext(),"categoryDB.sqlite",null,1);
        sqlcat.querydata("CREATE TABLE IF NOT EXISTS Category (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, img BLOG)");
        sqlt5=new sqltype(getContext(),"typeDB.sqlite",null,1);
        sqlt5.querydata("CREATE TABLE IF NOT EXISTS Type (TID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, cid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID))");
        sqlf1=new sqlfood(getContext(),"FoodDB.sqlite",null,1);
        sqlf1.querydata("CREATE TABLE IF NOT EXISTS FoodItem (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,  price INTEGER, gram DOUBLE, img BLOG , cid INTEGER  , tid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID) , FOREIGN KEY (tid) REFERENCES Type(TID))");

            int temppos=0;
            temppos=categorylist.pos+1;
            String type1=fooditem.fdis[fooditem.rs];
            Cursor cursor3 = sqlt5.getdata("SELECT TID FROM Type where name='"+type1+"' AND cid="+temppos);
            cursor3.moveToFirst();
            tempid=cursor3.getInt(0);
            Cursor cursor = sqlf1.getdata("SELECT * FROM FoodItem where cid="+temppos+" AND tid="+tempid);
            list1.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                double gram = cursor.getDouble(3);
                byte[] image = cursor.getBlob(4);
                list1.add(new fooddisplay(id, name, price, gram, image));
            }
            adeptor1.notifyDataSetChanged();
            fooditem.rs++;
    }

    private Context getApplicationContext() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

