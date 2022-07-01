package com.example.smartorder1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.smartorder1.managetaxes.ctax;
import static com.example.smartorder1.managetaxes.stax;

public class billList extends AppCompatActivity {
    GridView gridView;
    ArrayList<bill> list;
    billadeptor adeptor=null;
    public static sqlpermcart sqlp2;
    public static sqlfood sqlf2;
    public static double Subtotal=0,Total=0,Cgst=0,Sgst=0;
    Button bt1;

    TextView subtotal,cgst,sgst,total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);

        sqlf2=new sqlfood(getApplicationContext(),"FoodDB.sqlite",null,1);
        sqlf2.querydata("CREATE TABLE IF NOT EXISTS FoodItem (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,  price INTEGER, gram DOUBLE, img BLOG , cid INTEGER  , tid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID) , FOREIGN KEY (tid) REFERENCES Type(TID))");

        sqlp2=new sqlpermcart(this,"permcartDB.sqlite",null,1);
        sqlp2.querydata("CREATE TABLE IF NOT EXISTS Permcart (PCID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, price DOUBLE, quan INTEGER)");

        bt1=(Button)findViewById(R.id.btn_bill);
        gridView =(GridView) findViewById(R.id.grid_bill);
        list=new ArrayList<>();
        adeptor=new billadeptor(this,R.layout.bill_item_layout,list);
        gridView.setAdapter(adeptor);

        subtotal=(TextView)findViewById(R.id.bsubtot);
        total=(TextView)findViewById(R.id.btot);
        sgst=(TextView)findViewById(R.id.bsgst);
        cgst=(TextView)findViewById(R.id.bcgst);

        cgst.setText("");
        sgst.setText("");
        total.setText("");
        subtotal.setText("");
        Cursor cursor = sqlp2.getdata("SELECT * FROM Permcart");
        list.clear();
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            Cursor cursor1 = sqlf2.getdata("SELECT price FROM FoodItem where name ='"+name +"';");
            double price=0;
            while (cursor1.moveToNext())
            {
                price=cursor1.getDouble(0);

            }
           long quan=cursor.getLong(3);
            double totprice=cursor.getDouble(2);
            list.add(new bill(id,name,price,quan,totprice));
            Subtotal=Subtotal+totprice;
        }
        adeptor.notifyDataSetChanged();

        Cgst=(ctax*Subtotal)/100;
        Sgst=(stax*Subtotal)/100;

        cgst.setText((Double.toString(Cgst)));
        sgst.setText((Double.toString(Sgst)));
        Total=Subtotal+Cgst+Sgst;
        total.setText((Double.toString(Total)));
        subtotal.setText((Double.toString(Subtotal)));

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(billList.this).create();
                alertDialog.setTitle("Thank You");
                alertDialog.setMessage("\t\t\t\t\t\t\t\t\t\tThank You ! \n\t\t\t\t\t\t\t\t\t Visit Again...!!!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                cartlist.back=0;
                sqlp2.querydata("DELETE from Permcart ;");
                cgst.setText("0");
                sgst.setText("0");
                total.setText("0");
                subtotal.setText("0");
                startActivity(new Intent(getApplicationContext(),Add_details.class));
            }
        });
    }
}
