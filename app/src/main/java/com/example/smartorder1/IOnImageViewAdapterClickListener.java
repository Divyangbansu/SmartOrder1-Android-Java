package com.example.smartorder1;

import android.view.View;

interface IOnImageViewAdapterClickListener {
    void onCalListener(View view,int position,boolean isDecrease,boolean isDelete);
}
