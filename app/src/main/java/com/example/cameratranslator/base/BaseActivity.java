package com.example.cameratranslator.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cameratranslator.customview.LoadingView;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected P mPresenter;
    private LoadingView loadingView;

    @LayoutRes
    protected abstract int getLayoutID();

    protected abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        mPresenter = initPresenter();
        mPresenter.attachView(this);

        loadingView = new LoadingView(this);

        onCreate();
    }

    protected abstract void onCreate();

    @Override
    public void showLoading() {
        if (loadingView != null)
            loadingView.show();
    }

    @Override
    public void dismissLoading() {
        if (loadingView != null)
            loadingView.cancel();
    }
}
