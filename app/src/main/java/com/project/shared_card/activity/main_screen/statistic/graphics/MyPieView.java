package com.project.shared_card.activity.main_screen.statistic.graphics;

import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.project.shared_card.R;

import java.util.ArrayList;

import im.dacer.androidcharts.MyUtils;


public class MyPieView extends View {

    public static final int NO_SELECTED_INDEX = -1;
    private final int[] DEFAULT_COLOR_LIST = {
            getColor(getContext(), R.color.pie_1), getColor(getContext(), R.color.pie_2), getColor(getContext(), R.color.pie_3),
            getColor(getContext(), R.color.pie_4), getColor(getContext(), R.color.pie_5), getColor(getContext(), R.color.pie_6),
            getColor(getContext(), R.color.pie_7), getColor(getContext(), R.color.pie_8), getColor(getContext(), R.color.pie_9),
            getColor(getContext(), R.color.pie_10), getColor(getContext(), R.color.pie_11), getColor(getContext(), R.color.pie_12),
            getColor(getContext(), R.color.pie_13), getColor(getContext(), R.color.pie_14), getColor(getContext(), R.color.pie_15),
            getColor(getContext(), R.color.pie_16)
    };
    private Paint cirPaint;
    private Point pieCenterPoint;
    private Paint popupTextPaint = new Paint();
    private Paint textPaint;
    private RectF cirRect;
    private RectF cirSelectedRect;
    private int mViewWidth;
    private int mViewHeight;
    private int margin;
    private Context contextView;
    private int pieRadius;
    private MyPieView.OnPieClickListener onPieClickListener;
    private ArrayList<MyPieHelper> pieHelperList;
    private int selectedIndex = NO_SELECTED_INDEX;
    private ArrayList<String> namesLabel;
    private boolean showPercentLabel = true;
    private final int bottomTriangleHeight = 12;
    private final int popupTopPadding = MyUtils.dip2px(getContext(), 2);
    private final int popupBottomMargin = MyUtils.dip2px(getContext(), 5);
    private int popupBottomPadding = MyUtils.dip2px(getContext(), 2);
    private Runnable animator = new Runnable() {
        @Override public void run() {
            boolean needNewFrame = false;
            for (MyPieHelper pie : pieHelperList) {
                pie.update();
                if (!pie.isAtRest()) {
                    needNewFrame = true;
                }
            }
            if (needNewFrame) {
                postDelayed(this, 45);
            }
            invalidate();
        }
    };

    public MyPieView(Context context) {
        this(context, null);
    }

    public MyPieView(Context context, AttributeSet attrs) {
        super(context, attrs);

        pieHelperList = new ArrayList<MyPieHelper>();
        cirPaint = new Paint();
        cirPaint.setAntiAlias(true);
        cirPaint.setColor(getColor(getContext(), R.color.dark_green));
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getColor(getContext(), R.color.dark_green));
        textPaint.setTextSize(MyUtils.sp2px(getContext(), 9));
        textPaint.setStrokeWidth(3);
        textPaint.setTextAlign(Paint.Align.CENTER);
        pieCenterPoint = new Point();
        cirRect = new RectF();
        cirSelectedRect = new RectF();
        contextView = context;
    }

    public void showPercentLabel(boolean show) {
        showPercentLabel = show;
        postInvalidate();
    }

    public void setOnPieClickListener(MyPieView.OnPieClickListener listener) {
        onPieClickListener = listener;
    }

    public void setDate(ArrayList<MyPieHelper> helperList) {
        initPies(helperList);
        pieHelperList.clear();
        removeSelectedPie();

        if (!helperList.isEmpty()) {
            for (MyPieHelper pieHelper : helperList) {
                pieHelperList.add(new MyPieHelper(pieHelper.getStartDegree(), pieHelper.getStartDegree(), pieHelper));
            }
        } else {
            pieHelperList.clear();
        }

        removeCallbacks(animator);
        post(animator);

        //        pieHelperList = helperList;
        //        postInvalidate();
    }

    /**
     * Set startDegree and endDegree for each PieHelper
     */
    private void initPies(ArrayList<MyPieHelper> helperList) {
        float totalAngel = 270;
        for (MyPieHelper pie : helperList) {
            pie.setDegree(totalAngel, totalAngel + pie.getSweep());
            totalAngel += pie.getSweep();
        }
    }

    public void selectedPie(int index) {
        selectedIndex = index;
        if (onPieClickListener != null) onPieClickListener.onPieClick(index);
        postInvalidate();
    }

    public void removeSelectedPie() {
        selectedIndex = NO_SELECTED_INDEX;
        if (onPieClickListener != null) onPieClickListener.onPieClick(NO_SELECTED_INDEX);
        postInvalidate();
    }

    @Override protected void onDraw(Canvas canvas) {
        if (pieHelperList.isEmpty()) {
            return;
        }

        int index = 0;
        for (MyPieHelper pieHelper : pieHelperList) {
            boolean selected = (selectedIndex == index);
            RectF rect = selected ? cirSelectedRect : cirRect;
            if (pieHelper.isColorSetted()) {
                cirPaint.setColor(pieHelper.getColor());
            } else {
                cirPaint.setColor(DEFAULT_COLOR_LIST[index % 16]);
            }
            canvas.drawArc(rect, pieHelper.getStartDegree(), pieHelper.getSweep(), true, cirPaint);
            drawPercentText(canvas, pieHelper);

            drawLineBesideCir(canvas, pieHelper.getStartDegree(), selected);
            drawLineBesideCir(canvas, pieHelper.getEndDegree(), selected);

            index++;
        }
        if (selectedIndex != -1){
            drawPopup(canvas, new Point(canvas.getWidth()/2, canvas.getHeight()/2+50));
        }
    }

    private void drawLineBesideCir(Canvas canvas, float angel, boolean selectedCir) {
        int sth2 = selectedCir ? mViewHeight / 2
                : pieRadius; // Sorry I'm really don't know how to name the variable..
        int sth = 1;                                       // And it's
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1;
        }
        float lineToX = (float) (mViewHeight / 2 + Math.cos(Math.toRadians(-angel)) * sth2);
        float lineToY =
                (float) (mViewHeight / 2 + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * sth2);
    }

    private void drawPercentText(Canvas canvas, MyPieHelper pieHelper) {
        if (!showPercentLabel) return;
        float angel = (pieHelper.getStartDegree() + pieHelper.getEndDegree()) / 2;
        int sth = 1;
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1;
        }
        float x = (float) (mViewHeight / 2 + Math.cos(Math.toRadians(-angel)) * pieRadius / 1.25);
        float y = (float) (mViewHeight / 2
                + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * pieRadius / 1.25);
        canvas.drawText(pieHelper.getPercentStr(), x, y, textPaint);
    }

    private void drawText(Canvas canvas, MyPieHelper pieHelper) {
        if (pieHelper.getTitle() == null) return;
        float angel = (pieHelper.getStartDegree() + pieHelper.getEndDegree()) / 2;
        int sth = 1;
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1;
        }
        float x = (float) (mViewHeight / 2 + Math.cos(Math.toRadians(-angel)) * pieRadius / 2);
        float y = (float) (mViewHeight / 2
                + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * pieRadius / 2);
        canvas.drawText(pieHelper.getTitle(), x, y, textPaint);
    }

    public void addNames(ArrayList<String> names){
        namesLabel = names;
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {

            int clickedIndex = findPointAt((int) event.getX(), (int) event.getY());
            if (clickedIndex == selectedIndex) {
                selectedIndex = NO_SELECTED_INDEX;
            } else {
                selectedIndex = clickedIndex;
            }
            if (onPieClickListener != null) {
                onPieClickListener.onPieClick(selectedIndex);
            }
            postInvalidate();
        }

        return true;
    }

    /**
     * find pie index where point is
     */
    private int findPointAt(int x, int y) {
        double degree = Math.atan2(x - pieCenterPoint.x, y - pieCenterPoint.y) * 180 / Math.PI;
        degree = -(degree - 180) + 270;
        int index = 0;
        for (MyPieHelper pieHelper : pieHelperList) {
            if (degree >= pieHelper.getStartDegree() && degree <= pieHelper.getEndDegree()) {
                return index;
            }
            index++;
        }
        return NO_SELECTED_INDEX;
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        margin = mViewWidth / 16;
        pieRadius = (mViewWidth) / 2 - margin;
        pieCenterPoint.set(pieRadius + margin, pieRadius + margin);
        cirRect.set(pieCenterPoint.x - pieRadius, pieCenterPoint.y - pieRadius,
                pieCenterPoint.x + pieRadius, pieCenterPoint.y + pieRadius);
        cirSelectedRect.set(2, //minor margin for bigger circle
               2, mViewWidth -2, mViewHeight - 2);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        int preferred = 3;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = mViewWidth;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int measurement;

        switch (View.MeasureSpec.getMode(measureSpec)) {
            case View.MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }
    private void drawPopup(Canvas canvas, Point point) {
        popupTextPaint.setAntiAlias(true);
        popupTextPaint.setColor(getColor(getContext(), R.color.dark_green));
        popupTextPaint.setTextSize(MyUtils.sp2px(getContext(), 13));
        popupTextPaint.setStrokeWidth(5);
        popupTextPaint.setTextAlign(Paint.Align.CENTER);

        String numStr = String.valueOf(namesLabel.get(selectedIndex));
        boolean singularNum = (numStr.length() == 1);
        int sidePadding = MyUtils.dip2px(getContext(), singularNum ? 8 : 5);
        int x = point.x;
        int y = point.y - MyUtils.dip2px(getContext(), 10);
        Rect popupTextRect = new Rect();
        popupTextPaint.getTextBounds(numStr, 0, numStr.length(), popupTextRect);
        Rect r = new Rect(x - popupTextRect.width() / 2 - sidePadding, y
                - popupTextRect.height()
                - bottomTriangleHeight
                - popupTopPadding * 2
                - popupBottomMargin, x + popupTextRect.width() / 2 + sidePadding,
                y + popupTopPadding - popupBottomMargin + popupBottomPadding);

        Drawable popup =
                (Drawable) getDrawable(getContext(), R.drawable.long_popup);
        popup.setColorFilter(new PorterDuffColorFilter(DEFAULT_COLOR_LIST[selectedIndex], PorterDuff.Mode.MULTIPLY));
        popup.setBounds(r);
        popup.draw(canvas);
        canvas.drawText(numStr, x, y - bottomTriangleHeight - popupBottomMargin, popupTextPaint);
    }

    public interface OnPieClickListener {
        void onPieClick(int index);
    }
}
