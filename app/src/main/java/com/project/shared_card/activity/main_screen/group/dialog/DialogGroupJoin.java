package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;

import com.project.shared_card.R;

public class DialogGroupJoin {
    Context context;
    public Dialog dialog;
    public EditText name;
    public Button ready;
    public DialogGroupJoin(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_join_group);
        this.context = context;
        name = dialog.findViewById(R.id.dialog_edit_name);
        ready = dialog.findViewById(R.id.dialog_join);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

}
