package com.example.cameratranslator.ui.fcset;

import android.app.Application;

import androidx.annotation.StringRes;

import com.example.cameratranslator.base.BaseView;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.fcset.FCSet;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
class FCSetContract {
    interface View extends BaseView {
        void displayMessage(String message);

        void displayMessage(@StringRes int stringResID);

        void showFCSetsData(List<FCSet> fcSets);
    }

    interface Presenter {
        void createInteractor(Application application);

        void getAllSet();

        void addNewFCSet(String name);
    }

    interface Interactor {
        void addNewFCSet(String name, VoidCallback onSuccess, VoidCallback onError);

        void getAllFCSets(ListCallback<FCSet> fcSetListCallback, VoidCallback onError);
    }
}
