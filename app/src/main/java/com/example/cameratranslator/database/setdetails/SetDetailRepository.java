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

    public Observable<List<SetDetail>> getAllFlashCard(String setID) {
        return Observable.fromCallable(() -> setDetailDao.getAllFlashCard(setID));
    }

    public void insert(int flashCardID, String fcSetID) {
        SetDetail setDetail = new SetDetail(fcSetID, flashCardID);
        AppDatabase.databaseWriteExecutor.execute(() -> setDetailDao.insert(setDetail));
    }

    public void insert(SetDetail setDetail) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                setDetailDao.insert(setDetail);
            }
        });
    }
}
