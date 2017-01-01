package com.aphidmobile.flip.haruweather.components;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 星野悠 on 2017/1/1.
 */

public class HDrawerLayout extends DrawerLayout{
    private View menu ;
    public HDrawerLayout(Context context) {
        super(context);
    }

    public HDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.menu = this.getChildAt(1) ;
    }

    public HDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("TAG", "l:" + l) ;
    }
}
