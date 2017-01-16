package com.yeray697.calendarview;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import java.lang.reflect.Method;

/**
 * Created by yeray697 on 14/01/17.
 */
public class ViewAnimationUtils {
    public static void expand(final View v) {
        expand(v,500);
    }
    public static void collapse(final View v) {
        collapse(v,500);
    }
    public static void expand(final View v, int duration) {
        if (v != null) {
            Animation a;
            a = v.getAnimation();
            if (a != null) {
                a.cancel();
                v.clearAnimation();
            }
            final int targetHeight = getMaxHeight(v);
            final int initialHeight = (v.getLayoutParams().height<0)?0:v.getLayoutParams().height;
            v.getLayoutParams().height = initialHeight;
            v.setVisibility(View.VISIBLE);
            a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = (int) (((targetHeight - initialHeight) * interpolatedTime)) + initialHeight;
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            a.setDuration(duration);
            v.startAnimation(a);
        }
    }

    public static void collapse(final View v, int duration) {
        if (v != null) {
            Animation a;
            a = v.getAnimation();
            if (a != null) {
                a.cancel();
                v.clearAnimation();
            }
            final int initialHeight = (v.getHeight() == 0) ? getMaxHeight(v) : v.getHeight();

            a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            a.setDuration(duration);
            v.startAnimation(a);
        }
    }

    private static int getMaxHeight(View v){
        int lastHeight = v.getLayoutParams().height;
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int targtetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = lastHeight;
        return targtetHeight;
    }
}