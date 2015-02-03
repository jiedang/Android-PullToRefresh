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
    private static int mHeaderImageHeight = 0;
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
                //因为LIST VIEW 滚动和刷新停留采用两个不同的VIEW 只有滚动的提前添加到了界面 停留采用LIST HEAD VIEW 添加  所以导致在停留VIEW 获取不到高度
                // 1 保存高度
                // 2 在获取高度失败后  直接设置记录值的位置
                if(mHeaderImageHeight == 0 && mHeaderImage.getHeight() != 0){
                    mHeaderImageHeight = mHeaderImage.getHeight();
                }
                if(mHeaderImage.getHeight() != 0){
                    ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight/4*3);
                }else{
                    ViewHelper.setTranslationY(mHeaderImage, mHeaderImageHeight/5);
                }
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

        if(scaleOfLayout > 0.8f){
            if(valueAnimator == null){
                valueAnimator =  ValueAnimator.ofFloat(mHeaderImageHeight/4*3,mHeaderImageHeight/5).setDuration(250);
                valueAnimator.start();
            }else{
                float currentValue = (Float)valueAnimator.getAnimatedValue();
                if(ViewHelper.getTranslationY(mHeaderImage) != currentValue){
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
