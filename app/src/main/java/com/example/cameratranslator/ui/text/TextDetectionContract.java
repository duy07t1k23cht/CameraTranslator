package com.example.cameratranslator.ui.text;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.cameratranslator.base.BaseView;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.model.TextAnnotations;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class TextDetectionContract {

    public final static String IMAGE_PATH = "ImagePath";

    interface View extends BaseView {
        void setImage(Bitmap bitmap);

        void displayError(String message);
    }

    interface Presenter {
        void initDatas(Context context, Intent intent);

        void setImageFromFile();
    }

    interface Interactor {
        void getTextData(Context context, String imagePath, ListCallback<TextAnnotations> onPost, StringCallback onError);
    }
}
