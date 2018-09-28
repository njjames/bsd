package com.example.administrator.boshide2.Modular.View.diaog.base;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.AppUtils;

public abstract class BaseDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 注意，这里不再需要 getActivity().getLayoutInflater(), 因为 BaseDialogFragment 已经返回了正确的 inflater
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        if (!getDialog().getWindow().isFloating()) {
            setupDialog();
            inflater = new DialogLayoutInflater(getContext(), layoutInflater,
                    new DialogFrameLayout.OnTouchOutsideListener() {
                        @Override
                        public void onTouchOutside() {
                            if (isCancelable()) {
                                dismiss();
                            }
                        }
                    });
        }
        return inflater.inflate(getLayoutId(), container, false);
    }

    protected abstract int getLayoutId();

//    @Override
//    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
//        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        if (!getDialog().getWindow().isFloating()) {
//            setupDialog();
//            layoutInflater = new DialogLayoutInflater(getContext(), layoutInflater,
//                    new DialogFrameLayout.OnTouchOutsideListener() {
//                        @Override
//                        public void onTouchOutside() {
//                            if (isCancelable()) {
//                                dismiss();
//                            }
//                        }
//                    });
//        }
//        return layoutInflater;
//    }

    protected void setupDialog() {
        Window window = getDialog().getWindow();
        // 解决黑色状态栏的问题
        AppUtils.setStatusBarTranslucent(window, true);
        AppUtils.setStatusBarColor(window, Color.TRANSPARENT, false);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isCancelable()) {
                        dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
