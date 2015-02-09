package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by dangjie1 on 15/2/2.
 */
public class NewYearLoadingLayout extends LoadingLayout {
    private int mHeaderImageHeight = 0;
    private ValueAnimator valueAnimator;
    private boolean hasShowAnimator = false;
    private float startY,endY;
    public NewYearLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mHeaderImageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, context.getResources().getDisplayMetrics());
        startY =  mHeaderImageHeight / 3 * 2;
        endY =  mHeaderImageHeight /6;
        ViewHelper.setTranslationY(mHeaderImage, startY);
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
                valueAnimator = ValueAnimator.ofFloat(startY, endY).setDuration(250);
                valueAnimator.start();
                showNewYearAnimator();
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

    }

    @Override
    protected void refreshingImpl() {
        ViewHelper.setTranslationY(mHeaderImage, endY);
        showNewYearAnimator();
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    private void showNewYearAnimator() {
        ((AnimationDrawable) mHeaderImage.getDrawable()).start();
        hasShowAnimator = true;
    }

    @Override
    protected void resetImpl() {
        //已经显示过动画的 在移除前 设置为不显示 防止界面出现闪烁
        if(hasShowAnimator){
            hasShowAnimator = false;
            setVisibility(View.INVISIBLE);
        }

        ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        ViewHelper.setTranslationY(mHeaderImage, startY);
        valueAnimator = null;
    }
}
