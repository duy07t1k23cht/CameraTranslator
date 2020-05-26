package com.example.cameratranslator.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.cameratranslator.R;

/**
 * Created by Duy M. Nguyen on 5/17/2020.
 */
public class LoadingView extends Dialog {

    public LoadingView(@NonNull Context context) {
        super(context, R.style.LoadingView);

        setContentView(R.layout.loading_view);
        Window window = getWindow();
        if (window != null)
            window.setBackgroundDrawable(getContext().getDrawable(R.drawable.bg_transparent));

    }
}
