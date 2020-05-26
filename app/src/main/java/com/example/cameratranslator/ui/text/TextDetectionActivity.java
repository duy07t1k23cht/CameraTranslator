package com.example.cameratranslator.ui.text;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;

import static com.example.cameratranslator.utils.ViewUtils.*;

public class TextDetectionActivity extends BaseActivity<TextDetectionPresenter> implements TextDetectionContract.View {

    private ImageView imageView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_text_detection;
    }

    @Override
    protected TextDetectionPresenter initPresenter() {
        return new TextDetectionPresenter();
    }

    @Override
    protected void onCreate() {
        toast(this, "Text detection");

        mPresenter.initDatas(getApplicationContext(), getIntent());

        initViewComponents();

        mPresenter.setImageFromFile();
    }

    private void initViewComponents() {
        imageView = findViewById(R.id.image_view);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void displayError(String message) {
        toast(this, message);
    }
}
