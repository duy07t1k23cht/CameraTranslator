package com.example.cameratranslator.database.history;

import com.example.cameratranslator.model.BoundingPoly;

/**
 * Created by Duy M. Nguyen on 6/1/2020.
 */
class HistoryItem {

    private String imagePath;
    private String dateModified;
    private BoundingPoly boundingPoly;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }
}
