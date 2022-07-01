package com.example.smartorder1;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class cartlist extends AppCompatActivity {

    GridView gridView1;
    static ArrayList<cart> list;
    cartadeptor adeptor=null;
    ElegantNumberButton elb;
    static TextView temptot,emp;
    public static int back=0,disb=0;
    double tot=0;
    Button porder;
    FloatingActionButton orderbut;
    AlertDialog.Builder builder;
    public static  sqlpermcart sqlpc;
    public static  sqltempcart sqltc2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sqltc2=new sqltempcart(getApplicationContext(),"tempcartDB.sqlite",null,1);
        sqltc2.querydata("CREATE TABLE IF NOT EXISTS Tempcart (TCID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, price DOUBLE, quan INTEGER ,img BLOG )");

        sqlpc=new sqlpermcart(this,"permcartDB.sqlite",null,1);
        sqlpc.querydata("CREATE TABLE IF NOT EXISTS Permcart (PCID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, price DOUBLE, quan INTEGER)");

        gridView1 =(GridView) findViewById(R.id.grid_cart);
        porder=(Button)findViewById(R.id.btn_placeorder);
        list=new ArrayList<>();
        builder = new AlertDialog.Builder(this);
        temptot=findViewById(R.id.temptot);
        emp=findViewById(R.id.empcart);
        orderbut=(FloatingActionButton)findViewById(R.id.orderbut);
        adeptor=new cartadeptor(this,R.layout.cart_item_layout,list);
        gridView1.setAdapter(adeptor);
        Cursor cursor = sqltc2.getdata("SELECT * FROM Tempcart ");
        list.clear();

        orderbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),orderList.class));
            }
        });
        emp.setVisibility(View.INVISIBLE);
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            double price=cursor.getDouble(2);
            long quan=cursor.getLong(3);
            byte[] image = cursor.getBlob(4);
            tot=tot+price;
            list.add(new cart(id,name,price,quan,image));
        }
        String temp2 = Double.toString(tot);
        temptot.setText(temp2);
        adeptor.notifyDataSetChanged();

        if(list.size()==0)
        {
            temptot.setText("Empty cart");
            emp.setVisibility(View.VISIBLE);
        }
        porder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.size()!=0) {
                    builder.setMessage("Do You Want to Confirm Your Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    AlertDialog alertDialog = new AlertDialog.Builder(cartlist.this).create();
                                    alertDialog.setTitle("Thank You");
                                    alertDialog.setMessage("\t\t\t\t\t\t\t\t\t\tThank You ! \n\t\t\t\t\t\t\t\t Your Order Is Confirmed..");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    adddata();
                                                    back=1;
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    finish();
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Confirmation");
                    alert.show();
                }
                else
                {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(cartlist.this).create();
                    alertDialog1.setTitle("Empty Cart");
                    alertDialog1.setMessage("\t\t\t\t\t\t\t\t\t\tCart Is Empty !!");
                    alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog1.show();
                    temptot.setText("Empty cart");
                    emp.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void adddata()
    {
            Cursor cursor =sqltc2.getdata("SELECT * FROM Tempcart ");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double price = cursor.getDouble(2);
                long quan = cursor.getLong(3);
                double t = price;
                String i1 = Double.toString(t);
                try {
                    sqlpc.insertData(
                            name,
                            i1,
                            quan
                    );
                    //Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
           sqltc2.querydata("DELETE from Tempcart");
            list.clear();
           disb=1;
            startActivity(new Intent(getApplicationContext(), categorylist.class));
        temptot.setText("Empty cart");
        emp.setVisibility(View.VISIBLE);
    }
}
