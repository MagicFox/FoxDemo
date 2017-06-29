package com.example.fox.progressbardemo.view;

/**
 * Created by magicfox on 2017/6/21.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.fox.progressbardemo.R;

public class UpdateLoadingProgressBar extends View {

    private int DEFAULT_HEIGHT_DP = 20;

    private int borderWidth;

    private float maxProgress = 100f;

    private Paint textPaint;

    private Paint bgPaint;

    private Paint pgPaint;

    private String progressText;

    private Rect textRect;

    private RectF bgRectF;

    /**
     * 进度条 bitmap ，包含滑块
     */
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    /**
     * 当前进度
     */
    private float progress;

    private boolean isFinish;

    private boolean isStop;

    /**
     * 下载中颜色
     */
    private int loadingColor;

    /**
     * 暂停时颜色
     */
    private int stopColor;

    /**
     * 进度文本、边框、进度条颜色
     */
    private int progressColor;

    private int textSize;

    private int radius;

    BitmapShader bitmapShader;

    public UpdateLoadingProgressBar(Context context) {
        this(context, null, 0);
    }

    public UpdateLoadingProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpdateLoadingProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.UpdateLoadingProgressBar);
        try {
            textSize = (int) ta.getDimension(R.styleable.UpdateLoadingProgressBar_textSize, 12);
            loadingColor = ta.getColor(R.styleable.UpdateLoadingProgressBar_loadingColor, Color.parseColor("#40c4ff"));
            stopColor = ta.getColor(R.styleable.UpdateLoadingProgressBar_stopColor, Color.parseColor("#ff9800"));
            radius = (int) ta.getDimension(R.styleable.UpdateLoadingProgressBar_radius, 0);
            borderWidth = (int) ta.getDimension(R.styleable.UpdateLoadingProgressBar_borderWidth, 1);
        } finally {
            ta.recycle();
        }
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);

        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);

        textRect = new Rect();
        bgRectF = new RectF(borderWidth, borderWidth, getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth);

        if(isStop){
            progressColor = stopColor;
        } else{
            progressColor = loadingColor;
        }

        initPgBitmap();
    }

    private void initPgBitmap() {
        pgBitmap = Bitmap.createBitmap(getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth, Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightSpecMode){
            case MeasureSpec.AT_MOST:
                height = dp2px(DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                height = heightSpecSize;
                break;
        }
        setMeasuredDimension(widthSpecSize, height);

        if(pgBitmap == null){
            init();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //进度
        drawProgress(canvas);

        //进度text
        drawProgressText(canvas);

        //变色处理
        drawColorProgressText(canvas);
    }

    /**
     * 进度
     */
    private void drawProgress(Canvas canvas) {
        pgPaint.setColor(progressColor);

        float right = (progress / maxProgress) * getMeasuredWidth();
        pgCanvas.save(Canvas.CLIP_SAVE_FLAG);
        pgCanvas.clipRect(0, 0, right, getMeasuredHeight());
        pgCanvas.drawColor(progressColor);
        pgCanvas.restore();

        //控制显示区域
        bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        pgPaint.setShader(bitmapShader);
        canvas.drawRoundRect(bgRectF, radius, radius, pgPaint);
    }

    /**
     * 进度提示文本
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {
        textPaint.setColor(progressColor);
        progressText = getProgressText();
        textPaint.getTextBounds(progressText, 0, progressText.length(), textRect);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
    }

    /**
     * 变色处理
     * @param canvas
     */
    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        float progressWidth = (progress / maxProgress) * getMeasuredWidth();
        if(progressWidth > xCoordinate){
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            float right = Math.min(progressWidth, xCoordinate + tWidth * 1.1f);
            canvas.clipRect(xCoordinate, 0, right, getMeasuredHeight());
            canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
            canvas.restore();
        }
    }

    public void setProgress(float progress){
        if(!isStop){
            if(progress < maxProgress){
                this.progress = progress;
            } else {
                this.progress = maxProgress;
                finishLoad();
            }
            invalidate();
        }
    }

    public void setStop(boolean stop) {
        isStop = stop;
        if(isStop){
            progressColor = stopColor;
        } else {
            progressColor = loadingColor;
        }
        invalidate();
    }

    public void finishLoad() {
        isFinish = true;
        setStop(true);
    }

    public void toggle(){
        if(!isFinish){
            if(isStop){
                setStop(false);
            } else {
                setStop(true);
            }
        }
    }

    /**
     * 重置
     */
    public void reset(){
        setStop(true);
        progress = 0;
        isFinish = false;
        isStop = false;
        progressColor = loadingColor;
        progressText = "";
        initPgBitmap();
    }

    public float getProgress() {
        return progress;
    }

    public boolean isStop() {
        return isStop;
    }

    public boolean isFinish() {
        return isFinish;
    }

    private String getProgressText() {
        String text= "";
        if(!isFinish){
            if(!isStop){
                text = String.format("%s%%",(int)progress);//"下载中" + progress + "%";
            } else {
                text = "继续";
            }
        } else{
            text = "下载完成";
        }
        return text;
    }

    private int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
