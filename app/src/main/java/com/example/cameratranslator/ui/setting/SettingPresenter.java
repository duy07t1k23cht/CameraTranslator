package com.example.cameratranslator.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cameratranslator.base.BasePresenter;
import com.example.cameratranslator.utils.Pref;

import java.util.HashMap;
import java.util.Map;

import static com.example.cameratranslator.utils.LanguageUtils.languages;

/**
 * Created by Duy M. Nguyen on 5/26/2020.
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    Pref pref;

    @Override
    public void getPreference(Context context) {
        pref = new Pref(context);
    }

    @Override
    public void setApplicationLanguage(int position) {
        String language = languages[position];
        pref.putLanguage(language);
    }
}
