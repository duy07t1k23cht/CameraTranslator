package com.example.cameratranslator.ui.setdetail;

import androidx.annotation.StringRes;

import com.example.cameratranslator.base.BaseView;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class SetDetailContract {
    interface View extends BaseView {
        void displayError(String message);

        void displayError(@StringRes int stringResID);
    }

    interface Presenter {

    }

    interface Interactor {

    }
}
