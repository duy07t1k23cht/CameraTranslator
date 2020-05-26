package com.example.cameratranslator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duy M. Nguyen on 5/16/2020.
 */
public class LocalizedObjectAnnotation {

    @SerializedName("mid")
    @Expose
    private String mid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("score")
    @Expose
    private Float score;

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    private String translation;

    private String audioContent;

    private String currentLanguageCode;

    public String getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public Float getScore() {
        return score;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public String getTranslation() {
        if (translation == null || translation.isEmpty())
            return name.trim();
        else
            return translation.trim();
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getAudioContent() {
        return audioContent;
    }

    public void setAudioContent(String audioContent) {
        this.audioContent = audioContent;
    }

    public String getCurrentLanguageCode() {
        if (currentLanguageCode == null) {
            return "";
        }
        return currentLanguageCode;
    }

    public void setCurrentLanguageCode(String currentLanguageCode) {
        this.currentLanguageCode = currentLanguageCode;
    }
}