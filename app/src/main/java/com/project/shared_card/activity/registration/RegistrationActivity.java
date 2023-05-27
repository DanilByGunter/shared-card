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

        if (image.getDrawable() == null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
                    ModelConverter.savePhoto(String.valueOf(getFilesDir()),picture,-1,true);
                    ModelConverter.savePhoto(String.valueOf(getFilesDir()),picture,-1,false);
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
            ModelConverter.savePhoto(String.valueOf(getFilesDir()),picture,-1,true);
            ModelConverter.savePhoto(String.valueOf(getFilesDir()),picture,-1,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void createCategoryMetricShop() {
        List<MetricsEntity> metrics = Arrays.asList(
                new MetricsEntity("шт"),
                new MetricsEntity("кг"),
                new MetricsEntity("г"),
                new MetricsEntity("л"),
                new MetricsEntity("мл"));
        List<CurrencyEntity> currencies = Arrays.asList(
                new CurrencyEntity("₽"),
                new CurrencyEntity("$"),
                new CurrencyEntity("€"),
                new CurrencyEntity("₤"),
                new CurrencyEntity("₴"),
                new CurrencyEntity("₸"));
        List<ShopProductEntity> shopProduct = Arrays.asList(
                new ShopProductEntity("Аптека"),
                new ShopProductEntity("Азбука вкуса"),
                new ShopProductEntity("Ашан"),
                new ShopProductEntity("Бристоль"),
                new ShopProductEntity("Виктория"),
                new ShopProductEntity("Вкусвилл"),
                new ShopProductEntity("Красное и белое"),
                new ShopProductEntity("Лента"),
                new ShopProductEntity("Магнит"),
                new ShopProductEntity("Окей"),
                new ShopProductEntity("Перекрёсток"),
                new ShopProductEntity("Пятерочка"),
                new ShopProductEntity("Продуктовый"),
                new ShopProductEntity("Рынок"),
                new ShopProductEntity("Самокат"),
                new ShopProductEntity("Спортпит"),
                new ShopProductEntity("Фикспрайс"),
                new ShopProductEntity("Другое")
        );
        List<ShopTargetEntity> shopTarget = Arrays.asList(
                new ShopTargetEntity("Автотовары"),
                new ShopTargetEntity("Автоцентр"),
                new ShopTargetEntity("Гипермаркет"),
                new ShopTargetEntity("Детские товары"),
                new ShopTargetEntity("Зоомагазин"),
                new ShopTargetEntity("Интернет-магазин"),
                new ShopTargetEntity("Книжный магазин"),
                new ShopTargetEntity("Магазин канцтоваров"),
                new ShopTargetEntity("Магазин одежды"),
                new ShopTargetEntity("Мебельный"),
                new ShopTargetEntity("Музыкальный магазин"),
                new ShopTargetEntity("Онлайн площадка"),
                new ShopTargetEntity("Продовольственный"),
                new ShopTargetEntity("Рынок"),
                new ShopTargetEntity("Сексшоп"),
                new ShopTargetEntity("Спецмагазин"),
                new ShopTargetEntity("Строительные товары"),
                new ShopTargetEntity("Супермаркет"),
                new ShopTargetEntity("Художественный магазин"),
                new ShopTargetEntity("Цветочный магазин"),
                new ShopTargetEntity("Церковный магазин"),
                new ShopTargetEntity("Хобби-гипермаркет"),
                new ShopTargetEntity("Электротовары"));
        List<CategoriesProductEntity> categoriesProduct = Arrays.asList(
                new CategoriesProductEntity("Алкоголь"),
                new CategoriesProductEntity("Бытовые товары"),
                new CategoriesProductEntity("Готовая еда"),
                new CategoriesProductEntity("Грибы"),
                new CategoriesProductEntity("Зелень"),
                new CategoriesProductEntity("Крупы"),
                new CategoriesProductEntity("Лекарство"),
                new CategoriesProductEntity("Масло"),
                new CategoriesProductEntity("Молочные продукты"),
                new CategoriesProductEntity("Морепродукты"),
                new CategoriesProductEntity("Мясо"),
                new CategoriesProductEntity("Напитки"),
                new CategoriesProductEntity("Овощи"),
                new CategoriesProductEntity("Орехи"),
                new CategoriesProductEntity("Полуфабрикаты"),
                new CategoriesProductEntity("Рыба"),
                new CategoriesProductEntity("Сладости"),
                new CategoriesProductEntity("Снеки"),
                new CategoriesProductEntity("Фрукты"),
                new CategoriesProductEntity("Химия"),
                new CategoriesProductEntity("Хлебобулочные изделия"),
                new CategoriesProductEntity("Яичные продукты"),
                new CategoriesProductEntity("Другое")
                );
        List<CategoriesTargetEntity> categoriesTarget = Arrays.asList(
                new CategoriesTargetEntity("Быт"),
                new CategoriesTargetEntity("Дом"),
                new CategoriesTargetEntity("Досуг"),
                new CategoriesTargetEntity("Здоровье"),
                new CategoriesTargetEntity("Мебель"),
                new CategoriesTargetEntity("Одежда"),
                new CategoriesTargetEntity("Подарок"),
                new CategoriesTargetEntity("Продукты"),
                new CategoriesTargetEntity("Путешествие"),
                new CategoriesTargetEntity("Спорт"),
                new CategoriesTargetEntity("Транспорт"),
                new CategoriesTargetEntity("Творчество"),
                new CategoriesTargetEntity("Электроника"),
                new CategoriesTargetEntity("Другое"));

        db.metric().addMetrics(metrics);
        db.shop_product().add(shopProduct);
        db.shop_target().add(shopTarget);
        db.currency().add(currencies);
        db.category_target().add(categoriesTarget);
        db.category_product().add(categoriesProduct);
    }
}


