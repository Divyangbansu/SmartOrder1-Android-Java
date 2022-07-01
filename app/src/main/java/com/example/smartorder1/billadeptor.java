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

public class billadeptor extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<bill> blist;

    public billadeptor(Context context, int layout, ArrayList<bill> blist) {
        this.context = context;
        this.layout = layout;
        this.blist = blist;
    }

    @Override
    public int getCount() {
        return blist.size();
    }

    @Override
    public Object getItem(int position) {
        return blist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class viewHolder {
        TextView bfname;
        TextView bfprice;
        TextView bfquan;
        TextView bftotprice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row =convertView;
        viewHolder holder = new viewHolder();

        if(row == null)
        {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(layout,null);
            holder.bfname = (TextView) row.findViewById(R.id.billfoodname);
            holder.bfprice = (TextView) row.findViewById(R.id.billfoodprice);
            holder.bfquan = (TextView) row.findViewById(R.id.billfoodquan);
            holder.bftotprice = (TextView) row.findViewById(R.id.billtotalprice);
            row.setTag(holder);
        }
        else
        {
            holder = (viewHolder) row.getTag();
        }
       bill bill1= blist.get(position);

        holder.bfname.setText(bill1.getName());
        String temp1=Double.toString(bill1.getPrice());
        holder.bfprice.setText(temp1);
        String temp=Long.toString(bill1.getQuan());
        holder.bfquan.setText(temp);
        String temp2=Double.toString(bill1.getTotprice());
        holder.bftotprice.setText(temp2);
        return row;
    }

}

