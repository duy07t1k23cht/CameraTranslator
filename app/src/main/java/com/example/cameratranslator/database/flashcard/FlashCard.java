package com.example.cameratranslator.database.flashcard;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

/**
 * Created by Duy M. Nguyen on 6/2/2020.
 */
@Entity(tableName = "flashcard")
public class FlashCard {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    @NonNull
    @ColumnInfo(name = "language")
    private String language;

    @NonNull
    @ColumnInfo(name = "learned")
    private int learned;

    @ColumnInfo(name = "audio")
    private String audioContent;

    public FlashCard(byte[] image, String word, String language, boolean learned, String audioContent) {
        this.image = image;
        this.word = word;
        this.language = language;
        this.learned = learned ? 1 : 0;
        this.audioContent = audioContent;
    }

    public FlashCard(byte[] image, String word, String language) {
        this.image = image;
        this.word = word;
        this.language = language;
        this.learned = 0;
        this.audioContent = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLearned() {
        return learned;
    }

    public void setLearned(int learned) {
        this.learned = learned;
    }

    public String getAudioContent() {
        return audioContent;
    }

    public void setAudioContent(String audioContent) {
        this.audioContent = audioContent;
    }
}
