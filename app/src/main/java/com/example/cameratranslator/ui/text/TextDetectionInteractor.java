package com.example.cameratranslator.ui.text;

import android.content.Context;

import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.model.TextAnnotations;
import com.example.cameratranslator.utils.BitmapUtils;
import com.example.cameratranslator.utils.ImageDetectionHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class TextDetectionInteractor implements TextDetectionContract.Interactor {

    @Override
    public void getTextData(
            Context context,
            String imagePath,
            ListCallback<TextAnnotations> onPost,
            StringCallback onError) {
        BitmapUtils.compressImage(
                context,
                imagePath,
                bitmap -> ImageDetectionHelper
                        .getTextData(bitmap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                onPost::execute,
                                throwable -> onError.execute(throwable.getMessage()))
                        .isDisposed(),
                onError);
    }
}
