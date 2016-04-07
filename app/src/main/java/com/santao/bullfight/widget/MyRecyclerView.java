package com.santao.bullfight.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;



public class MyRecyclerView extends RecyclerView {


    public MyRecyclerView(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int newHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, newHeightSpec);
    }

}
