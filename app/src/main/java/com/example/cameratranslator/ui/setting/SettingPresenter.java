package com.example.cameratranslator.ui.setting;

import android.content.Context;

import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.utils.Preference;

import static com.example.cameratranslator.utils.LanguageUtils.languages;

/**
 * Created by Duy M. Nguyen on 5/26/2020.
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    Preference pref;

    @Override
    public void getPreference(Context context) {
        pref = new Preference(context);
    }

    @Override
    public void setApplicationLanguage(int position) {
        String language = languages[position];
        pref.putLanguage(language);
    }
}
