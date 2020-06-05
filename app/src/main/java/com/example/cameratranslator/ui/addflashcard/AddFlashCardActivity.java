package com.example.cameratranslator.ui.addflashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;

public class AddFlashCardActivity extends BaseActivity<AddFlashCardPresenter> implements AddFlashCardContract.View {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_flash_card;
    }

    @Override
    protected AddFlashCardPresenter initPresenter() {
        return new AddFlashCardPresenter();
    }

    @Override
    protected void onCreate() {

        initViewComponents();
    }

    private void initViewComponents() {

    }
}