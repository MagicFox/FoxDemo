package com.example.fox.progressbardemo.view;

/**
 * Created by magicfox on 2017/6/21.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.fox.progressbardemo.R;


public class TranslatedImageView extends ImageView {

    private float translatedScale = 1f;

    public TranslatedImageView(final Context context) {
        this(context, null);
    }

    public TranslatedImageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TranslatedImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.translatedImageView);
            translatedScale = a.getFloat(R.styleable.translatedImageView_translatedScale, 1);
            a.recycle();
        }

        setScaleType(ImageView.ScaleType.MATRIX);
        setImageMatrix(matrix);
    }

    private final Matrix matrix = new Matrix();

    public void setScale(final float scale) {
        translatedScale = scale;

        Drawable drawable = getDrawable();
        int dw = drawable.getIntrinsicWidth();
        float offsetX = dw * (1 - translatedScale);

        matrix.reset();
        matrix.postTranslate(-offsetX, 0);
        setImageMatrix(matrix);
    }
}

