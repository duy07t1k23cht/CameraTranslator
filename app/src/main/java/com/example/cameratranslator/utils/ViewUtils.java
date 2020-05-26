package com.example.cameratranslator.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class ViewUtils {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toast(Context context, @StringRes int stringResID) {
        Toast.makeText(context, context.getString(stringResID), Toast.LENGTH_LONG).show();
    }
}
