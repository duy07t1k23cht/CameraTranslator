package com.example.cameratranslator.ui.text;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.model.TextAnnotations;
import com.example.cameratranslator.utils.BitmapUtils;

import java.io.IOException;

import static com.example.cameratranslator.ui.text.TextDetectionContract.IMAGE_PATH;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class TextDetectionPresenter extends BasePresenter<TextDetectionContract.View> implements TextDetectionContract.Presenter {

    private TextDetectionInteractor interactor = new TextDetectionInteractor();

    private String imagePath = null;
    private Context context;

    @Override
    public void initDatas(Context context, Intent intent) {
        this.context = context;
        imagePath = intent.getStringExtra(IMAGE_PATH);
    }

    @Override
    public void setImageFromFile() {
        try {
            mView.setImage(BitmapUtils.modifyOrientation(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            mView.displayError(e.getMessage());
            return;
        }

        interactor.getTextData(
                context,
                imagePath,
                listTextAnnotations -> {
                    StringBuilder message = new StringBuilder();
                    for (TextAnnotations textAnnotations : listTextAnnotations) {
                        message.append(textAnnotations.getDescription()).append("\n");
                    }
                    mView.displayError(message.toString());
                },
                error -> {
                    Log.d("__RESSULT", "Error: " + error);
                    mView.displayError("Error: " + error);
                }
        );
    }
}
