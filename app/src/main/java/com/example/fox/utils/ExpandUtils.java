package com.example.fox.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by MagicFox on 2016/11/21.
 */
public class ExpandUtils {

    public static int getColor(final Context context, final int drawableId) {
        return ContextCompat.getColor(context, drawableId);
    }

    public static Drawable getDrawable(final Context context, final int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if(textView == null)return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    public static void setLeftCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable start) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, null, null, null);
    }

    public static void setRightCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable right) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(textView, null, null, right, null);
    }

    public static void setTopCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable top) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(textView, null, top, null, null);
    }

    public static void setBottomCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable bottom) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(textView, null, null, null, bottom);
    }

}
