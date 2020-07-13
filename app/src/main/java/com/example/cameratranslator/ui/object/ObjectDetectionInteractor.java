package com.example.cameratranslator.ui.object;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.cameratranslator.callback.IntCallback;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.LongCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.database.fcset.FCSetRepository;
import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.database.flashcard.FlashCardRepository;
import com.example.cameratranslator.database.setdetails.SetDetailRepository;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.model.Translation;
import com.example.cameratranslator.utils.BitmapUtils;
import com.example.cameratranslator.utils.api.TextToSpeechAPIHelper;
import com.example.cameratranslator.utils.api.VisionAPIHelper;
import com.example.cameratranslator.utils.api.TranslationAPIHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class ObjectDetectionInteractor implements ObjectDetectionContract.Interactor {

    private FCSetRepository fcSetRepository;
    private FlashCardRepository flashCardRepository;
    private SetDetailRepository setDetailRepository;

    public ObjectDetectionInteractor(Application application) {
        fcSetRepository = new FCSetRepository(application);
        flashCardRepository = new FlashCardRepository(application);
        setDetailRepository = new SetDetailRepository(application);
    }

    @Override
    public void getObjectData(
            Context context,
            String imagePath,
            ListCallback<LocalizedObjectAnnotation> onPost,
            StringCallback onError) {
        BitmapUtils.compressImage(
                context,
                imagePath,
                bitmap -> VisionAPIHelper
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
        TranslationAPIHelper.translate(localizedObjectAnnotations, targetLanguageCode)
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
        TextToSpeechAPIHelper.getAudio(localizedObjectAnnotation, targetLanguageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onPost::execute)
                .isDisposed();
    }

    @Override
    public void getAllFCSets(ListCallback<FCSet> fcSetListCallback, VoidCallback onError) {
        fcSetRepository.getAllFlashCardSets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    Log.d("__XX__", "List: " + list.size());
                    fcSetListCallback.execute(list);
                })
                .isDisposed();
    }

    @Override
    public void insertNewFlashCard(Bitmap image, String word, String language, IntCallback onSuccess, VoidCallback onError) {
        try {
//            FlashCard flashCard = new FlashCard(
//                    BitmapUtils.toByteArray(image),
//                    word,
//                    language
//            );
            flashCardRepository.insert(image, word, language, id -> onSuccess.execute((int) id));
        } catch (Exception e) {
            e.printStackTrace();
            onError.execute();
        }
    }

    @Override
    public void addFlashCardToExistSet(int flashCardID, String setID, VoidCallback onSuccess, VoidCallback onError) {
        try {
            setDetailRepository.insert(flashCardID, setID);
        } catch (Exception e) {
            e.printStackTrace();
            onError.execute();
        }

//        onSuccess.execute();
    }
}
