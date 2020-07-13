package com.example.cameratranslator.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.database.fcset.FCSetDao;
import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.database.flashcard.FlashCardDao;
import com.example.cameratranslator.database.setdetails.SetDetail;
import com.example.cameratranslator.database.setdetails.SetDetailDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */

@Database(entities = {FlashCard.class, FCSet.class, SetDetail.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FlashCardDao flashCardDao();

    public abstract FCSetDao fcSetDao();

    public abstract SetDetailDao setDetailDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app.db")
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
