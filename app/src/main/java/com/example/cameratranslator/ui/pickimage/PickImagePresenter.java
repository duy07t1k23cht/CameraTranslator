package com.example.cameratranslator.ui.pickimage;

import android.app.Application;
import android.util.Log;

import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.utils.BitmapUtils;

import java.util.ArrayList;


/**
 * Created by Duy M. Nguyen on 5/24/2020.
 */
public class PickImagePresenter extends BasePresenter<PickImageContract.View> implements PickImageContract.Presenter {

    @Override
    public void getAllImage(Application application) {
        ArrayList<String> imagePaths = BitmapUtils.getImagePathFromStorage(application);
        mView.setImageData(imagePaths);
    }
}
