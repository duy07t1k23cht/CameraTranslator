package com.example.cameratranslator.database.fcset;

import android.app.Application;

import com.example.cameratranslator.database.AppDatabase;
import com.example.cameratranslator.database.flashcard.FlashCard;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class FCSetRepository {

    private FCSetDao fcSetDao;

    public FCSetRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        fcSetDao = database.fcSetDao();
    }

    public Observable<List<FCSet>> getAllFlashCardSets() {
        return Observable.fromCallable(() -> fcSetDao.getAllFlashCardSets());
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> fcSetDao.deleteAll());
    }

    public void insert(FCSet fcSet) {
        fcSetDao.insert(fcSet);
//        AppDatabase.databaseWriteExecutor.execute(() -> fcSetDao.insert(fcSet));
    }
}
