package com.example.cameratranslator.ui.main;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.cameratranslator.base.BaseView;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class MainContract {

    public final static int PICK_IMG_REQ = 182;

    interface View extends BaseView {
        void highlightTextTab();

        void highlightObjectTab();

        void pickImage();

        void openSettingDrawer();

        void closeSettingDrawer();
    }

    interface Presenter {
        void changeToTextMode();

        void changeToObjectMode();

        void toDetectActivity(Activity activity, String imagePath);
    }

    interface Interactor {

    }
}
