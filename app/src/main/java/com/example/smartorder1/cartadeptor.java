package com.example.smartorder1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class cartadeptor extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<cart> clist;
    public static int w;
    public static double mul,p,total,t1;
    cartadeptor cartadeptor=this;

    public cartadeptor(Context context, int layout, ArrayList<cart> clist) {
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
        ImageView cartimg,cartdelete;
        TextView cfname;
        TextView cfprice;
        ElegantNumberButton ebquan;
    }

    @Override
    public View getView(int position,final View convertView, final ViewGroup parent) {
        View row =convertView;
        viewHolder holder = new viewHolder();

        if(row == null)
        {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(layout,null);

            holder.cfname = (TextView) row.findViewById(R.id.cartfoodname);
            holder.cfprice = (TextView) row.findViewById(R.id.cartprice);
            holder.cartimg = (ImageView) row.findViewById(R.id.cartimg);
            holder.ebquan=(ElegantNumberButton)row.findViewById(R.id.elb);
            holder.cartdelete=(ImageView)row.findViewById(R.id.cartdelete);
            row.setTag(holder);
        }
        else
        {
            holder = (viewHolder) row.getTag();
        }
        cart cart1= clist.get(position);

        holder.cfname.setText(cart1.getName());
        int a=cart1.getId();
        String temp1=Double.toString(cart1.getPrice());
        holder.cfprice.setText(temp1);
        String temp=Long.toString(cart1.getQuan());
        holder.ebquan.setNumber(temp);

        byte[] foodimage= cart1.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(foodimage,0,foodimage.length);
        holder.cartimg.setImageBitmap(bitmap);

        w=a;

        viewHolder finalHolder = holder;

        holder.ebquan.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                total=0;
                foodadeptor.sqltc.querydata("UPDATE Tempcart SET  quan="+newValue+" where TCID="+a);
                Cursor cursor8 = DynamicFragment.sqlf1.getdata("SELECT price from FoodItem where name='"+cart1.getName()+"';");
                while(cursor8.moveToNext()) {
                    p = cursor8.getDouble(0);
                        mul= p * newValue;
                        foodadeptor.sqltc.querydata("UPDATE Tempcart SET  price=" + mul + " where TCID=" + a);
                }
                Cursor cursor9 = foodadeptor.sqltc.getdata("SELECT price from Tempcart;");
                while(cursor9.moveToNext()) {
                    t1 = cursor9.getDouble(0);
                        total = total + t1;
                }
                String temp2 = Double.toString(mul);
                finalHolder.cfprice.setText(temp2);
                String temp3 = Double.toString(total);
                cartlist.temptot.setText(temp3);
                cart1.setPrice(mul);
                int y=newValue;
                cart1.setQuan(y);
                Long tem=cart1.getQuan();
                String s=Long.toString(tem);
                finalHolder.ebquan.setNumber(s,true);
            }
        });

        final int curr=position;
        cart c= clist.get(position);
        holder.cartdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "Cart Deleted" + curr, Toast.LENGTH_SHORT).show();
                removeitem(c,a);
                notifyDataSetChanged();
            }
        });
        return row;
    }

    public void removeitem(cart c2,int a)
    {
        double total1=0,t2;
        int pos=clist.indexOf(c2);
        clist.remove(pos);
        foodadeptor.sqltc.querydata("DELETE from Tempcart where TCID=" + a);
        Cursor cursor9 = foodadeptor.sqltc.getdata("SELECT price from Tempcart;");
        while(cursor9.moveToNext()) {
            t2 = cursor9.getDouble(0);
            total1 = total1 + t2;
        }
        String temp3 = Double.toString(total1);
        cartlist.temptot.setText(temp3);

        if(cartlist.list.size()==0)
        {
            cartlist.temptot.setText("Empty cart");
            cartlist.emp.setVisibility(View.VISIBLE);
        }
    }
}
