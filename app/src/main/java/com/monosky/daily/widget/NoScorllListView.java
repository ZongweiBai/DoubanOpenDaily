package com.monosky.daily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义不滑动的ListView
 * Created by jonez_000 on 2015/8/18.
 */
public class NoScorllListView extends ListView {
    public NoScorllListView(Context context) {
        super(context);
    }

    public NoScorllListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScorllListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
