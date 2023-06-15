package com.project.shared_card.activity.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.MainActivity;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;
import com.project.shared_card.database.entity.currency.CurrencyEntity;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.metrics.MetricsEntity;
import com.project.shared_card.database.entity.shop.product.ShopProductEntity;
import com.project.shared_card.database.entity.shop.target.ShopTargetEntity;
import com.project.shared_card.database.entity.story.model.History;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private ImplDB db;
    private ImageView image;
    private Button button;
    private EditText editText;
    private SharedPreferences.Editor prefEditor;
    ActivityResultLauncher<String> getContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        button.setOnClickListener(this::clickOnButton);
        image.setOnClickListener(this::clickOnImage);
    }

    void init() {
        SharedPreferences settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        db = new ImplDB(this);
        image = findViewById(R.id.image_user_group);
        editText = findViewById(R.id.edit_text_your_name);
        button = findViewById(R.id.button_continue);

        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onActivityResult);
    }

    public void clickOnImage(View v) {
        getContent.launch("image/*");
    }

    public void clickOnButton(View v) {
        if (editText.getText().toString().equals(""))
            return;
        byte[] picture;
        if (image.getDrawable() == null) {
            picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
        }
        else{
            picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable().getCurrent()).getBitmap());
        }
        long idUser = Long.parseLong(getString(R.string.me_id));
        createUser(idUser,picture);
    }

    private void createUser(long idUser,byte[] picture) {
        prefEditor.putString(getString(R.string.key_for_me_name), editText.getText().toString()).apply();
        prefEditor.putString(getString(R.string.key_for_select_group_id), String.valueOf(idUser)).apply();
        db.user_name().createUser(new UserNameEntity(idUser, editText.getText().toString(), picture));
        db.group_name().createGroup(new GroupNameEntity(idUser, editText.getText().toString(),picture));
        db.group().createGroup(new GroupEntity(idUser, idUser, true));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void onActivityResult(Uri result) {
        image.setImageURI(result);
    }



}


