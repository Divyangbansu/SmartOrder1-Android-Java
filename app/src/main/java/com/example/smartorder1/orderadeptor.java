package com.example.smartorder1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class orderadeptor extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<order> olist;

    public orderadeptor(Context context, int layout, ArrayList<order> olist) {
        this.context = context;
        this.layout = layout;
        this.olist = olist;
    }

    @Override
    public int getCount() {
        return olist.size();
    }

    @Override
    public Object getItem(int position) {
        return olist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class viewHolder {
        TextView ofname;
        TextView ofprice;
        TextView ofquan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row =convertView;
        viewHolder holder = new viewHolder();

        if(row == null)
        {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(layout,null);
            holder.ofname = (TextView) row.findViewById(R.id.orderfoodname);
            holder.ofprice = (TextView) row.findViewById(R.id.orderprice);
            holder.ofquan = (TextView) row.findViewById(R.id.orderquan);
            row.setTag(holder);
        }
        else
        {
            holder = (viewHolder) row.getTag();
        }
        order order1= olist.get(position);

        holder.ofname.setText(order1.getName());
        String temp1=Double.toString(order1.getPrice());
        holder.ofprice.setText(temp1);
        String temp=Long.toString(order1.getQuan());
        holder.ofquan.setText(temp);
        return row;
    }

}

