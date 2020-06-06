package com.example.cameratranslator.database.flashcard;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */
@Dao
public interface FlashCardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(FlashCard flashCard);

    @Query("DELETE FROM flashcard")
    void deleteAll();

    @Query("SELECT * FROM flashcard ORDER BY word ASC")
    List<FlashCard> getAllFlashCards();

    @Query("SELECT * FROM flashcard WHERE id IN (:ids)")
    List<FlashCard> getFlashCardsByID(List<Integer> ids);
}
