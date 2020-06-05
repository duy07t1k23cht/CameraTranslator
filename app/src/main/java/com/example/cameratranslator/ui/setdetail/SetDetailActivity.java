package com.example.cameratranslator.ui.setdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;

import static com.example.cameratranslator.utils.ViewUtils.toast;

public class SetDetailActivity extends BaseActivity<SetDetailPresenter> implements SetDetailContract.View {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_set_detail;
    }

    @Override
    protected SetDetailPresenter initPresenter() {
        return new SetDetailPresenter();
    }

    @Override
    protected void onCreate() {

    }

    @Override
    public void displayError(String message) {
        toast(this, message);
    }

    @Override
    public void displayError(int stringResID) {
        toast(this, stringResID);
    }
}