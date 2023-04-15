package com.project.shared_card.activity.main_screen.check;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.project.shared_card.R;

public class HelperForAnimation {
    final int SPEED_ANIMATION_SORT_ICON = 300;
    int heightStart;
    final int heightEnd=50;
    Context getContext;
    Button buttonSort;

    public HelperForAnimation(Context getContext, Button buttonSort) {
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
        PopupMenu popupMenu = new PopupMenu(wrapper, buttonSort);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
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
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                closeAnimation();
            }
        });
        popupMenu.show();
    }
}
