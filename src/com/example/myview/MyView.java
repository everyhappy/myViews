package com.example.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View{

    private Paint mPaint;
    private float x, startY, endY, firstY, nextStartY, secondY;
    private String[] text = {"第一行显示", "第二个显示"};
    private float textWidth, textHeight;
    private float speech = 0;
    private static final int CHANGE_SPEECH = 0x01;
    private Boolean isScrooll =false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE_SPEECH:
                    speech = 1f;
                    break;
            }
        }
    };
    public MyView(Context context,AttributeSet attrs) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    protected void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(80);
        Rect rect = new Rect();
        mPaint.getTextBounds(text[0], 0, text[0].length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
        x = getLeft() + getPaddingLeft();
        startY = getTop() + textHeight + getPaddingTop() - 7;// 文字基线 需要调整
        Log.d("hk", "textHeight startY  = "+textHeight+" "+startY);
        endY = startY + textHeight + getPaddingBottom();
        Log.d("hk", "endY  getPaddingBottom() "+endY+" "+getPaddingBottom());
        nextStartY = getTop() - 7;
        firstY = startY;
        secondY = nextStartY;
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    
    public boolean onTouchEvent(MotionEvent event) {
        
        float x;
        float y;
        float lastx;
        float lasty;
        x = event.getRawX();
        y = event.getRawY();
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:

            Log.d("HK", "ACTION_DOWN"+x+" "+y);
            break;
        case MotionEvent.ACTION_MOVE:
            int lefttopx = (int) (x-textWidth/2);
            int lefttopy = (int) (y-textHeight/2-180);
            int rightbottomx = (int) (lefttopx+textWidth);
            int rightbottomy = (int) (lefttopy+textHeight);
            //if(Math.abs(rightbottomx-lefttopx)>50&&Math.abs(rightbottomy-lefttopy)>20)
            layout(lefttopx, lefttopy, rightbottomx, rightbottomy);
            break;
        case MotionEvent.ACTION_UP:
            Log.d("HK", "ACTION_UP");
            break;
        }
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("HK", "onDraw");
        //启动滚动
        if (!isScrooll) {
            mHandler.sendEmptyMessageDelayed(CHANGE_SPEECH, 2000);
            isScrooll = true;
        }

        canvas.drawText(text[0], x, startY, mPaint);
        canvas.drawText(text[1], x, nextStartY, mPaint);
        startY += speech;
        nextStartY += speech;

        //超出View的控件时
        if (startY > endY || nextStartY > endY) {
            if (startY > endY) {
                //第一次滚动过后交换值
                startY = secondY;
                nextStartY = firstY;
            } else if (nextStartY > endY) {
                //第二次滚动过后交换值
                startY = firstY;
                nextStartY = secondY;
            }
            speech = 0;
            isScrooll = false;
        }
        invalidate();
    }
}
