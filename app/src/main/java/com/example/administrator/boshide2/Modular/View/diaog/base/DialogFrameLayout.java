package com.example.administrator.boshide2.Modular.View.diaog.base;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DialogFrameLayout extends FrameLayout {
    public DialogFrameLayout(@NonNull Context context) {
        super(context);
        commonInit(context);
    }

    interface OnTouchOutsideListener {
        void onTouchOutside();
    }

    GestureDetector gestureDetector = null;

    OnTouchOutsideListener onTouchOutsideListener;

    public void setOnTouchOutsideListener(OnTouchOutsideListener onTouchOutsideListener) {
        this.onTouchOutsideListener = onTouchOutsideListener;
    }

    private void commonInit(@NonNull Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Rect rect = new Rect();
                getHitRect(rect);
                int count = getChildCount();
                for (int i = count - 1; i > -1; i--) {
                    View child = getChildAt(i);
                    Rect outRect = new Rect();
                    child.getHitRect(outRect);
                    if (outRect.contains((int) e.getX(), (int) e.getY())) {
                        return false;
                    }
                }
                if (onTouchOutsideListener != null) {
                    onTouchOutsideListener.onTouchOutside();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}
