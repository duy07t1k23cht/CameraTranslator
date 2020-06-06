package com.example.cameratranslator.ui.object;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.callback.IntCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.navigation.Navigation;
import com.example.cameratranslator.utils.BitmapUtils;
import com.example.cameratranslator.utils.LanguageUtils;
import com.example.cameratranslator.utils.Preference;

import java.io.IOException;
import java.util.List;

import static com.example.cameratranslator.ui.object.ObjectDetectionContract.IMAGE_PATH;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class ObjectDetectionPresenter extends BasePresenter<ObjectDetectionContract.View> implements ObjectDetectionContract.Presenter {

    private ObjectDetectionInteractor interactor;

    private Preference pref;

    private String imagePath = null;
    private Bitmap bitmap;
    private Context context;

    private List<LocalizedObjectAnnotation> localizedObjectAnnotations;
    private int currentPosition = -1;

    @Override
    public void createInteractor(Application application) {
        interactor = new ObjectDetectionInteractor(application);
    }

    @Override
    public void getPreference(Context context) {
        pref = new Preference(context);
    }

    @Override
    public void getCurrentLanguage() {
        mView.setCurrentLanguage(pref.getLanguage());
    }

    @Override
    public void initDatas(Context context, Intent intent) {
        this.context = context;
        imagePath = intent.getStringExtra(IMAGE_PATH);
    }

    @Override
    public void setImageFromFile() {
        mView.clearOldData();
        try {
            bitmap = BitmapUtils.modifyOrientation(imagePath);
            mView.setImage(bitmap);
            mView.showLoading();
        } catch (IOException e) {
            e.printStackTrace();
            mView.displayError(e.getMessage());
            mView.dismissLoading();
            return;
        }

        interactor.getObjectData(
                context,
                imagePath,
                listLabelAnnotations -> {
                    localizedObjectAnnotations = listLabelAnnotations;
                    interactor.getTranslateData(
                            localizedObjectAnnotations,
                            LanguageUtils.languageCode.get(pref.getLanguage()),
                            translations -> {
                                for (int index = 0; index < translations.size(); index++) {
                                    String translateText = translations.get(index).getTranslatedText();
                                    localizedObjectAnnotations.get(index).setTranslation(translateText);
                                }
                                mView.localizeObject(localizedObjectAnnotations);
                                mView.setListObjectForBottomSheet(localizedObjectAnnotations);
                                mView.dismissLoading();
                            });
                },
                error -> {
                    mView.displayError("Error: " + error);
                    mView.dismissLoading();
                }
        );

    }

    @Override
    public void setPosition(int position) {
        currentPosition = position;

        mView.setImageIndex(currentPosition);
        mView.setRecyclerViewIndex(currentPosition);
        mView.setObjectData(localizedObjectAnnotations.get(currentPosition));
    }

    @Override
    public void speakText() {
        LocalizedObjectAnnotation objectAnnotation = localizedObjectAnnotations.get(currentPosition);
        MediaPlayer mediaPlayer = new MediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        } else {
            mediaPlayer.reset();
            mView.showLayoutSpeakLoading();
            interactor.getAudioData(
                    objectAnnotation,
                    LanguageUtils.languageCode.get(pref.getLanguage()),
                    string -> {
                        objectAnnotation.setAudioContent(string);
                        objectAnnotation.setCurrentLanguageCode(LanguageUtils.languageCode.get(pref.getLanguage()));

                        String audioUrl = "data:audio/mp3;base64," + string;

                        try {
                            mediaPlayer.setDataSource(audioUrl);
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnPreparedListener(mp -> {
                                mView.showLayoutIsSpeaking();
                                mp.start();
                            });
                            mediaPlayer.setOnCompletionListener(mp -> {
                                mView.refreshLayoutSpeak();
                                mp.stop();
                                mp.release();
                            });
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        } catch (IOException e) {
                            mView.refreshLayoutSpeak();
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public void getAllSet() {
        interactor.getAllFCSets(
                list -> mView.showDialogAllSet(list),
                () -> mView.displayError(R.string.something_went_wrong));
    }

    @Override
    public void addToExistSet(String setID) {
        if (bitmap == null) {
            mView.displayError(R.string.something_went_wrong);
            return;
        }

        byte[] bytes = BitmapUtils.toByteArray(bitmap);
        String word = localizedObjectAnnotations.get(currentPosition).getName();
        String language = LanguageUtils.languageCode.get(pref.getLanguage());

        interactor.insertNewFlashCard(bitmap, word, language,
                new IntCallback() {
                    @Override
                    public void execute(int fcID) {
                        interactor.addFlashCardToExistSet(
                                fcID,
                                setID,
                                () -> mView.displayError(R.string.added_flashcard),
                                () -> mView.displayError(R.string.something_went_wrong));
                    }
                }, new VoidCallback() {
                    @Override
                    public void execute() {

                    }
                });
    }

    @Override
    public void toAddFlashCard(Activity activity) {
        if (imagePath == null || imagePath.isEmpty() || currentPosition < 0 || currentPosition >= localizedObjectAnnotations.size()) {
            mView.displayError(R.string.something_went_wrong);
            return;
        }

        Navigation.toAddFlashCardActivity(activity, imagePath, localizedObjectAnnotations.get(currentPosition), LanguageUtils.languageCode.get(pref.getLanguage()));
    }
}
