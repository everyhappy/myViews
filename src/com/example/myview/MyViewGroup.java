package com.example.myview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyViewGroup extends ViewGroup{



    public MyViewGroup(Context context,AttributeSet attrs) {
        super(context);
        // TODO Auto-generated constructor stub
        addView(CreatView());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
//        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int childCount = getChildCount();
        Log.d("HK", "childCount = "+childCount);
        if(childCount == 0) {
            setMeasuredDimension(0, 0);
            Log.d("HK", "setMeasuredDimension(0, 0)");
        } else {
            Log.d("HK", "setMeasuredDimension(chirldWidth, chirldHight)");
            for(int i=0;i<childCount;i++) {
                TextView tvChild = (TextView) getChildAt(i);
                measureChild(tvChild, widthMeasureSpec, heightMeasureSpec);  
                int childWidth = tvChild.getMeasuredWidth();
                int childHight = tvChild.getMeasuredHeight();
                int row = i-1/4;
                Log.d("HK", "childWitch = "+childWidth);
                setMeasuredDimension(childWidth*4, (childHight)*(row+1));
                Log.d("HK", "setMeasuredDimension w h = "+childWidth*4+" "+childHight*(row+1));
            }
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        int childCount = getChildCount();
        int left = 0;
        //int row = childCount/4;
        int mod = childCount%4;
        int row = 0;
        TextView childView;
        for(int i=0;i<childCount;i++) {
            childView = (TextView) getChildAt(i);
            if(i==childCount-1) {
                childView.setText("点击添加");
                childView.setBackgroundColor(Color.BLUE);
                childView.setTextColor(Color.BLACK);
                childView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Log.d("HK", "onLayout Click");
                        addView(CreatView());
                        //requestLayout();
                        invalidate();
                    }
                });
            } else {
                childView.setText("新加入的");
                childView.setTextColor(Color.BLACK);
                childView.setBackgroundColor(Color.GREEN);
            }
            int width = childView.getMeasuredWidth();
            Log.d("HK", "onLayout = "+width+" "+childView.getHeight());
            row = i/4;
            if((i+1)%4==1&&row!=0) {
                left = 0;
                Log.d("HK", "onLayout change line");
            }
            childView.layout(left, childView.getMeasuredHeight()*row, left+width, childView.getMeasuredHeight()*(row+1));
            Log.d("HK", "childView.layout left top right buttom= "+left+" "+0+" "+(left+width)+" "+childView.getMeasuredHeight());
            left += width;
            Log.d("HK", "onLayout row =  "+row +" "+i%4+" "+i);
        }
    }

    protected TextView CreatView() {
        TextView tv = new TextView(MyContext.getContext());
        tv.setHeight(80);
        tv.setWidth(220);
        return tv;
    }
    protected void CreatTextView(int type,TextView tv) {
        switch (type) {
        case 0:
//            TextView tv0 = new TextView(MyContext.getContext());
            tv.setText("点击添加");
            tv.setBackgroundColor(Color.BLUE);
            tv.setTextColor(Color.BLACK);
            tv.setHeight(80);
            tv.setWidth(220);
//            addView(tv0);
            break;
        case 1:
//            TextView tv1 = new TextView(MyContext.getContext());
            tv.setText("新加入的");
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.GREEN);
            tv.setHeight(80);
            tv.setWidth(220);
//            addView(tv1);
            break;
        default:
            break;
        }
    }
    protected void addView() {
        
    }
}
