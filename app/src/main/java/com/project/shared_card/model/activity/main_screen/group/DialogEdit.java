package com.project.shared_card.model.activity.main_screen.group;

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
    Dialog dialog;
    EditText name;
    ImageView image;
    Button ready;
    ActivityResultLauncher<String> getContent;
    public DialogEdit(Context context, ActivityResultLauncher<String> getContent) {
        dialog = new Dialog(context);
        this.context = context;
        this.getContent = getContent;
        dialog.setContentView(R.layout.dialog_edit_profile);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        name = dialog.findViewById(R.id.dialog_edit_name);
        image = dialog.findViewById(R.id.dialog_image);
        image.setOnClickListener(this::imageOnClick);
        ready = dialog.findViewById(R.id.dialog_ready);
    }
    void imageOnClick(View v){
        getContent.launch("image/*");
    }

}
