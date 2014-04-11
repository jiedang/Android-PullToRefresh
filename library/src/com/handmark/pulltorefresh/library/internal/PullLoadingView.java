package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by jiasheng on 14-4-4.
 */
public class PullLoadingView extends LoadingLayout {

    public PullLoadingView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.pull_clip;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        mHeaderImage.getDrawable().setLevel(Math.min(10000, (int) (5000 * scaleOfLayout)));
    }

    @Override
    protected void pullToRefreshImpl() {

    }

    @Override
    protected void refreshingImpl() {
        mHeaderProgress.setVisibility(View.VISIBLE);
        mHeaderImage.setVisibility(View.GONE);
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void resetImpl() {
        mHeaderProgress.setVisibility(View.GONE);
        mHeaderImage.setVisibility(View.VISIBLE);
        mHeaderImage.getDrawable().setLevel(0);
    }
}
