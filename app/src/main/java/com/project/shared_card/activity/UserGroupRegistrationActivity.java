package com.project.shared_card.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.model.SignUp;

public class UserGroupRegistrationActivity extends AppCompatActivity {
    ImplDB db;

    ImageView image;
    Button button;
    TextView textView;
    EditText editText;
    int numberOfClick=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group_registration);
        db = new ImplDB(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Bundle arguments = getIntent().getExtras();
        image = findViewById(R.id.image_user_group);
        textView = findViewById(R.id.text_choice_of_avatar);
        editText = findViewById(R.id.edit_text_your_name);
        button = findViewById(R.id.button_continue);
        button.setOnClickListener(this::onClick);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityIfNeeded(photoPickerIntent, 1);
            }
        });
    }
    public void onClick(View v) {
        switch (numberOfClick){
            case 0:
                if (editText.getText().toString().equals("")){
                    return;
                }
                SignUp user = new SignUp(editText.getText().toString(),image.getDrawingCache().toString());
                db.getUsersRepository().createUser(user);
                textView.setText(getString(R.string.choose_an_avatar_for_group));
                editText.setHint(getString(R.string.enter_your_group));
                editText.setText("");
                image.setImageDrawable(null);
                numberOfClick+=1;
                break;
            case 1:
                if (editText.getText().toString().equals("")){
                    return;
                }
                SignUp group = new SignUp(editText.getText().toString(),image.getDrawingCache().toString());
                db.getGroupsRepository().createGroups(group);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String FilePath = data.getDataString();
            System.out.println(FilePath);
            image.setImageURI(Uri.parse(FilePath));
        }
    }
}


