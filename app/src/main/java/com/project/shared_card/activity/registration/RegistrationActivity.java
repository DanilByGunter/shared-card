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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
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
        createCategoryMetricShop();
        long idUser = Long.parseLong(getString(R.string.me_id));
        createUser(idUser);
    }

    private void createUser(long idUser) {
        prefEditor.putString(getString(R.string.key_for_me_name), editText.getText().toString()).apply();
        prefEditor.putString(getString(R.string.key_for_select_group_id), String.valueOf(idUser)).apply();

        db.user_name().createUser(new UserNameEntity(idUser, editText.getText().toString()));
        db.group_name().createGroup(new GroupNameEntity(idUser, editText.getText().toString()));
        db.group().createGroup(new GroupEntity(idUser, idUser, true));

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
                new MetricsEntity("шт"),
                new MetricsEntity("кг"),
                new MetricsEntity("л"),
                new MetricsEntity("мл"));
        List<CurrencyEntity> currencies = Arrays.asList(
                new CurrencyEntity("руб"),
                new CurrencyEntity("$"),
                new CurrencyEntity("евро")
        );
        List<ShopProductEntity> shopProduct = Arrays.asList(
                new ShopProductEntity("продуктовый"),
                new ShopProductEntity("рынок"),
                new ShopProductEntity("Пятерочка"),
                new ShopProductEntity("Магнит"),
                new ShopProductEntity("Перекресток"),
                new ShopProductEntity("Вкусвилл"));
        List<ShopTargetEntity> shopTarget = Arrays.asList(
                new ShopTargetEntity("продуктовый"),
                new ShopTargetEntity("онлайн"),
                new ShopTargetEntity("продукты"),
                new ShopTargetEntity("электротехника"),
                new ShopTargetEntity("автосалон"),
                new ShopTargetEntity("хобби-гипермаркет"));
        List<CategoriesProductEntity> categoriesProduct = Arrays.asList(
                new CategoriesProductEntity("мясо"),
                new CategoriesProductEntity("рыба"),
                new CategoriesProductEntity("молочные продукты"),
                new CategoriesProductEntity("алкоголь"));
        List<CategoriesTargetEntity> categoriesTarget = Arrays.asList(
                new CategoriesTargetEntity("продукты"),
                new CategoriesTargetEntity("авто"),
                new CategoriesTargetEntity("искусство"),
                new CategoriesTargetEntity("электротовары"));

        db.metric().addMetrics(metrics);
        db.shop_product().add(shopProduct);
        db.shop_target().add(shopTarget);
        db.currency().add(currencies);
        db.category_target().add(categoriesTarget);
        db.category_product().add(categoriesProduct);
    }
}


