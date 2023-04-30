package com.project.shared_card.activity.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.MainActivity;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.categories.CategoriesEntity;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.metrics.MetricsEntity;
import com.project.shared_card.database.entity.shop.ShopEntity;
import com.project.shared_card.model.SignUp;

import java.io.File;
import java.io.FileNotFoundException;
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
        createCategoryMetricShop();
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

        if (image.getDrawable() == null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
                    createDirectory(picture);
                }
            });
            thread.start();
        }

        long idUser = Long.parseLong(getString(R.string.me_id));
        createUser(idUser);
    }

    private void createUser(long idUser) {
        prefEditor.putString(getString(R.string.key_for_me_name), editText.getText().toString()).apply();
        prefEditor.putString(getString(R.string.key_for_select_group_id), String.valueOf(idUser)).apply();

        SignUp user = new SignUp(idUser, editText.getText().toString());
        SignUp group = new SignUp(idUser, editText.getText().toString());

        db.getUserNameRepository().createUser(user);
        db.getGroupNameRepository().createGroups(group);
        db.getGroupRepository().createRepository(new GroupEntity(idUser, idUser, true));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void onActivityResult(Uri result) {
        try {
            image.setImageURI(result);
            InputStream inputStream = getContentResolver().openInputStream(result);
            byte[] picture = new byte[inputStream.available()];
            inputStream.read(picture);
            //picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable().getCurrent()).getBitmap());
            createDirectory(picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectory(byte[] picture) {
        FileOutputStream fosUser;
        FileOutputStream fosGroup;
        File fileUser = new File(getFilesDir() + "/user");
        File fileGroup = new File(getFilesDir() + "/group");
        fileUser.mkdir();
        fileGroup.mkdir();
        try {
            fosUser = new FileOutputStream(getFilesDir() + "/user/-1");
            fosGroup = new FileOutputStream(getFilesDir() + "/group" + "/-1");
            fosUser.write(picture);
            fosGroup.write(picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCategoryMetricShop() {
        List<MetricsEntity> metrics = Arrays.asList(
                new MetricsEntity(1, "шт"),
                new MetricsEntity(2, "кг"),
                new MetricsEntity(3, "л"),
                new MetricsEntity(4, "мл"));
        List<ShopEntity> shops = Arrays.asList(
                new ShopEntity(1, "продуктовый", true),
                new ShopEntity(2, "рынок", true),
                new ShopEntity(3, "Пятерочка", true),
                new ShopEntity(4, "Магнит", true),
                new ShopEntity(5, "Перекресток", true),
                new ShopEntity(6, "Вкусвилл", true),
                new ShopEntity(7, "продуктовый", false),
                new ShopEntity(8, "онлайн", false),
                new ShopEntity(9, "продукты", false),
                new ShopEntity(10, "электротехника", false),
                new ShopEntity(11, "автосалон", false),
                new ShopEntity(12, "хобби-гипермаркет", false));
        List<CategoriesEntity> categories = Arrays.asList(
                new CategoriesEntity(1, "мясо", true),
                new CategoriesEntity(2, "рыба", true),
                new CategoriesEntity(3, "молочные продукты", true),
                new CategoriesEntity(4, "алкоголь", true),
                new CategoriesEntity(5, "продукты", false),
                new CategoriesEntity(6, "авто", false),
                new CategoriesEntity(7, "искусство", false),
                new CategoriesEntity(8, "электротовары", false));

        db.getMetricsRepository().addMetrics(metrics);
        db.getShopRepository().addShop(shops);
        db.getCategoriesRepository().add(categories);
    }
}


