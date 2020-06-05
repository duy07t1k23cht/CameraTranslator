package com.example.cameratranslator.database.fcset;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */
@Entity(tableName = "fc_set")
public class FCSet {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public FCSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
