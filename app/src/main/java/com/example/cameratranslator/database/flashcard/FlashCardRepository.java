package com.example.cameratranslator.database.flashcard;

import android.app.Application;

import com.example.cameratranslator.database.AppDatabase;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */
public class FlashCardRepository {

//    private final static String TABLE_NAME = "flashcard";
//
//    private final static String COL_ID = "id";
//    private final static String COL_IMAGE = "image";
//    private final static String COL_WORD = "word";
//    private final static String COL_STATUS = "status";
//    private final static String COL_AUDIO = "audio";
//
//    private AppDatabase appDatabase;
//
//    private CompositeDisposable compositeDisposable;
//
//    public FlashCardRepository(AppDatabase appDatabase) {
//        this.appDatabase = appDatabase;
//
//        compositeDisposable = new CompositeDisposable();
//    }

    private FlashCardDao flashCardDao;

    public FlashCardRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        flashCardDao = database.flashCardDao();
    }

    public Observable<List<FlashCard>> getAllFlashCard() {
        return Observable.fromCallable(() -> flashCardDao.getAllFlashCards());
    }

    public Observable<List<FlashCard>> getFlashCardByIDs(List<Integer> ids) {
        return Observable.fromCallable(() -> flashCardDao.getFlashCardsByID());
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> flashCardDao.deleteAll());
    }

    public long insert(FlashCard flashCard) {
        return flashCardDao.insert(flashCard);
//        AppDatabase.databaseWriteExecutor.execute(() -> flashCardDao.insert(flashCard));
    }

//    private void addFlashCard(FlashCard flashCard) {
//        SQLiteDatabase database = appDatabase.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(COL_IMAGE, flashCard.getImage());
//        contentValues.put(COL_WORD, flashCard.getWord());
//        contentValues.put(COL_STATUS, flashCard.isLearned() ? 1 : 0);
//        contentValues.put(COL_AUDIO, flashCard.getAudioContent());
//
//        database.insert(TABLE_NAME, null, contentValues);
//        database.close();
//    }
}
