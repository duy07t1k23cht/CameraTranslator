package com.example.cameratranslator.ui.object;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.cameratranslator.base.BaseView;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.model.Translation;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class ObjectDetectionContract {

    public final static String IMAGE_PATH = "ImagePathObject";

    interface View extends BaseView {
        void clearOldData();

        void setImage(Bitmap bitmap);

        void displayError(String error);

        void displayError(@StringRes int stringResID);

        void localizeObject(List<LocalizedObjectAnnotation> localizedObjectAnnotations);

        void setListObjectForBottomSheet(List<LocalizedObjectAnnotation> localizedObjectAnnotations);

        void collapseBottomSheet();

        void expandBottomSheet();

        void setImageIndex(int position);

        void setRecyclerViewIndex(int position);

        void setCurrentLanguage(String language);

        void setObjectData(LocalizedObjectAnnotation localizedObjectAnnotation);

        void showLayoutSpeakLoading();

        void showLayoutIsSpeaking();

        void refreshLayoutSpeak();
    }

    interface Presenter {

        void getPreference(Context context);

        void initDatas(Context context, Intent intent);

        void getCurrentLanguage();

        void setPosition(int position);

        void setImageFromFile();

        void speakText();

        void toAddFlashCard(Activity activity);
    }

    interface Interactor {
        void getObjectData(Context context, String imagePath, ListCallback<LocalizedObjectAnnotation> onPost, StringCallback onError);

        void getTranslateData(List<LocalizedObjectAnnotation> localizedObjectAnnotations, String targetLanguageCode, ListCallback<Translation> onPost);

        void getAudioData(LocalizedObjectAnnotation objectAnnotation, String targetLanguageCode, StringCallback onPost);
    }
}
