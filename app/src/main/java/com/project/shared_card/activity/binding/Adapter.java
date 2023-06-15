package com.project.shared_card.activity.binding;

import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.BindingAdapter;

public class Adapter {
    @BindingAdapter("status")
    public static void changeStatus(View view, int status) {
        if(status==0){
            ((CheckBox) view).setChecked(false);
        }
        else{
            ((CheckBox) view).setChecked(true);
        }
    }
}
