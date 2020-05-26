package com.example.cameratranslator.ui.pickimage;

import android.app.Application;

import com.example.cameratranslator.base.BaseView;

import java.util.ArrayList;

/**
 * Created by Duy M. Nguyen on 5/24/2020.
 */
public class PickImageContract {
    interface View extends BaseView {
        void setImageData(ArrayList<String> imagePaths);
    }

    interface Presenter {
        void getAllImage(Application application);
    }
}
