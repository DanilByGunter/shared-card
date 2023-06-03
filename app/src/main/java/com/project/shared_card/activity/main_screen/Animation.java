package com.project.shared_card.activity.main_screen;

import android.animation.ValueAnimator;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class Animation {
    public static void animationUpOfNavigationView(NavigationView navigationView,int heightStartForNavigation){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) navigationView.getY(), heightStartForNavigation);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                navigationView.setY(value);
                navigationView.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
    public static void animationDownOfNavigationView(NavigationView navigationView){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) navigationView.getY(),2200);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                navigationView.setY(value);
                navigationView.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
    public static void animationDownOfButton(FloatingActionButton buttonAddProduct){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) buttonAddProduct.getY(),1850);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                buttonAddProduct.setY(value);
                buttonAddProduct.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
    public static void animationUpOfButton(FloatingActionButton buttonAddProduct,int heightStartForButton){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) buttonAddProduct.getY(), heightStartForButton);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                buttonAddProduct.setY(value);
                buttonAddProduct.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
}
