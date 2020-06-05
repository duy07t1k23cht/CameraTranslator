package com.example.cameratranslator.database.fcset;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cameratranslator.database.flashcard.FlashCard;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
@Dao
public interface FCSetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FCSet fcSet);

    @Query("DELETE FROM fc_set")
    void deleteAll();

    @Query("SELECT * FROM fc_set ORDER BY name ASC")
    List<FCSet> getAllFlashCardSets();
}
