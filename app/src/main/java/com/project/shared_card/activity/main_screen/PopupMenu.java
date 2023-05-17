package com.project.shared_card.activity.main_screen;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.project.shared_card.R;

public class PopupMenu {
    final int SPEED_ANIMATION_SORT_ICON = 300;
    int heightStart;
    final int heightEnd=50;
    Context getContext;
    public android.widget.PopupMenu popupMenu;
    Button buttonSort;

    public PopupMenu(Context getContext, Button buttonSort) {
        this.getContext = getContext;
        this.buttonSort = buttonSort;
        heightStart = buttonSort.getLayoutParams().height;
        Context wrapper = new ContextThemeWrapper(getContext, R.style.popup_menu);
        popupMenu = new android.widget.PopupMenu(wrapper, buttonSort);
        popupMenu.inflate(R.menu.popup_menu);
    }

    private void openAnimation(){
        ValueAnimator widthAnimator = ValueAnimator.ofInt(heightStart, heightEnd);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                buttonSort.getLayoutParams().height = value;
                buttonSort.requestLayout();
            }
        });
        widthAnimator.setDuration(SPEED_ANIMATION_SORT_ICON);
        widthAnimator.start();
    }
    private void closeAnimation(){
        ValueAnimator widthAnimator = ValueAnimator.ofInt( heightEnd, heightStart);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                buttonSort.getLayoutParams().height = value;
                buttonSort.requestLayout();
            }
        });
        widthAnimator.setDuration(SPEED_ANIMATION_SORT_ICON);
        widthAnimator.start();
    }
    public void openPopupMenu(){
        openAnimation();

        popupMenu.setOnDismissListener(new android.widget.PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(android.widget.PopupMenu menu) {
                closeAnimation();
            }
        });
        popupMenu.show();
    }
}
