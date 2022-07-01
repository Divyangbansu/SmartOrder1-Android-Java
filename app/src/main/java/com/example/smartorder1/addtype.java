package com.example.smartorder1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.smartorder1.managefood.sqlc2;
import static com.example.smartorder1.managefood.sqlc3;

public class addtype extends AppCompatActivity {

    EditText type;
    Spinner cate;
    Button sub;
    String cname;
    public static sqlcategory sqlc4;
    public static sqltype sqlt;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adepter1;
    int fl2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtype);
        type=(EditText)findViewById(R.id.typeid);
        cate=(Spinner)findViewById(R.id.catid);
        sub=(Button)findViewById(R.id.addtype);
        sqlc4=new sqlcategory(this,"categoryDB.sqlite",null,1);
        sqlc4.querydata("CREATE TABLE IF NOT EXISTS Category (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, img BLOG)");
        sqlt=new sqltype(this,"typeDB.sqlite",null,1);
        sqlt.querydata("CREATE TABLE IF NOT EXISTS Type (TID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, cid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID))");


        final Cursor cursor4 = sqlc4.getdata("SELECT name FROM Category ");
        list.clear();
        String name;
        name="-->>  Select Category  <<-- ";
        list.add(name);
        while(cursor4.moveToNext())
        {
            name=cursor4.getString(0);
            list.add(name);
        }
        adepter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        cate.setAdapter(adepter1);
        adepter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adepter1.notifyDataSetChanged();

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cname=type.getText().toString().toUpperCase();
                if(cate.getSelectedItemPosition()!=0) {
                    Cursor cursor5 = sqlt.getdata("SELECT TID FROM Type where name='" + cname + "' AND cid=" + cate.getSelectedItemPosition());
                    String name;
                    while (cursor5.moveToNext()) {
                        name = cursor5.getString(0);
                        if (!name.equals(null)) {
                            Toast.makeText(getApplicationContext(), "  Already Added", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    try{
                        sqlt.insertData(
                                type.getText().toString().trim().toUpperCase(),cate.getSelectedItemPosition()
                        );
                        Toast.makeText(getApplicationContext(), cate.getSelectedItemPosition()+ type.getText().toString() + "  Added Successfully",Toast.LENGTH_SHORT).show();
                        type.setText("");
                        cate.setSelection(0);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
