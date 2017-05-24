package com.example.fox.widght;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.example.fox.R;


/**
 *
 */
public class LoadingProgressDialog extends ProgressDialog {
	private Window window;

	public LoadingProgressDialog(Context context) {
		super(context);
	}

	public LoadingProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		window = getWindow();
		setCanceledOnTouchOutside(false);
		setWindowSize(SizeUtils.dp2px(getContext(), 120), SizeUtils.dp2px(getContext(), 120));
		setScreen();
		View view = getLayoutInflater().inflate(R.layout.loading_dialog, null);
		view.setBackgroundDrawable(getBackgroundDrawable());
		setContentView(view);
	}

	/**
	 * 设置窗口大小
	 * 
	 * @param width
	 * @param height
	 */
	private void setWindowSize(int width, int height) {
		window.getAttributes().width = width;
		window.getAttributes().height = height;
	}

	/**
	 * 设置屏幕不变暗
	 */
	private void setScreen() {
		LayoutParams lp = window.getAttributes();
		lp.dimAmount = 0;
		window.setAttributes(lp);
	}

	/**
	 * 
	 */
	private Drawable getBackgroundDrawable() {
		ColorDrawable background = new ColorDrawable() {

			@Override
			public void draw(Canvas canvas) {
				super.draw(canvas);
				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
				paint.setColor(Color.BLACK);
				paint.setAlpha(100);
				canvas.drawRoundRect(new RectF(getBounds()), SizeUtils.dp2px(getContext(), 10),
						SizeUtils.dp2px(getContext(), 10), paint);
			}

		};
		return background;
	}
}
