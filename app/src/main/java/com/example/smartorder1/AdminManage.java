package com.example.smartorder1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;

public class AdminManage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout dl;
    private ActionBarDrawerToggle abt;
    Spinner tablevalue;
    Button placebut;
    int tabflag=0;
    public static String tablename="Table-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);
        getSupportActionBar().setTitle("Manage Details");
        dl = (DrawerLayout) findViewById(R.id.did);
        tablevalue=(Spinner)findViewById(R.id.tabspin);
        placebut=(Button)findViewById(R.id.placebut);
        ArrayAdapter<CharSequence> ad=ArrayAdapter.createFromResource(this,R.array.tablearray,android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tablevalue.setAdapter(ad);
        tablevalue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    tabflag=1;
                    tablename=parent.getItemAtPosition(position).toString().trim();
                    Toast.makeText(getApplicationContext(),tablename,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        placebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabflag==1) {
                    adminpage.flag = 1;
                    startActivity(new Intent(getApplicationContext(), categorylist.class));
                    tabflag=0;
                }
            }
        });
        abt= new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        dl.addDrawerListener(abt);
        abt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv=(NavigationView)findViewById(R.id.nview);
        nv.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(abt.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if(id==R.id.category)
        {
            startActivity(new Intent(getApplicationContext(),adminpage.class));
        }
        else if(id == R.id.food)
        {
            startActivity(new Intent(getApplicationContext(),managefood.class));
        }
        else if(id == R.id.taxes)
        {
            startActivity(new Intent(getApplicationContext(),managetaxes.class));
        }
        else if(id == R.id.type)
        {
            startActivity(new Intent(getApplicationContext(),addtype.class));
        }
        return false;
    }
}
