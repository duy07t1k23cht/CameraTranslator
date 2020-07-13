package com.example.cameratranslator.ui.setdetail;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.StringRes;

import com.example.cameratranslator.base.BaseView;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.flashcard.FlashCard;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class SetDetailContract {

    public final static String SET_NAME = "SetName";

    public static enum FrontViewMode {
        MODE_WORD_ONLY,
        MODE_IMAGE_ONLY
    }

    interface View extends BaseView {
        void displayError(String message);

        void displayError(@StringRes int stringResID);

        void showNoCardText();

        void showErrorText();

        void setToolbarData(String setName);

        void showCard(FlashCard flashCard, FrontViewMode frontViewMode);

        void updatePageNumber(int currentPage, int totalPage);

        void showLayoutSpeakLoading();

        void showLayoutIsSpeaking();

        void refreshLayoutSpeak();
    }

    interface Presenter {

        void createInteractor(Application application);

        void getIntentData(Intent intent);

        void initSetFlashCard();

        void speakWord();

        void nextCard();

        void previousCard();

        void setCurrentCard(int position);

    }

    interface Interactor {
        void getListFlashCard(String setID, ListCallback<FlashCard> flashCardListCallback, VoidCallback onError);

        void getAudioData(FlashCard flashCard, StringCallback onPost);
    }
}
