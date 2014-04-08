package com.handmark.pulltorefresh.samples.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

/**
 * Created by jiasheng on 14-4-4.
 */
public class MyView extends PullToRefreshListView {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, Mode mode) {
        super(context, mode);
    }

    public MyView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        return new RefreshView(context, mode, Orientation.VERTICAL, attrs);
    }
}
