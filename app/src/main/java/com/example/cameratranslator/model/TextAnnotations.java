package com.example.cameratranslator.model;

import com.example.cameratranslator.model.BoundingPoly;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextAnnotations {
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }
}
