package com.project.shared_card.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.model.SignUp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;

public class UserGroupRegistrationActivity extends AppCompatActivity {
    ImplDB db;
    ByteArrayOutputStream blob;
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
        if (editText.getText().toString().equals("")){
            return;
        }
        SignUp user = new SignUp(editText.getText().toString(), "");
        db.getUsersRepository().createUser(user);
        SignUp group = new SignUp("default", "res/drawable-v24/grocery_card.png");
        db.getGroupsRepository().createGroups(group);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String FilePath = data.getDataString();
            image.setImageURI(Uri.parse(FilePath));
            Uri uri =  Uri.parse(FilePath);

            File source = new File(FilePath);
            FileOutputStream fos = null;
            try {
                fos = openFileOutput("me", MODE_PRIVATE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fos.write(Files.readAllBytes(Paths.get(FilePath)));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


