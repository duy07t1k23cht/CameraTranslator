package com.example.cameratranslator.ui.setting;

import android.content.Context;

import com.example.cameratranslator.base.BaseView;

/**
 * Created by Duy M. Nguyen on 5/26/2020.
 */
public class SettingContract {
    interface View extends BaseView {
        void setListLanguage();
    }

    interface Presenter {
        void getPreference(Context context);
        void setApplicationLanguage(int position);
    }
}
