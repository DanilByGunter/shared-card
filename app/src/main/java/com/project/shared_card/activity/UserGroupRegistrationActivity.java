package com.project.shared_card.activity;


import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
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
        if(image.getDrawable() == null){
            byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable)getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
            FileOutputStream fos;
            try {
                fos= openFileOutput("me.png", MODE_PRIVATE);
                fos.write(picture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        SignUp user = new SignUp(editText.getText().toString(), "me.png");
        db.getUsersRepository().createUser(user);
        SignUp group = new SignUp("", "res/drawable-v24/grocery_card.png");
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
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable()).getBitmap());
                    FileOutputStream fos;
                    try {
                        fos= openFileOutput("me.png", MODE_PRIVATE);
                        fos.write(picture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        }
    }

}


