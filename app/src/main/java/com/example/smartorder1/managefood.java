package com.example.smartorder1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class managefood extends AppCompatActivity {

    EditText food,price,gram;
    Button imgbut,foodbut,vbut;
    ImageView imgfood;
    Spinner category,foodtype;
    public static sqlfood sqlf;
    public static sqlcategory sqlc2;
    public static  sqltype sqlc3;
    final int req=999;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    ArrayAdapter<String> adepter1,adepter2;
    Integer tempid,f6=0,f7=0;
    String cat;
    AwesomeValidation aw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managefood);
        aw = new AwesomeValidation(ValidationStyle.BASIC);

        aw.addValidation(this,R.id.foodid,"^[A-Za-z\\s]{1,}$", R.string.invalid);
        aw.addValidation(this,R.id.priceid,"^[0-9]{1,}$", R.string.invalidnum);
        aw.addValidation(this,R.id.gramid,"^[0-9]{1,}$", R.string.invalidnum);
        aw.addValidation(this,R.id.foodid, RegexTemplate.NOT_EMPTY,R.string.empty_msg);
        aw.addValidation(this,R.id.priceid, RegexTemplate.NOT_EMPTY,R.string.empty_msg);
        aw.addValidation(this,R.id.gramid, RegexTemplate.NOT_EMPTY,R.string.empty_msg);

        sqlc2=new sqlcategory(this,"categoryDB.sqlite",null,1);
        sqlc2.querydata("CREATE TABLE IF NOT EXISTS Category (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, img BLOG)");
        sqlc3=new sqltype(this,"typeDB.sqlite",null,1);
        sqlc3.querydata("CREATE TABLE IF NOT EXISTS Type (TID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, cid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID))");
        init();

        final Cursor cursor = sqlc2.getdata("SELECT name FROM Category ");
        list.clear();
        String name;
        name="-->>  Select Category  <<-- ";
        list.add(name);
        while(cursor.moveToNext())
        {
            name=cursor.getString(0);
            list.add(name);
        }
        adepter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        category.setAdapter(adepter1);
        adepter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adepter1.notifyDataSetChanged();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = sqlc3.getdata("SELECT name FROM Type where cid="+position);
                list2.clear();
                String name1;
                name1="-->>  Select Type  <<-- ";
                list2.add(name1);
                while(cursor1.moveToNext())
                {
                    name1=cursor1.getString(0);
                    list2.add(name1);
                }
                adepter2=new ArrayAdapter<String>(foodtype.getContext(),android.R.layout.simple_spinner_item,list2);
                foodtype.setAdapter(adepter2);
                adepter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adepter2.notifyDataSetChanged();
                if(!foodtype.isEnabled() && position!=0)
                {
                    foodtype.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        foodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    cat = foodtype.getSelectedItem().toString();
                    Cursor cursor3 = sqlc3.getdata("SELECT TID FROM Type where name='"+cat+"' AND cid="+category.getSelectedItemPosition());
                    cursor3.moveToFirst();
                    tempid=cursor3.getInt(0);
                    f6=1;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sqlf=new sqlfood(this,"FoodDB.sqlite",null,1);
        sqlf.querydata("CREATE TABLE IF NOT EXISTS FoodItem (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,  price INTEGER, gram DOUBLE, img BLOG , cid INTEGER  , tid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID) , FOREIGN KEY (tid) REFERENCES Type(TID))");

        imgbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        managefood.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        req
                );
                f7=1;
            }
        });

        foodbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(aw.validate() && f6==1 && f7==1) {
                    String temp = food.getText().toString().toUpperCase();
                    Cursor cursor6 = sqlf.getdata("SELECT name from FoodItem");

                    String name;
                    while (cursor6.moveToNext()) {
                        name = cursor6.getString(0);
                        if (name.equals(temp)) {
                            Toast.makeText(getApplicationContext(), "  Already Added", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    try {
                        sqlf.insertData(
                                food.getText().toString().trim().toUpperCase(),
                                price.getText().toString().trim(),
                                gram.getText().toString().trim(),
                                imageViewToByte(imgfood),
                                category.getSelectedItemPosition(),
                                tempid
                        );
                        Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();
                        food.setText("");
                        price.setText("");
                        gram.setText("");
                        category.setSelection(0);
                        foodtype.setSelection(0);
                        imgfood.setImageResource(R.drawable.fooditem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    f7=0;
                    f6=0;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*vbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/




    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytea=stream.toByteArray();
        return bytea;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==req)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Intent i1=new Intent(Intent.ACTION_PICK);
                i1.setType("image/*");
                startActivityForResult(i1,req);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"You Don't Have Permission to Access File Location",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == req && resultCode == RESULT_OK &&  data != null)
        {

            Uri uri=data.getData();
            try{
                InputStream in= getContentResolver().openInputStream(uri);

                Bitmap bmap= BitmapFactory.decodeStream(in);
                imgfood.setImageBitmap(bmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init()
    {
        food=(EditText)findViewById(R.id.foodid);
        category=(Spinner)findViewById(R.id.categoryid);
        foodtype=(Spinner)findViewById(R.id.foodtypeid);
        price=(EditText)findViewById(R.id.priceid);
        gram=(EditText)findViewById(R.id.gramid);
        imgbut=(Button)findViewById(R.id.imgfoodbut);
        foodbut=(Button)findViewById(R.id.addfood);
        imgfood=(ImageView)findViewById(R.id.imgfood);
        vbut=(Button)findViewById(R.id.viewfood);
        foodtype.setEnabled(false);
    }

}
