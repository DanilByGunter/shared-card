package com.project.shared_card.activity.main_screen.statistic.graphics;


import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.project.shared_card.R;

import java.util.ArrayList;

import im.dacer.androidcharts.MyUtils;


public class MyBarView extends View {
    private final int MINI_BAR_WIDTH;
    private final int BAR_SIDE_MARGIN;
    private final int TEXT_TOP_MARGIN;
    private final int[] FOREGROUND_COLOR = {
            getColor(getContext(), R.color.pie_16), getColor(getContext(), R.color.pie_15), getColor(getContext(), R.color.pie_14),
            getColor(getContext(), R.color.pie_13), getColor(getContext(), R.color.pie_12), getColor(getContext(), R.color.pie_11),
            getColor(getContext(), R.color.pie_10), getColor(getContext(), R.color.pie_9), getColor(getContext(), R.color.pie_8),
            getColor(getContext(), R.color.pie_7), getColor(getContext(), R.color.pie_6), getColor(getContext(), R.color.pie_5),
            getColor(getContext(), R.color.pie_4), getColor(getContext(), R.color.pie_3), getColor(getContext(), R.color.pie_2),
            getColor(getContext(), R.color.pie_1)
    };
    private final int BACKGROUND_COLOR = getColor(getContext(), R.color.light_green_2_translucent);
    private ArrayList<Float> percentList;
    private ArrayList<Float> targetPercentList;
    private Paint textPaint;
    private Paint bgPaint;
    private Paint fgPaint;
    private Rect rect;
    private int barWidth;
    private int bottomTextDescent;
    private boolean autoSetWidth = true;
    private int topMargin;
    private int bottomTextHeight;
    private ArrayList<String> bottomTextList = new ArrayList<String>();
    private ArrayList<Integer> primary_data;
    private Runnable animator = new Runnable() {
        @Override public void run() {
            boolean needNewFrame = false;
            for (int i = 0; i < targetPercentList.size(); i++) {
                if (percentList.get(i) < targetPercentList.get(i)) {
                    percentList.set(i, percentList.get(i) + 0.02f);
                    needNewFrame = true;
                } else if (percentList.get(i) > targetPercentList.get(i)) {
                    percentList.set(i, percentList.get(i) - 0.02f);
                    needNewFrame = true;
                }
                if (Math.abs(targetPercentList.get(i) - percentList.get(i)) < 0.02f) {
                    percentList.set(i, targetPercentList.get(i));
                }
            }
            if (needNewFrame) {
                postDelayed(this, 40);
            }
            invalidate();
        }
    };

    public MyBarView(Context context) {
        this(context, null);
    }

    public MyBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(BACKGROUND_COLOR);
        fgPaint = new Paint(bgPaint);
        rect = new Rect();
        topMargin = MyUtils.dip2px(context, 5);
        int textSize = MyUtils.sp2px(context, 12);
        barWidth = MyUtils.dip2px(context, 4);
        MINI_BAR_WIDTH = MyUtils.dip2px(context, 6);
        BAR_SIDE_MARGIN = MyUtils.dip2px(context, 6);
        TEXT_TOP_MARGIN = MyUtils.dip2px(context, 5);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        percentList = new ArrayList<Float>();
    }

    /**
     * dataList will be reset when called is method.
     *
     * @param bottomStringList The String ArrayList in the bottom.
     */
    public void setBottomTextList(ArrayList<String> bottomStringList) {
        //        this.dataList = null;
        this.bottomTextList = bottomStringList;
        Rect r = new Rect();
        bottomTextDescent = 0;
        barWidth = MINI_BAR_WIDTH;
        for (String s : bottomTextList) {
            textPaint.getTextBounds(s, 0, s.length(), r);
            if (bottomTextHeight < r.height()) {
                bottomTextHeight = r.height();
            }
            if (autoSetWidth && (barWidth < r.width())) {
                barWidth = r.width();
            }
            if (bottomTextDescent < (Math.abs(r.bottom))) {
                bottomTextDescent = Math.abs(r.bottom);
            }
        }
        setMinimumWidth(2);
        postInvalidate();
    }

    /**
     * @param list The ArrayList of Integer with the range of [0-max].
     */
    public void setDataList(ArrayList<Integer> list, int max) {
        primary_data = list;
        targetPercentList = new ArrayList<Float>();
        if (max == 0) max = 1;

        for (Integer integer : list) {
            targetPercentList.add(1 - (float) integer / (float) max);
        }

        // Make sure percentList.size() == targetPercentList.size()
        if (percentList.isEmpty() || percentList.size() < targetPercentList.size()) {
            int temp = targetPercentList.size() - percentList.size();
            for (int i = 0; i < temp; i++) {
                percentList.add(1f);
            }
        } else if (percentList.size() > targetPercentList.size()) {
            int temp = percentList.size() - targetPercentList.size();
            for (int i = 0; i < temp; i++) {
                percentList.remove(percentList.size() - 1);
            }
        }
        setMinimumWidth(2);
        removeCallbacks(animator);
        post(animator);
    }

    @Override protected void onDraw(Canvas canvas) {
        int i = 1;
        if (percentList != null && !percentList.isEmpty()) {
            for (Float f : percentList) {
                fgPaint.setColor(FOREGROUND_COLOR[i%16]);
                rect.set(BAR_SIDE_MARGIN * i + barWidth * (i - 1), topMargin,
                        (BAR_SIDE_MARGIN + barWidth) * i,
                        getHeight() - bottomTextHeight - TEXT_TOP_MARGIN);
                canvas.drawRect(rect, bgPaint);
                /*rect.set(BAR_SIDE_MARGIN*i+barWidth*(i-1),
                        topMargin+(int)((getHeight()-topMargin)*percentList.get(i-1)),
                        (BAR_SIDE_MARGIN+barWidth)* i,
                        getHeight()-bottomTextHeight-TEXT_TOP_MARGIN);*/
                /**
                 * The correct total height is "getHeight()-topMargin-bottomTextHeight-TEXT_TOP_MARGIN",not "getHeight()-topMargin".
                 * fix by zhenghuiy@gmail.com on 11/11/13.
                 */
                rect.set(BAR_SIDE_MARGIN * i + barWidth * (i - 1), topMargin + (int) ((getHeight()
                                - topMargin
                                - bottomTextHeight
                                - TEXT_TOP_MARGIN) * percentList.get(i - 1)),
                        (BAR_SIDE_MARGIN + barWidth) * i,
                        getHeight() - bottomTextHeight - TEXT_TOP_MARGIN);
                canvas.drawRect(rect, fgPaint);
                textPaint.setColor(getColor(getContext(), R.color.dark_green));
                canvas.drawText(String.valueOf(primary_data.get(i-1)), BAR_SIDE_MARGIN * i + barWidth * (i - 1) + barWidth / 2,
                        getHeight()/2 - bottomTextDescent, textPaint);
                i++;
            }
        }

        if (bottomTextList != null && !bottomTextList.isEmpty()) {
            i = 1;
            for (String s : bottomTextList) {
                textPaint.setColor(FOREGROUND_COLOR[i%16]);
                canvas.drawText(s, BAR_SIDE_MARGIN * i + barWidth * (i - 1) + barWidth / 2,
                        getHeight() - bottomTextDescent, textPaint);
                i++;
            }
        }
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mViewWidth = measureWidth(widthMeasureSpec);
        int mViewHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        int preferred = 0;
        if (bottomTextList != null) {
            preferred = bottomTextList.size() * (barWidth + BAR_SIDE_MARGIN);
        }
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = 222;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }
}
