package com.example.smartorder1;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class categorylist extends AppCompatActivity {
    GridView gridView;
    ArrayList<categoryitem> list;
    categoryadeptor adeptor=null;
    public static TextView txtname1;
    public static int pos;
    CardView cd;
    static FloatingActionButton fb,bbut;
    private LinearLayout fabContainer;
    private FloatingActionButton fab;
    private boolean fabMenuOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category1);
        gridView =(GridView) findViewById(R.id.gridView);
        fb=findViewById(R.id.cflbut);
        bbut=findViewById(R.id.cbillbut);
        list=new ArrayList<>();
        adeptor=new categoryadeptor(this,R.layout.customcategory,list);
        gridView.setAdapter(adeptor);
        cd=(CardView)findViewById(R.id.cdview);
        //get data from sql

        if(adminpage.flag==0) {
            Cursor cursor = adminpage.sqlc.getdata("SELECT * FROM Category ");
            list.clear();
            while(cursor.moveToNext())
            {
                int id=cursor.getInt(0);
                String name=cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                list.add(new categoryitem(id,name,image));
            }
            adeptor.notifyDataSetChanged();
        }
        else {
            Cursor cursor1 = Add_details.sqlc1.getdata("SELECT * FROM Category ");
            list.clear();
            while(cursor1.moveToNext())
            {
                int id=cursor1.getInt(0);
                String name=cursor1.getString(1);
                byte[] image = cursor1.getBlob(2);
                list.add(new categoryitem(id,name,image));
            }
            adeptor.notifyDataSetChanged();
        }

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            pos=position;
            fooditem.rs=0;
            startActivity(new Intent(getApplicationContext(),fooditem.class));
        });

        fabContainer = (LinearLayout) findViewById(R.id.cfabContainerLayout1);

        fab = (FloatingActionButton) findViewById(R.id.cfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabMenu();
            }
        });

       fb.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),cartlist.class)));

        bbut.setClickable(false);
        if(cartlist.disb==1) {
            bbut.setClickable(true);
        }
        bbut.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),billList.class)));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleFabMenu() {
        if (!fabMenuOpen) {
            fab.setImageResource(R.drawable.ic_shopping_cart_24dp);
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = 0;
            int endRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;

            fabContainer.setVisibility(View.VISIBLE);
            ViewAnimationUtils
                    .createCircularReveal(
                            fabContainer,
                            centerX,
                            centerY,
                            startRadius,
                            endRadius
                    )
                    .setDuration(1000)
                    .start();
        } else {
            fab.setImageResource(R.drawable.ic_shopping_cart_24dp);
            int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;
            int endRadius = 0;

            Animator animator = ViewAnimationUtils
                    .createCircularReveal(
                            fabContainer,
                            centerX,
                            centerY,
                            startRadius,
                            endRadius
                    );
            animator.setDuration(1000);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fabContainer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
        }
        fabMenuOpen = !fabMenuOpen;
    }

    @Override
    public void onBackPressed() {
        if(cartlist.back!=1)
        {
            super.onBackPressed();
        }
    }
}



