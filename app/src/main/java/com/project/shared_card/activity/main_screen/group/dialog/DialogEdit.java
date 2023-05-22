package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;

import com.project.shared_card.R;

public class DialogEdit {
    Context context;
    public Dialog dialog;
    public EditText name;
    public ImageView image;
    public Button ready;
    ActivityResultLauncher<String> getContent;
    public DialogEdit(Context context, ActivityResultLauncher<String> getContent) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_profile);
        this.context = context;
        this.getContent = getContent;
        name = dialog.findViewById(R.id.dialog_edit_name);
        image = dialog.findViewById(R.id.dialog_image);
        ready = dialog.findViewById(R.id.dialog_ready);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        image.setOnClickListener(this::imageOnClick);
    }
    void imageOnClick(View v){
        getContent.launch("image/*");
    }

}
