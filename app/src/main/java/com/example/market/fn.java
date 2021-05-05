package com.example.market;

import android.content.Context;
import android.util.TypedValue;

class fn{
    static public int dp(int i, Context c){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, i,
                c.getResources().getDisplayMetrics());
    }
}
