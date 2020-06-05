package com.example.cameratranslator.database.setdetails;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;

import io.reactivex.annotations.NonNull;

/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */
@Entity(tableName = "set_details", primaryKeys = {"setID", "flashID"})
public class SetDetail {

    @NonNull
    @ColumnInfo(name = "setID")
    private String setID;

    @NonNull
    @ColumnInfo(name = "flashID")
    private int flashID;

    public SetDetail(String setID, int flashID) {
        this.setID = setID;
        this.flashID = flashID;
    }

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public int getFlashID() {
        return flashID;
    }

    public void setFlashID(int flashID) {
        this.flashID = flashID;
    }
}
