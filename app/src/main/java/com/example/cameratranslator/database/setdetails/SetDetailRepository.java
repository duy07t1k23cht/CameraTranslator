package com.example.cameratranslator.database.setdetails;

import android.app.Application;

import com.example.cameratranslator.database.AppDatabase;
import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.database.flashcard.FlashCard;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class SetDetailRepository {

    private SetDetailDao setDetailDao;

    public SetDetailRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        setDetailDao = database.setDetailDao();
    }

    public Observable<List<SetDetail>> getAllFlashCard(FCSet fcSet) {
        return Observable.fromCallable(() -> setDetailDao.getAllFlashCard(fcSet.getName()));
    }

    public void insert(FlashCard flashCard, FCSet fcSet) {
        setDetailDao.insert(new SetDetail(fcSet.getName(), flashCard.getId()));
    }

    public void insert(SetDetail setDetail) {
        setDetailDao.insert(setDetail);
    }
}
