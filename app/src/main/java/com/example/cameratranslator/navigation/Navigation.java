package com.example.cameratranslator.navigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.ui.addflashcard.AddFlashCardActivity;
import com.example.cameratranslator.ui.addflashcard.AddFlashCardContract;
import com.example.cameratranslator.ui.fcset.FCSetActivity;
import com.example.cameratranslator.ui.object.ObjectDetectionActivity;
import com.example.cameratranslator.ui.object.ObjectDetectionContract;
import com.example.cameratranslator.ui.pickimage.PickImageActivity;
import com.example.cameratranslator.ui.setting.SettingActivity;
import com.example.cameratranslator.ui.text.TextDetectionActivity;
import com.example.cameratranslator.ui.text.TextDetectionContract;
import com.google.gson.Gson;

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

    public static void toAddFlashCardActivity(Activity activity, byte[] byteImage, String word, String language, String audioContent) {
        Intent intent = new Intent(activity, AddFlashCardActivity.class);

        intent.putExtra(AddFlashCardContract.IMAGE, byteImage);
        intent.putExtra(AddFlashCardContract.WORD, word);
        intent.putExtra(AddFlashCardContract.LANGUAGE, language);
        intent.putExtra(AddFlashCardContract.AUDIO, audioContent);

        activity.startActivity(intent);
    }

    public static void toAddFlashCardActivity(Activity activity, String imagePath, LocalizedObjectAnnotation localizedObjectAnnotation, String language) {
        Intent intent = new Intent(activity, AddFlashCardActivity.class);

        intent.putExtra(AddFlashCardContract.IMAGE_PATH, imagePath);
        intent.putExtra(AddFlashCardContract.JSON_DATA, new Gson().toJson(localizedObjectAnnotation));
        intent.putExtra(AddFlashCardContract.LANGUAGE, language);

        activity.startActivity(intent);
    }

    public static void toFlashCardSetActivity(Activity activity) {
        Intent intent = new Intent(activity, FCSetActivity.class);

        activity.startActivity(intent);
    }
}
