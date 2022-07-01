package com.example.smartorder1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
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
import java.util.Arrays;

public class adminpage extends AppCompatActivity  {

    EditText editname,typen;
    Button imgbut,catbut,vbut,tbut;
    ImageView imgcat;
    public static sqlcategory sqlc;
    public static sqltype sqlt;
    Spinner ftype;
    String[] foodarr;
    public static int i=0,flag=0,flag1=0,x=0,z=1,f8=0;
    public static String[] getftype=new String[30];
    public static String[] foodarray=new String[50];
    ArrayList<String> farr;
    AwesomeValidation aw;

    final int req=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        aw = new AwesomeValidation(ValidationStyle.BASIC);

        aw.addValidation(this,R.id.addcat,"^[A-Za-z\\s]{1,}$", R.string.invalid);
        aw.addValidation(this,R.id.addcat, RegexTemplate.NOT_EMPTY,R.string.empty_msg);

        /*ftype=(Spinner)findViewById(R.id.type);
        foodarr=getResources().getStringArray(R.array.foodtype);
        farr=new ArrayList<>(Arrays.asList(foodarr));
        final ArrayAdapter adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,farr);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ftype.setAdapter(adapter1);

        ftype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(editname.getText().toString().equals("") && position!=0)
               {
                   Toast.makeText(adminpage.this, " enter category", Toast.LENGTH_SHORT).show();
               }
                else if(position!=0) {
                    if(flag1==0 )
                    {
                        foodarray[x]=editname.getText().toString().trim();
                        flag1=1;
                    }
                    for (int j = 0; j < i; j++) {
                        if (foodarr[position].equals(getftype[j])) {
                            flag = 1;
                        }
                    }
                    if (flag == 1) {
                           Toast.makeText(adminpage.this, foodarr[position] + " is already Added", Toast.LENGTH_SHORT).show();
                        flag = 0;
                    } else {
                        Toast.makeText(adminpage.this, foodarr[position] + " Added", Toast.LENGTH_SHORT).show();
                        foodarray[x]+=" "+foodarr[position];
                        getftype[i] = foodarr[position];
                        i++;
                    }
                    // farr.remove(position);
                    //adapter1.notifyDataSetChanged();
                    ftype.setSelection(0);
                }
                Toast.makeText(adminpage.this, foodarray[x], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        init();
        sqlc=new sqlcategory(this,"categoryDB.sqlite",null,1);
        sqlc.querydata("CREATE TABLE IF NOT EXISTS Category (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, img BLOG)");

        sqlt=new sqltype(this,"typeDB.sqlite",null,1);
        sqlt.querydata("CREATE TABLE IF NOT EXISTS Type (TID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, cid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID))");


        imgbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        adminpage.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        req
                );
                f8=1;
            }
        });

        catbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(aw.validate() && f8==1) {
                    String temp = editname.getText().toString().toUpperCase().trim();
                    Cursor cursor6 = sqlc.getdata("SELECT name from Category");

                    String name;
                    while (cursor6.moveToNext()) {
                        name = cursor6.getString(0);
                        if (name.equals(temp)) {
                            Toast.makeText(getApplicationContext(), name + "  Already Added", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    try {
                        sqlc.insertData(
                                editname.getText().toString().trim().toUpperCase(),
                                imageViewToByte(imgcat)
                        );
                        Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();
                        flag = 1;
                        z++;
                        editname.setText("");
                        imgcat.setImageResource(R.drawable.fooditem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    f8=0;
                }
            }
        });

        vbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                Intent intent =new Intent(adminpage.this,categorylist.class);
                startActivity(intent);
            }
        });

        tbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String tname1 = typen.getText().toString().toUpperCase().trim();
                if(aw.validate() && !(tname1.equals("")) ) {
                    String temp = editname.getText().toString().toUpperCase();
                    Cursor cursor6 = sqlc.getdata("SELECT name from Category");

                    String name;
                    while (cursor6.moveToNext()) {
                        name = cursor6.getString(0);
                        if (name.equals(temp)) {
                            Toast.makeText(getApplicationContext(), name + "  Already Added", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String tname = typen.getText().toString().toUpperCase();
                    Cursor cursor5 = sqlt.getdata("SELECT TID FROM Type where name='" + tname + "' AND cid=" + z);
                    String name1;
                    while (cursor5.moveToNext()) {
                        name1 = cursor5.getString(0);
                        if (!name1.equals(null)) {
                            Toast.makeText(getApplicationContext(), name1 + "  Already Added", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    try {
                        sqlt.insertData(
                                typen.getText().toString().trim().toUpperCase(), z
                        );
                        Toast.makeText(getApplicationContext(), editname.getText().toString().trim() + typen.getText().toString() + "  Added Successfully", Toast.LENGTH_SHORT).show();
                        typen.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), " Please Add Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytea=stream.toByteArray();
        return bytea;
    }
    @Override
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == req && resultCode == RESULT_OK &&  data != null)
        {

            Uri uri=data.getData();
            try{
                InputStream in= getContentResolver().openInputStream(uri);

                Bitmap bmap= BitmapFactory.decodeStream(in);
                imgcat.setImageBitmap(bmap);
                }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void btn_food()
    {
        Intent intent =new Intent(adminpage.this,fooditem.class);
        startActivity(intent);
    }

    private void init()
    {
        editname=(EditText)findViewById(R.id.addcat);
        typen=(EditText)findViewById(R.id.type);
        imgbut=(Button)findViewById(R.id.imgb);
        catbut=(Button)findViewById(R.id.addc);
        imgcat=(ImageView)findViewById(R.id.imgc);
        vbut=(Button)findViewById(R.id.viewcat);
        tbut=(Button)findViewById(R.id.tbut);

    }
}
