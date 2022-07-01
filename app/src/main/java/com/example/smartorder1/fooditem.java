package com.example.smartorder1;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;

public class fooditem extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout mTabLayout;
    public static  sqltype sqlt3;
    public static String fdis[]=new String[10];
    public static int rs=0;
    int index1=0;
    public static FloatingActionButton flbut,billbut;
    private LinearLayout fabContainer;
    private FloatingActionButton fab;
    private boolean fabMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem);
        sqlt3=new sqltype(this,"typeDB.sqlite",null,1);
        sqlt3.querydata("CREATE TABLE IF NOT EXISTS Type (TID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, cid INTEGER , FOREIGN KEY (cid) REFERENCES Category(ID))");

        flbut= findViewById(R.id.flbut);
        flbut.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),cartlist.class)));

        billbut= findViewById(R.id.billbut);
        billbut.setClickable(false);
        if(cartlist.disb==1) {
            billbut.setClickable(true);
        }
        billbut.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), billList.class)));

        fabContainer = (LinearLayout) findViewById(R.id.fabContainerLayout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabMenu();
            }
        });




        initViews();

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


    private void initViews(){
        viewPager = findViewById(R.id.vp);
        mTabLayout =  findViewById(R.id.tbl);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        setDynamicFragmentToTabLayout();



    }

    private void setDynamicFragmentToTabLayout() {

        int temppos=0;

        temppos=categorylist.pos+1;


       final Cursor cursor5 = sqlt3.getdata("SELECT name FROM Type where ci" +
               "d="+temppos);

        String name1="";
        while(cursor5.moveToNext())
        {
            name1=cursor5.getString(0);
            Toast.makeText(getApplicationContext(),name1,Toast.LENGTH_SHORT).show();
            mTabLayout.addTab(mTabLayout.newTab().setText(name1));
            fdis[index1]=name1;
            index1++;
        }
        DynamicFragmentAdapter mDynamicFragmentAdapter = new DynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(mDynamicFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    onChangeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);


       /*for (int i = 0; i < 5; i++) {

            mTabLayout.addTab(mTabLayout.newTab().setText("Category: " + i));
        }
        DynamicFragmentAdapter mDynamicFragmentAdapter = new DynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(mDynamicFragmentAdapter);
        viewPager.setCurrentItem(0);*/

    }

    private void onChangeTab(int position) {

    }
}
