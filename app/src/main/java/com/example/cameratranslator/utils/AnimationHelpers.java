package com.example.cameratranslator.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.Nullable;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class AnimationHelpers {

    private final static int DEFAULT_DURATION = 200;

    public static void rotate(View view, float fromDegree, float toDegree) {

        RotateAnimation rotate = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(DEFAULT_DURATION);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillAfter(true);

        view.startAnimation(rotate);

    }

    public static void leftIn(View view, float fromXDelta) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, 0, 0, 0);
        translateAnimation.setDuration(DEFAULT_DURATION);
        view.startAnimation(translateAnimation);
    }
}
