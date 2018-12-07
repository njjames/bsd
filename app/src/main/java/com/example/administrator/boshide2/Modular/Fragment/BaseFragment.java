package com.example.administrator.boshide2.Modular.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    private Activity acticity;
    protected View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        acticity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initView() {
    }

    public void initData() {
    }

    protected abstract int getLayoutId();

    public Activity getHostActicity() {
        return acticity;
    }

    public View getView() {
        return view;
    }

    @Override
    public void onPause() {
        removeUsingCzy();
        super.onPause();
    }

    public void removeUsingCzy() {
    }
}
