package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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
public class NewYearLoadingLayout extends LoadingLayout{
    private int frontViewHeiht = 0;
    private int defaultHeiht = 0;
    private int mHeaderImageHeight = 0;
    private View frontView;
    private float moveDistance = 0;
    private ValueAnimator valueAnimator;
    public NewYearLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        frontView = findViewById(R.id.new_year_bag_front);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if(frontViewHeiht == 0){
                    frontViewHeiht =  frontView.getHeight();
                }
                if(defaultHeiht == 0){
                    defaultHeiht = getHeight();
                }
                if(mHeaderImageHeight == 0){
                    mHeaderImageHeight = mHeaderImage.getHeight();
                }
                ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight/4*3);
            }
        });



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
        if(scaleOfLayout > 1){
            setHeight((int)(scaleOfLayout * defaultHeiht));
            moveDistance = (scaleOfLayout - 1) * defaultHeiht;
        }

        if(scaleOfLayout > 0.75f){
            if(valueAnimator == null){
                valueAnimator =  ValueAnimator.ofFloat(mHeaderImageHeight/4*3,mHeaderImageHeight/5).setDuration(250);
                valueAnimator.start();
            }else{
                float currentValue = (Float)valueAnimator.getAnimatedValue();
                ViewHelper.setTranslationY(mHeaderImage, currentValue);
            }
        }

    }

    @Override
    protected void pullToRefreshImpl() {
    }

    @Override
    protected void refreshingImpl() {
        ViewHelper.setTranslationY(newYearbg, moveDistance);
        ((AnimationDrawable) mHeaderImage.getDrawable()).start();
    }

    @Override
    protected void releaseToRefreshImpl() {
    }

    @Override
    protected void resetImpl() {
        ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        if(mHeaderImageHeight > 0){
            ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight/4*3);
        }
        ViewHelper.setTranslationY(newYearbg,0);
        if(moveDistance > 0){
            setHeight(defaultHeiht);
        }
        valueAnimator = null;
    }
}
