package com.example.smartorder1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Add_details extends AppCompatActivity implements View.OnTouchListener {

    public static sqlcategory sqlc1;
    public static String custname = "";
    public static long mobilenum = 0;
    EditText name, mob;
    ImageView adminLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        name = (EditText) findViewById(R.id.custuser);
        mob = (EditText) findViewById(R.id.mobile);
        adminLog = (ImageView) findViewById(R.id.imageView2);
        adminLog.setOnTouchListener(this);
        sqlc1 = new sqlcategory(this, "categoryDB.sqlite", null, 1);
        sqlc1.querydata("CREATE TABLE IF NOT EXISTS Category (ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, img BLOG)");
    }

    public void btn_category(View view) {
       /* //get data from sql
        AwesomeValidation aw;
        aw = new AwesomeValidation(ValidationStyle.BASIC);
        aw.addValidation(this,R.id.mobile, "^[0-9]{10}$",R.string.err_cust4);
        aw.addValidation(this,R.id.custuser,"^[A-Za-z]{1,}$",R.string.err_cust3);
        aw.addValidation(this,R.id.custuser, RegexTemplate.NOT_EMPTY,R.string.err_cust1);
        aw.addValidation(this,R.id.mobile, RegexTemplate.NOT_EMPTY,R.string.err_cust2);
        if(aw.validate())
        {
            custname=name.getText().toString().trim();
            mobilenum=Long.parseLong(mob.getText().toString().trim());
            Toast.makeText(getApplicationContext(),custname+mob.getText().toString(),Toast.LENGTH_SHORT).show();*/
        adminpage.flag = 1;
        startActivity(new Intent(getApplicationContext(), categorylist.class));
        // }
    }
   /* public void adminlog(View view)
    {
         Toast.makeText(getApplicationContext(),"Hii",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), adminlogin.class));
    }*/

    final Runnable run = new Runnable() {

        @Override
        public void run() {

            startActivity(new Intent(getApplicationContext(), adminlogin.class));
            // Your code to run on long click

        }
    };
    final Handler handel = new Handler();

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handel.postDelayed(run, 3000/* OR the amount of time you want */);
                    break;

                default:
                    handel.removeCallbacks(run);
                    break;

            }
            return true;
        }


}
