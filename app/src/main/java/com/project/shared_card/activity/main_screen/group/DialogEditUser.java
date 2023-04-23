package com.project.shared_card.activity.main_screen.group;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;

import com.project.shared_card.R;

public class DialogEditUser {
    Context context;
    Dialog dialog;
    EditText dialogNameUser;
    ImageView dialogImage;
    Button dialogReady;
    ActivityResultLauncher<String> getContent;
    public DialogEditUser(Context context, ActivityResultLauncher<String> getContent) {
        dialog = new Dialog(context);
        this.context = context;
        this.getContent = getContent;
        dialog.setContentView(R.layout.dialog_edit_profile);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogNameUser = dialog.findViewById(R.id.dialog_edit_name);
        dialogImage = dialog.findViewById(R.id.dialog_image);
        dialogImage.setOnClickListener(this::imageOnClick);
        dialogReady = dialog.findViewById(R.id.dialog_ready);
    }
    void imageOnClick(View v){
        getContent.launch("image/*");
    }

}
