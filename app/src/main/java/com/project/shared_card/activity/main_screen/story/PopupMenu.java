package com.project.shared_card.activity.main_screen.story;

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
    Button buttonSort;

    public PopupMenu(Context getContext, Button buttonSort) {
        this.getContext = getContext;
        this.buttonSort = buttonSort;
        heightStart = buttonSort.getLayoutParams().height;
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
    public void popupMenu(){
        openAnimation();
        Context wrapper = new ContextThemeWrapper(getContext, R.style.popup_menu);
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(wrapper, buttonSort);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.by_product:
                        Toast.makeText(getContext,
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.by_date:
                        Toast.makeText(getContext,
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.by_category:
                        Toast.makeText(getContext,
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.by_user:
                        Toast.makeText(getContext,
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                    default:
                        return false;
                }

            }
        });
        popupMenu.setOnDismissListener(new android.widget.PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(android.widget.PopupMenu menu) {
                closeAnimation();
            }
        });
        popupMenu.show();
    }
}
