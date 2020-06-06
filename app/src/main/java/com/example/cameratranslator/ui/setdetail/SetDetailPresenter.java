package com.example.cameratranslator.ui.setdetail;

import android.app.Application;
import android.content.Intent;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.ui.fcset.FCSetInteractor;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class SetDetailPresenter extends BasePresenter<SetDetailContract.View> implements SetDetailContract.Presenter {

    private SetDetailInteractor interactor;

    private String setID;
    private List<FlashCard> flashCardList;

    private int currentPosition = 0;

    private SetDetailContract.FrontViewMode frontViewMode = SetDetailContract.FrontViewMode.MODE_IMAGE_ONLY;

    @Override
    public void createInteractor(Application application) {
        interactor = new SetDetailInteractor(application);
    }

    @Override
    public void getIntentData(Intent intent) {
        setID = intent.getStringExtra(SetDetailContract.SET_NAME);
        mView.setToolbarData(setID);
    }

    @Override
    public void initSetFlashCard() {
        interactor.getListFlashCard(
                setID,
                list -> {
                    flashCardList = list;
                    if (list == null || list.isEmpty()) {
                        mView.displayError(R.string.no_card_found);
                    } else {
                        setCurrentCard(0);
                    }
                },
                () -> mView.displayError("Fuck here")
        );
    }

    @Override
    public void nextCard() {
        if (currentPosition + 1 == flashCardList.size())
            setCurrentCard(0);
        else
            setCurrentCard(currentPosition + 1);
    }

    @Override
    public void previousCard() {
        if (currentPosition == 0)
            setCurrentCard(flashCardList.size() - 1);
        else
            setCurrentCard(currentPosition - 1);
    }

    @Override
    public void setCurrentCard(int position) {
        currentPosition = position;
        mView.showCard(flashCardList.get(currentPosition), frontViewMode);
        mView.updatePageNumber(currentPosition + 1, flashCardList.size());
    }
}