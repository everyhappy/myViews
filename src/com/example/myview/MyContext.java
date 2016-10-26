package com.example.myview;

import android.app.Application;
import android.content.Context;

public class MyContext extends Application{
    private static Context mContext;
    
    public void onCreate() {
        mContext = getApplicationContext();
    }
    
    public static Context getContext(){  
        return mContext;  
    }  
}
