package com.example.cameratranslator.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class BasePresenter<V extends BaseView> {

    protected V mView = null;

    void attachView(V view) {
        mView = view;
    }
}
