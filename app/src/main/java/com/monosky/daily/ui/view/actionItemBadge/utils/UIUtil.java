package com.monosky.daily.ui.view.actionItemBadge.utils;

import android.content.Context;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

/**
 * Created by mikepenz on 02.07.15.
 */
public class UIUtil {


    public static int convertDpToPx(Context context, float dp) {
        return (int) applyDimension(COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
