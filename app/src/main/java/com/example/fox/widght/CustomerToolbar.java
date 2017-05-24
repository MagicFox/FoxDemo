package com.example.fox.widght;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fox.R;
import com.example.fox.utils.ExpandUtils;


/**
 * 标题栏
 * Created by MagicFox on 2017/3/9.
 */

public class CustomerToolbar extends Toolbar implements View.OnClickListener {

    public static final int NO_RESOURCE = 0;

    private RelativeLayout mRlTitle;
    private TextView mLeftButton;
    private TextView mTextTitle;//title
    private TextView mRightButton;
    private View mView;

    private LayoutInflater mInflater;
    private Listener mLeftListener, mRightListener;

    public CustomerToolbar(Context context) {
        this(context, null);
    }

    public CustomerToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
//        setContentInsetsRelative(10, 10);
    }

    private void init() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.common_title, null);
            mRlTitle = (RelativeLayout) mView.findViewById(R.id.rl_title);
            mTextTitle = (TextView) mView.findViewById(R.id.tvCommonTitle);
            mLeftButton = (TextView) mView.findViewById(R.id.tvCommonTitleLeft);
            mRightButton = (TextView) mView.findViewById(R.id.tvCommonTitleRight);
            //然后使用LayoutParams把控件添加到子view中
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);

            mLeftButton.setOnClickListener(this);
            mRightButton.setOnClickListener(this);
        }
    }

    /** 设置标题栏的背景颜色 */
    public CustomerToolbar setTitleBarBackground(@ColorRes int resId) {
        if (resId != NO_RESOURCE) {
            mRlTitle.setBackgroundResource(resId);
        }
        return this;
    }

    public CustomerToolbar setTitle(String title) {
        mTextTitle.setText(title);
        return this;
    }

    /**
     * 设置左侧按钮属性
     * @param resId：内容
     * @param colorId: 文字颜色
     * @param drawableId： 图标
     * @return
     */
    public CustomerToolbar setLeftBtnText(@StringRes int resId, @ColorRes int colorId, @DrawableRes int drawableId) {
        if (resId != NO_RESOURCE)
            mLeftButton.setText(resId);
        if (colorId != NO_RESOURCE){
            mLeftButton.setTextColor(ExpandUtils.getColor(getContext(),colorId));
        }
        if (drawableId != NO_RESOURCE) {
            ExpandUtils.setLeftCompoundDrawablesRelativeWithIntrinsicBounds(mLeftButton, getResources().getDrawable(drawableId));
        }
        return this;
    }

    /**
     * 设置右侧按钮属性
     * @param resId：内容
     * @param colorId: 文字颜色
     * @param drawableId： 图标
     * @return
     */
    public CustomerToolbar setRightBtnText(@StringRes int resId, @ColorRes int colorId, @DrawableRes int drawableId) {
        if (resId != NO_RESOURCE)
            mRightButton.setText(resId);
        if (colorId != NO_RESOURCE)
            mRightButton.setTextColor(getResources().getColor(colorId));
        if (drawableId != NO_RESOURCE) {
            ExpandUtils.setRightCompoundDrawablesRelativeWithIntrinsicBounds(mRightButton, getResources().getDrawable(drawableId));
        }
        return this;
    }

    public CustomerToolbar setLeftVisible(int visibility) {
        if (mLeftButton != null) {
            mLeftButton.setVisibility(visibility);
        }
        return this;
    }

    public CustomerToolbar setRightVisible(int visibility) {
        if (mRightButton != null) {
            mRightButton.setVisibility(visibility);
        }
        return this;
    }

    public CustomerToolbar setLeftBtnListener(Listener listener) {
        if (mLeftListener == null) {
            mLeftListener = listener;
        }
        return this;
    }

    public CustomerToolbar setRightBtnListener(Listener listener) {
        if (mRightListener == null) {
            mRightListener = listener;
        }
        return this;
    }

    public interface Listener {
        void onClick(View v);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCommonTitleLeft:
                if (mLeftListener != null) {
                    mLeftListener.onClick(v);
                }
                break;

            case R.id.tvCommonTitleRight:
                if (mRightListener != null) {
                    mRightListener.onClick(v);
                }
                break;

            default:
                break;
        }
    }
}
