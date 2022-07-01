package com.example.smartorder1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class categoryadeptor extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<categoryitem> clist;
    public static int c123=0;

    public categoryadeptor(Context context, int layout, ArrayList<categoryitem> clist) {
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
        CircleImageView imageview;
        TextView txtname;
        CardView c1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row =convertView;
        viewHolder holder = new viewHolder();

        if(row == null)
        {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflator.inflate(layout,null);

            holder.txtname = (TextView) row.findViewById(R.id.kl);
            holder.imageview =  row.findViewById(R.id.imgfood);
            holder.c1=  row.findViewById(R.id.cdview);
            row.setTag(holder);
        }
        else
        {
            holder = (viewHolder) row.getTag();
        }
        categoryitem category= clist.get(position);

        if(c123==0) {
            holder.c1.setCardBackgroundColor(Color.parseColor("#8BD8BD"));
            holder.txtname.setTextColor(Color.parseColor("#243665"));
            c123=1;
        }
        else if(c123==1)
        {
            holder.c1.setCardBackgroundColor(Color.parseColor("#EC8B5E"));
            holder.txtname.setTextColor(Color.parseColor("#141A46"));
            c123=2;
        }
        else if(c123==2)
        {
            holder.c1.setCardBackgroundColor(Color.parseColor("#EE4E34"));
            holder.txtname.setTextColor(Color.parseColor("#FCEDDA"));
            c123=0;
        }
        holder.txtname.setText(category.getName());

        byte[] foodimage= category.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(foodimage,0,foodimage.length);
        holder.imageview.setImageBitmap(bitmap);
        return row;
    }

}
