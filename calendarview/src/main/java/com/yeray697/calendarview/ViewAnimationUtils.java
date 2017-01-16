package com.yeray697.calendarview;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Class with methods that animate views
 * @author yeray697
 */
class ViewAnimationUtils {
    /**
     * Expand a view from current height to WRAP_CONTENT height
     * @param v View that will be expanded
     */
    static void expand(final View v) {
        expand(v,500);
    }

    /**
     * Collapse a view from current height to 0
     * @param v View that will be collapsed
     */
    static void collapse(final View v) {
        collapse(v,500);
    }

    /**
     * Expand a view from current height to WRAP_CONTENT height
     * @param v View that will be expanded
     * @param duration Duration of the animation
     */
    static void expand(final View v, int duration) {
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

    /**
     * Collapse a view from current height to 0
     * @param v View that will be collapsed
     * @param duration Duration of the animation
     */
    static void collapse(final View v, int duration) {
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

    /**
     * Get the max height (wrap_content) of a view
     * @param v View from we will get the max height
     * @return Return the max height
     */
    private static int getMaxHeight(View v){
        int lastHeight = v.getLayoutParams().height;
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int targtetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = lastHeight;
        return targtetHeight;
    }
}