package com.example.smartorder1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class foodadeptor extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<fooddisplay> clist;
    public static  sqltempcart sqltc;
    long quan=1;
    public static int f123=0;

    public foodadeptor(Context context, int layout, ArrayList<fooddisplay> clist) {
        this.context = context;
        this.layout = layout;
        this.clist = clist;
    }

    @Override
    public int getCount() {
        return clist.size();
    }

    @Override
    public Object getItem(int position) {
        return clist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class viewHolder {
        ImageView imageview;
        TextView name;
        TextView price;
        TextView gram;
        Button addid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        sqltc=new sqltempcart(parent.getContext(),"tempcartDB.sqlite",null,1);
        sqltc.querydata("CREATE TABLE IF NOT EXISTS Tempcart (TCID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, price DOUBLE, quan INTEGER ,img BLOG )");

        View row =convertView;
        viewHolder holder = new viewHolder();

        if(row == null)
        {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(layout,null);

            holder.name = (TextView) row.findViewById(R.id.nid);
            holder.price = (TextView) row.findViewById(R.id.pid);
            holder.gram = (TextView) row.findViewById(R.id.gid);
            holder.imageview = (ImageView) row.findViewById(R.id.imgfood1);
            holder.addid=(Button)row.findViewById(R.id.add123);
            row.setTag(holder);
        }
        else
        {
            holder = (viewHolder) row.getTag();
        }
        fooddisplay food= clist.get(position);

        holder.name.setText(food.getName());
        String temp1=Integer.toString(food.getPrice());
        holder.price.setText(temp1);
        String temp=Double.toString(food.getGram());
        holder.gram.setText(temp);


        byte[] foodimage= food.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(foodimage,0,foodimage.length);
        holder.imageview.setImageBitmap(bitmap);

        holder.addid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f123=0;
                Cursor cursor7 = sqltc.getdata("SELECT * from TempCart");
                String name;

                while (cursor7.moveToNext()) {
                    name = cursor7.getString(1);
                    if (name.equals(food.getName())) {
                        Toast.makeText(v.getContext(),food.getName() +"Already Added", Toast.LENGTH_SHORT).show();
                        f123=1;
                    }
                }
                double t=food.getPrice();
                String i1=Double.toString(t);
                if(f123!=1) {
                    try {
                        sqltc.insertData(
                                food.getName(),
                                i1,
                                quan,
                                food.getImage()
                        );
                        Toast.makeText(parent.getContext(), food.getName() + " Added Successfully In Cart ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return row;
    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytea=stream.toByteArray();
        return bytea;
    }

}
