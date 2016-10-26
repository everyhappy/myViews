package com.example.myview;

import com.example.myview.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class TestView extends View{

    private String testText;
    private int testColor;
    private int testSize;
    private int testBackGroudColor;
    private Rect mBound;
    private Paint mPaint;
    
    public TestView(Context context) {
        this(context, null);
    }
    
    public TestView(Context context,AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++)
        {  
            int attr = ta.getIndex(i);
            switch (attr)  
            {  
            case R.styleable.CustomTitleView_testText:  
                testText = ta.getString(attr);  
                break;  
            case R.styleable.CustomTitleView_testColor:  
                
                testColor = ta.getColor(attr, Color.GRAY);
                break;  
            case R.styleable.CustomTitleView_testSize:
                
                testSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                break;  
            case R.styleable.CustomTitleView_testBackGroudColor:
                testBackGroudColor = ta.getColor(attr, Color.BLACK);
            }  
  
        }  
        ta.recycle();
        mPaint = new Paint();
        mPaint.setTextSize(testSize);
        mBound = new Rect();
        mPaint.getTextBounds(testText, 0, testText.length(), mBound);  
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }
    
    protected void onDraw(Canvas canvas)  
    {  
        mPaint.setColor(testBackGroudColor);  
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);  
  
        mPaint.setColor(testColor);  
        canvas.drawText(testText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);  
    }
}
