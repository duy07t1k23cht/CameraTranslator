package com.example.cameratranslator.ui.pickimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BaseActivity;

import java.util.ArrayList;

public class PickImageActivity extends BaseActivity<PickImagePresenter> implements PickImageContract.View {

    private RecyclerView rvImages;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_pick_image;
    }

    @Override
    protected PickImagePresenter initPresenter() {
        return new PickImagePresenter();
    }

    @Override
    protected void onCreate() {

        initViewComponents();

        mPresenter.getAllImage(getApplication());
    }

    private void initViewComponents() {
        // Setup Toolbars
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup RecyclerView
        rvImages = findViewById(R.id.rv_images);
        rvImages.hasFixedSize();
    }

    @Override
    public void setImageData(ArrayList<String> imagePaths) {
        ItemImageAdapter imageAdapter = new ItemImageAdapter(this, imagePaths);
        rvImages.setAdapter(imageAdapter);
    }
}
