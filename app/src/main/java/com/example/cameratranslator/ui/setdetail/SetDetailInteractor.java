package com.example.cameratranslator.ui.setdetail;

import android.app.Application;
import android.util.Log;

import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.fcset.FCSetRepository;
import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.database.flashcard.FlashCardRepository;
import com.example.cameratranslator.database.setdetails.SetDetail;
import com.example.cameratranslator.database.setdetails.SetDetailRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
class SetDetailInteractor implements SetDetailContract.Interactor {

    private SetDetailRepository setDetailRepository;
    private FlashCardRepository flashCardRepository;

    public SetDetailInteractor(Application application) {
        setDetailRepository = new SetDetailRepository(application);
        flashCardRepository = new FlashCardRepository(application);
    }

    @Override
    public void getListFlashCard(String setID, ListCallback<FlashCard> flashCardListCallback, VoidCallback onError) {
        setDetailRepository.getAllFlashCard(setID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        setDetails -> {
                            ArrayList<Integer> ids = new ArrayList<>();
                            for (SetDetail setDetail : setDetails) {
                                ids.add(setDetail.getFlashID());
                            }
                            flashCardRepository.getFlashCardByIDs(ids)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(list -> {
                                                Log.d("__XX__", "Get from " + setID + ": " + list.size());
                                                flashCardListCallback.execute(list);
                                            },
                                            throwable -> onError.execute())
                                    .isDisposed();
                        }, throwable -> onError.execute())
                .isDisposed();
    }
}
