package com.example.cameratranslator.database.flashcard;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.cameratranslator.callback.LongCallback;
import com.example.cameratranslator.database.AppDatabase;
import com.example.cameratranslator.utils.BitmapUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */
public class FlashCardRepository {

    private FlashCardDao flashCardDao;

    public FlashCardRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        flashCardDao = database.flashCardDao();
    }

    public Observable<List<FlashCard>> getAllFlashCard() {
        return Observable.fromCallable(() -> flashCardDao.getAllFlashCards());
    }

    public Observable<List<FlashCard>> getFlashCardByIDs(List<Integer> ids) {
        return Observable.fromCallable(() -> flashCardDao.getFlashCardsByID(ids));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> flashCardDao.deleteAll());
    }

    public void insert(FlashCard flashCard, LongCallback insertedIDCallback) {
        AppDatabase.databaseWriteExecutor.execute(() -> insertedIDCallback.execute(flashCardDao.insert(flashCard))
        );
    }

    public void insert(Bitmap image, String word, String language, LongCallback insertedIDCallback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
                    FlashCard flashCard = new FlashCard(
                            BitmapUtils.toByteArray(image),
                            word,
                            language
                    );
                    insertedIDCallback.execute(flashCardDao.insert(flashCard));
                }
        );
    }
}
