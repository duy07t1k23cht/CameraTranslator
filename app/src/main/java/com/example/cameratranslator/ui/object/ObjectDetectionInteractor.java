package com.example.cameratranslator.ui.object;

import android.content.Context;

import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.model.Translation;
import com.example.cameratranslator.utils.BitmapUtils;
import com.example.cameratranslator.utils.GetAudioHelper;
import com.example.cameratranslator.utils.ImageDetectionHelper;
import com.example.cameratranslator.utils.TranslateHelper;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class ObjectDetectionInteractor implements ObjectDetectionContract.Interactor {

    @Override
    public void getObjectData(
            Context context,
            String imagePath,
            ListCallback<LocalizedObjectAnnotation> onPost,
            StringCallback onError) {
        BitmapUtils.compressImage(
                context,
                imagePath,
                bitmap -> ImageDetectionHelper
                        .getObjectData(bitmap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                onPost::execute,
                                throwable -> onError.execute(throwable.getMessage()))
                        .isDisposed(),
                onError);
    }

    @Override
    public void getTranslateData(
            List<LocalizedObjectAnnotation> localizedObjectAnnotations,
            String targetLanguageCode,
            ListCallback<Translation> onPost) {
        TranslateHelper.translate(localizedObjectAnnotations, targetLanguageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onPost::execute)
                .isDisposed();
    }

    @Override
    public void getAudioData(
            LocalizedObjectAnnotation localizedObjectAnnotation,
            String targetLanguageCode,
            StringCallback onPost) {
        GetAudioHelper.getAudio(localizedObjectAnnotation, targetLanguageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onPost::execute)
                .isDisposed();
    }
}
