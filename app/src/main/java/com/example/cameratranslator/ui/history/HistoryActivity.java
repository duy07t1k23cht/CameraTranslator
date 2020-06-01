package com.example.cameratranslator.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;

public class HistoryActivity extends BaseActivity<HistoryPresenter> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_history;
    }

    @Override
    protected HistoryPresenter initPresenter() {
        return new HistoryPresenter();
    }

    @Override
    protected void onCreate() {
        initViewComponents();
    }

    private void initViewComponents() {

    }
}