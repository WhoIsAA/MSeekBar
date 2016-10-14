package com.whoisaa.mseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by AA on 2016/10/13.
 */

public class MSeekBar extends SeekBar {

    /**
     * SeekBar数值文字颜色
     */
    private int mTextColor;

    /**
     * SeekBar数值文字大小
     */
    private float mTextSize;

    /**
     * SeekBar数值文字内容
     */
    private String mText;

    /**
     * SeekBar数值文字背景
     */
    private Bitmap mBackgroundBitmap;

    /**
     * SeekBar数值文字背景宽高
     */
    private float mBgWidth, mBgHeight;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * SeekBar数值文字方向
     */
    private int mTextOrientation;

    /**
     * SeekBar数值文字宽度
     */
    private float mTextWidth;

    /**
     * SeekBar数值文字基线Y坐标
     */
    private float mTextBaseLineY;

    //文字方向
    private static final int ORIENTATION_TOP = 1;
    private static final int ORIENTATION_BOTTOM = 2;



    public MSeekBar(Context context) {
        this(context, null);
    }

    public MSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MSeekBar, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for(int i = 0; i < count; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.MSeekBar_textColor:
                    mTextColor = ta.getColor(index, Color.WHITE);
                    break;

                case R.styleable.MSeekBar_textSize:
                    mTextSize = ta.getDimension(index, 15f);
                    break;

                case R.styleable.MSeekBar_textBackground:
                    //获取文字背景图片的宽高
                    int bgResId = ta.getResourceId(index, R.mipmap.bg_seekbar_display1);
                    mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), bgResId);
                    mBgWidth = mBackgroundBitmap.getWidth();
                    mBgHeight = mBackgroundBitmap.getHeight();
                    break;

                case R.styleable.MSeekBar_textOrientation:
                    mTextOrientation = ta.getInt(index, ORIENTATION_TOP);
                    break;
            }
        }
        ta.recycle();

        //设置画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);

        //设置文字显示方向
        if(mTextOrientation == ORIENTATION_TOP) {
            //设置SeekBar顶部数值文字预留空间，左右为数值背景图片的一半，顶部为数值背景图片高度加五的间隔
            setPadding((int) Math.ceil(mBgWidth) / 2, (int) Math.ceil(mBgHeight) + 5, (int) Math.ceil(mBgWidth) / 2, 0);
        } else {
            //设置SeekBar顶部数值文字预留空间，左右为数值背景图片的一半，底部为数值背景图片高度加五的间隔
            setPadding((int) Math.ceil(mBgWidth) / 2, 0, (int) Math.ceil(mBgWidth) / 2, (int) Math.ceil(mBgHeight) + 5);
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getTextLocation();
        Rect bgRect = getProgressDrawable().getBounds();
        //计算数值背景X坐标
        float bgX = bgRect.width() * getProgress() / getMax();
        //计算数值背景Y坐标
        float bgY = 0;
        if(mTextOrientation == ORIENTATION_BOTTOM) {
            bgY = mBgHeight + 10;
        }

        //计算数值文字X坐标
        float textX = bgX + (mBgWidth - mTextWidth) / 2;
        float textY = (float) (mTextBaseLineY + bgY + (0.16 * mBgHeight / 2) - 10);

        //绘制文字和背景
        canvas.drawBitmap(mBackgroundBitmap, bgX, bgY, mPaint);
        canvas.drawText(mText, textX, textY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return super.onTouchEvent(event);
    }

    /**
     * 计算SeekBar数值文字的显示位置
     */
    private void getTextLocation() {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mText = "￥" + getProgress();
        //测量文字宽度
        mTextWidth = mPaint.measureText(mText);
        //计算文字基线Y坐标
        mTextBaseLineY = mBgHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
    }
}
