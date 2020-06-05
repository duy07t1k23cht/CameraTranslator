package com.example.cameratranslator.ui.fcset;

import android.app.Application;
import android.util.Log;

import com.example.cameratranslator.R;
import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.ui.object.ObjectDetectionInteractor;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
class FCSetPresenter extends BasePresenter<FCSetContract.View> implements FCSetContract.Presenter {

    private FCSetInteractor interactor;

    @Override
    public void createInteractor(Application application) {
        interactor = new FCSetInteractor(application);
    }

    @Override
    public void getAllSet() {
        interactor.getAllFCSets(
                list -> mView.showFCSetsData(list),
                () -> mView.displayMessage(R.string.something_went_wrong));
    }

    @Override
    public void addNewFCSet(String name) {
        if (name.isEmpty()) {
            mView.displayMessage(R.string.this_field_cannot_be_empty);
            return;
        }

        interactor.addNewFCSet(
                name,
                () -> {
                    mView.displayMessage(R.string.add_new_set_successfully);
                    getAllSet();
                },
                () -> mView.displayMessage(R.string.something_went_wrong)
        );
    }
}
