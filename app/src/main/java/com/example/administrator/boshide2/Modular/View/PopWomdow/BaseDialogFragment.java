package com.example.administrator.boshide2.Modular.View.PopWomdow;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Joke on 2017/3/31.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected abstract int getContentLayout();

    protected abstract void init();

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract int getGrivaty();

    protected abstract int getWindowAnimations();

    protected abstract int getDialogBackground();

    View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View contentView = inflater.inflate(getContentLayout(), null);
//        ButterKnife.bind(this, contentView);
        init();
        return contentView;
    }

    public View findViewById(int id) {
        return contentView.findViewById(id);
    }


    private void initAttributes() {
        WindowManager.LayoutParams attr = getDialog().getWindow().getAttributes();
        attr.dimAmount = 0.6f;
        if (getHeight() != 0) {
            attr.height = getHeight();
        }
        if (getWidth() != 0) {
            attr.width = getWidth();
        }
        getDialog().getWindow().setAttributes(attr);

        if (getGrivaty() != 0) {
            getDialog().getWindow().setGravity(getGrivaty());
        }

        if (getDialogBackground() != 0) {

            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(getDialogBackground()));
            getDialog().getWindow().setBackgroundDrawable(colorDrawable);
        }

        if (getWindowAnimations() != 0) {
            getDialog().getWindow().setWindowAnimations(getWindowAnimations());
        }

    }

    @Override
    public final void onStart() {
        initAttributes();
        super.onStart();
    }
}
