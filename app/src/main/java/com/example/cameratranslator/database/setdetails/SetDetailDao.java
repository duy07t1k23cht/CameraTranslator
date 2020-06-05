package com.example.cameratranslator.database.setdetails;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
@Dao
public interface SetDetailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(SetDetail setDetails);

    @Query("SELECT * FROM set_details WHERE setID = :setID")
    public List<SetDetail> getAllFlashCard(String setID);
}
