package com.example.cameratranslator.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Duy M. Nguyen on 5/26/2020.
 */
public class Pref {

    public class Key {
        private static final String LANGUAGE = "Language";
    }

    private static final String APP_PREF_NAME = "CameraTranslatorPref";

    private SharedPreferences sharedPreferences;

    public Pref(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void putLanguage(String language) {
        sharedPreferences
                .edit()
                .putString(Key.LANGUAGE, language)
                .apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(Key.LANGUAGE, LanguageUtils.languages[0]);
    }
}
