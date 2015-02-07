package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by dangjie1 on 15/2/2.
 */
public class NewYearLoadingLayout extends LoadingLayout {
    private int mHeaderImageHeight = 0;
    private ValueAnimator valueAnimator;

    public NewYearLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mHeaderImageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, context.getResources().getDisplayMetrics());
        ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight / 3 * 2);

    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.animation_new_year;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }


    @Override
    protected void onPullImpl(float scaleOfLayout) {
        if (scaleOfLayout > 0.9f) {
            if (valueAnimator == null) {
                valueAnimator = ValueAnimator.ofFloat(mHeaderImageHeight / 4 * 3, mHeaderImageHeight / 5).setDuration(250);
                valueAnimator.start();
            } else {
                float currentValue = (Float) valueAnimator.getAnimatedValue();
                if (ViewHelper.getTranslationY(mHeaderImage) != currentValue) {
                    ViewHelper.setTranslationY(mHeaderImage, currentValue);
                }
            }
        }

    }

    @Override
    protected void pullToRefreshImpl() {
        setVisibility(View.VISIBLE);
    }

    @Override
    protected void refreshingImpl() {
        showNewYearAnimator();
    }

    @Override
    protected void releaseToRefreshImpl() {
        showNewYearAnimator();
    }

    private void showNewYearAnimator() {
        ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight / 5);
        ((AnimationDrawable) mHeaderImage.getDrawable()).start();
    }

    @Override
    protected void resetImpl() {
        setVisibility(View.INVISIBLE);
        ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        if (mHeaderImageHeight > 0) {
            ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight / 3 * 2);
        }
        valueAnimator = null;
    }
}
