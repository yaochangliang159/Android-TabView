package com.ycl.tabview.library;

import android.content.Context;
import android.util.TypedValue;

/**
 * 作者：yaochangliang on 2016/8/13 17:41
 * 邮箱：yaochangliang159@sina.com
 */
public class TabViewUtil {

    public TabViewUtil() {
    }
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }
}
