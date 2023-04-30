package com.project.shared_card.activity.main_screen.check.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;

import com.project.shared_card.R;

public class DialogAddProduct {
    Context context;
    public Dialog dialog;
    public EditText name,count;
    public Spinner category, metric;
    public Button ready;
    public DialogAddProduct(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_create_product);
        this.context = context;
        name = dialog.findViewById(R.id.dialog_edit_text_product);
        count = dialog.findViewById(R.id.dialog_count_of_product);
        category = dialog.findViewById(R.id.dialog_text_category);
        metric = dialog.findViewById(R.id.dialog_metric);
        ready = dialog.findViewById(R.id.dialog_btn_add);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
