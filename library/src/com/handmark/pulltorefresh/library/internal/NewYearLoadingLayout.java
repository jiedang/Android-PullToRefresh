package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by dangjie1 on 15/2/2.
 */
public class NewYearLoadingLayout extends LoadingLayout{
    private int frontViewHeiht = 0;
    private int defaultHeiht = 0;
    private int mHeaderImageHeight = 0;
    private int moveMaxDistance = 0;
    private View frontView;
    public NewYearLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        frontView = findViewById(R.id.new_year_bag_front);
        mUseIntrinsicAnimation = false;
        moveMaxDistance =  (int) (88 * context.getResources().getDisplayMetrics().density);
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
        if(frontViewHeiht == 0){
            frontViewHeiht =  frontView.getHeight();
        }
        if(defaultHeiht == 0){
            defaultHeiht = getHeight();
        }
        if(mHeaderImageHeight == 0){
            mHeaderImageHeight = mHeaderImage.getHeight();
        }
        boolean isOut = - ViewHelper.getTranslationY(mHeaderImage)  > moveMaxDistance ;

        if( !isOut && scaleOfLayout * defaultHeiht > frontViewHeiht){
            float distance = scaleOfLayout * defaultHeiht - frontViewHeiht;
            ViewHelper.setTranslationY(mHeaderImage,- distance);

        }
    }

    @Override
    protected void pullToRefreshImpl() {
    }

    @Override
    protected void refreshingImpl() {
        ((AnimationDrawable) mHeaderImage.getDrawable()).start();
    }

    @Override
    protected void releaseToRefreshImpl() {
    }

    @Override
    protected void resetImpl() {
        ((AnimationDrawable) mHeaderImage.getDrawable()).stop();
        ViewHelper.setTranslationY(mHeaderImage,0);
    }
}
