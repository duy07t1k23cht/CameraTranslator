package com.example.cameratranslator.navigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.cameratranslator.ui.object.ObjectDetectionActivity;
import com.example.cameratranslator.ui.object.ObjectDetectionContract;
import com.example.cameratranslator.ui.pickimage.PickImageActivity;
import com.example.cameratranslator.ui.setting.SettingActivity;
import com.example.cameratranslator.ui.text.TextDetectionActivity;
import com.example.cameratranslator.ui.text.TextDetectionContract;

import java.io.ByteArrayOutputStream;


/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class Navigation {

    public static void toTextDetectionActivity(Activity activity, String imagePath) {
        Intent intent = new Intent(activity, TextDetectionActivity.class);

        intent.putExtra(TextDetectionContract.IMAGE_PATH, imagePath);

        activity.startActivity(intent);
    }

    public static void toObjectDetectionActivity(Activity activity, String imagePath) {
        Intent intent = new Intent(activity, ObjectDetectionActivity.class);

        intent.putExtra(ObjectDetectionContract.IMAGE_PATH, imagePath);

        activity.startActivity(intent);
    }

    public static void toPickImageActivity(Activity activity) {
        Intent intent = new Intent(activity, PickImageActivity.class);
        activity.startActivity(intent);
    }

    public static void toSettingActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }
}
