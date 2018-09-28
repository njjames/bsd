package com.example.administrator.boshide2.Modular.View;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017-6-29.
 */

public class MyEdtext extends android.support.v7.widget.AppCompatEditText{
    public MyEdtext(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyEdtext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEdtext(Context context, AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
