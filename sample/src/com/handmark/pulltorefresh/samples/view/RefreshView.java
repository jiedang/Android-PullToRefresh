package com.handmark.pulltorefresh.samples.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.handmark.pulltorefresh.samples.R;

/**
 * Created by jiasheng on 14-4-4.
 */
public class RefreshView extends LoadingLayout {

    private final Animation mAnimation;

    public RefreshView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mAnimation = new AlphaAnimation(0, 1);
        mAnimation.setDuration(1200);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mHeaderImage.setBackgroundResource(R.drawable.iv_gray);
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.refresh_clip;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        mHeaderImage.clearAnimation();
        mHeaderImage.getDrawable().setAlpha(255);
        mHeaderImage.getDrawable().setLevel(Math.min(10000, (int) (5000 * scaleOfLayout)));
    }

    @Override
    protected void pullToRefreshImpl() {

    }

    @Override
    protected void refreshingImpl() {
        mHeaderImage.getDrawable().setLevel(10000);
        mHeaderImage.startAnimation(mAnimation);
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
        mHeaderImage.getDrawable().setLevel(0);
    }
}
