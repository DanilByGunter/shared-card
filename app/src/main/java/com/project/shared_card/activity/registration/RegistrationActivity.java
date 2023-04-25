package com.project.shared_card.activity.registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.MainActivity;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.categories.CategoriesEntity;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.metrics.MetricsDao;
import com.project.shared_card.database.entity.metrics.MetricsEntity;
import com.project.shared_card.database.entity.shop.ShopEntity;
import com.project.shared_card.model.SignUp;
import com.project.shared_card.retrofit.RetrofitService;
import com.project.shared_card.retrofit.api.UserApi;
import com.project.shared_card.retrofit.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private ImplDB db;
    private ImageView image;
    private Button button;
    private EditText editText;
    private SignUp user;
    private SignUp group;
    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;
    private byte[] picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        db = new ImplDB(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        image = findViewById(R.id.image_user_group);
        editText = findViewById(R.id.edit_text_your_name);
        button = findViewById(R.id.button_continue);
        button.setOnClickListener(this::onClick);
        createCategoryMetricShop();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityIfNeeded(photoPickerIntent,1);
            }
        });
    }
    public void onClick(View v)  {
        if (editText.getText().toString().equals("")){
            return;
        }
//##############################Сохранение id группы, id пользователя и имени пользователя#########################
        //TODO server
        if (image.getDrawable() == null) {
            picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
        }
        else{
            picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable().getCurrent()).getBitmap());
        }
        long idUser= -1;

        User user = new User(editText.getText().toString(),picture);
        RetrofitService retrofit = new RetrofitService();
        UserApi userApi = retrofit.getRetrofit().create(UserApi.class);
        userApi.addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                createUser(response.body().getId_user());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                createUser(-1);
            }
        });
    }
    private void createUser(long idUser){
        prefEditor.putString(getString(R.string.key_for_user_id), String.valueOf(idUser)).apply();
        prefEditor.putString(getString(R.string.key_for_user_name), editText.getText().toString()).apply();
        String identifierGroup ="-1#" + editText.getText().toString();
        prefEditor.putString(getString(R.string.key_for_select_group_id),identifierGroup).apply();
//####################Сохранение фотки###############################

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fosUser;
                FileOutputStream fosGroup;
                File fileUser = new File(getFilesDir()+"/user");
                File fileGroup = new File(getFilesDir()+"/group");
                fileUser.mkdir();
                fileGroup.mkdir();

                try {
                    fosUser = new FileOutputStream(getFilesDir()+"/user/"+idUser +".png");
                    fosGroup = new FileOutputStream(getFilesDir()+"/group"+"/-1.png");
                    fosUser.write(picture);
                    fosGroup.write(picture);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

//############################добавление в БД#########################

        user = new SignUp(idUser, editText.getText().toString());
        db.getUserNameRepository().createUser(user);
        long idGroup = -1;
        group = new SignUp(idGroup, editText.getText().toString());
        db.getGroupNameRepository().createGroups(group);
        db.getGroupRepository().createRepository(new GroupEntity(idUser,idGroup, true));
//############################запуск активити#########################
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String FilePath = data.getDataString();
            image.setImageURI(Uri.parse(FilePath));
        }
    }
    private void createCategoryMetricShop(){
        List<MetricsEntity> metrics = Arrays.asList(
                new MetricsEntity(1,"шт"),
                new MetricsEntity(2,"кг"),
                new MetricsEntity(3,"л"),
                new MetricsEntity(4, "мл"));
        List<ShopEntity> shops = Arrays.asList(
                new ShopEntity(1,"продуктовый",true),
                new ShopEntity(2,"рынок", true),
                new ShopEntity(3,"Пятерочка", true),
                new ShopEntity(4,"Магнит",true),
                new ShopEntity(5,"Перекресток", true),
                new ShopEntity(6,"Вкусвилл", true),
                new ShopEntity(7,"продуктовый",false),
                new ShopEntity(8,"онлайн", false),
                new ShopEntity(9,"продукты", false),
                new ShopEntity(10,"электротехника",false),
                new ShopEntity(11,"автосалон", false),
                new ShopEntity(12,"хобби-гипермаркет", false));
        List<CategoriesEntity> categories = Arrays.asList(
                new CategoriesEntity(1,"мясо",true),
                new CategoriesEntity(2,"рыба",true),
                new CategoriesEntity(3,"молочные продукты", true),
                new CategoriesEntity(4, "алкоголь",true),
                new CategoriesEntity(5,"продукты",false),
                new CategoriesEntity(6,"авто",false),
                new CategoriesEntity(7,"искусство", false),
                new CategoriesEntity(8, "электротовары",false));

        db.getMetricsRepository().addMetrics(metrics);
        db.getShopRepository().addShop(shops);
        db.getCategoriesRepository().add(categories);
    }
}


