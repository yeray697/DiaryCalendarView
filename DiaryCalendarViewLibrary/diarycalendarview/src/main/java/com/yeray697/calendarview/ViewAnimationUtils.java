/*
	Copyright (c) 2017 Yeray Ruiz Juárez, https://github.com/yeray697

	Permission is hereby granted, free of charge, to any person obtaining
	a copy of this software and associated documentation files (the
	"Software"), to deal in the Software without restriction, including
	without limitation the rights to use, copy, modify, merge, publish,
	distribute, sublicense, and/or sell copies of the Software, and to
	permit persons to whom the Software is furnished to do so, subject to
	the following conditions:

	The above copyright notice and this permission notice shall be
	included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
	LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
	OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
	WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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