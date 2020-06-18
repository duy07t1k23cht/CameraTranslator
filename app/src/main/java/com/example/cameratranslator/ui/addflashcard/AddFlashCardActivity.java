package com.example.cameratranslator.ui.addflashcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;

import static com.example.cameratranslator.utils.ViewUtils.dismiss;

public class AddFlashCardActivity extends BaseActivity<AddFlashCardPresenter> implements AddFlashCardContract.View {

    private Toolbar toolbar;
    private TextView tvWord, btnAddToSet, btnAdd, btnCancel;
    private ImageView image;

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

        // Vanish some unnecessary views
        dismiss(findViewById(R.id.layout_speak_front));
        dismiss(findViewById(R.id.btn_is_learned_front));

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tvWord = findViewById(R.id.tv_word_front);
        image = findViewById(R.id.iv_fc_image_front);

        btnAddToSet = findViewById(R.id.btn_add_to_set);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
    }
}