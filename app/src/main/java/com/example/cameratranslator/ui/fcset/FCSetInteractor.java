package com.example.cameratranslator.ui.fcset;

import android.app.Application;

import com.example.cameratranslator.R;
import com.example.cameratranslator.callback.ListCallback;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.callback.VoidCallback;
import com.example.cameratranslator.database.fcset.FCSet;
import com.example.cameratranslator.database.fcset.FCSetRepository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class FCSetInteractor implements FCSetContract.Interactor {

    private FCSetRepository fcSetRepository;

    public FCSetInteractor(Application application) {
        fcSetRepository = new FCSetRepository(application);
    }

    @Override
    public void getAllFCSets(ListCallback<FCSet> fcSetListCallback, VoidCallback onError) {
        fcSetRepository.getAllFlashCardSets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fcSetListCallback::execute, throwable -> onError.execute())
                .isDisposed();
    }

    @Override
    public void addNewFCSet(String name, VoidCallback onSuccess, VoidCallback onError) {
        try {
            fcSetRepository.insert(new FCSet(name));
        } catch (Exception ex) {
            onError.execute();
            return;
        }

        onSuccess.execute();
    }
}
