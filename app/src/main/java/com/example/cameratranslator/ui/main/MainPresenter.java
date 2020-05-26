package com.example.cameratranslator.ui.main;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.navigation.Navigation;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private enum DetectMode {
        MODE_TEXT, MODE_OBJECT;
    }

    private DetectMode detectMode = DetectMode.MODE_OBJECT;

    @Override
    public void changeToTextMode() {
        detectMode = DetectMode.MODE_TEXT;
        mView.highlightTextTab();
    }

    @Override
    public void changeToObjectMode() {
        detectMode = DetectMode.MODE_OBJECT;
        mView.highlightObjectTab();
    }

    @Override
    public void toDetectActivity(Activity activity, String imagePath) {
        if (detectMode == DetectMode.MODE_TEXT) {
            Navigation.toTextDetectionActivity(activity, imagePath);
        } else {
            Navigation.toObjectDetectionActivity(activity, imagePath);
        }
    }
}
