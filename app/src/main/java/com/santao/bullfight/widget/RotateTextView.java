package com.santao.bullfight.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class RotateTextView extends TextView {

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(45, getMeasuredWidth()/2, getMeasuredHeight()/2);
//        canvas.translate(30,-10);
        super.onDraw(canvas);
    }

}