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
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.model.SignUp;
import com.project.shared_card.retrofit.RetrofitService;
import com.project.shared_card.retrofit.api.UserApi;
import com.project.shared_card.retrofit.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserGroupRegistrationActivity extends AppCompatActivity {

    public static final String TEXT_VIEW_KEY = "1";
    public static final String EDIT_VIEW_KEY = "2";
    public static final String  SCREEN_REGISTRATION= "3";
    private ImplDB db;
    private ImageView image;
    private Button button;
    private TextView textView;
    private EditText editText;
    private Boolean ruleRegistration;
    private SignUp user;
    private SignUp group;
    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;
    private byte[] picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group_registration);
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        Bundle arguments = getIntent().getExtras();
        String textForTextView = arguments.get(TEXT_VIEW_KEY).toString();
        String textForEditView = arguments.get(EDIT_VIEW_KEY).toString();
        ruleRegistration = arguments.getBoolean(SCREEN_REGISTRATION);
        db = new ImplDB(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        image = findViewById(R.id.image_user_group);
        textView = findViewById(R.id.text_choice_of_avatar);
        textView.setText(textForTextView);
        editText = findViewById(R.id.edit_text_your_name);
        editText.setHint(textForEditView);
        button = findViewById(R.id.button_continue);
        button.setOnClickListener(this::onClick);
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
        if(ruleRegistration) {
//##############################Сохранение id группы, id пользователя и имени пользователя#########################
            //TODO server
            if (image.getDrawable() == null) {
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
            }
            else{
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable().getCurrent()).getBitmap());
            }
            long idUser= -1;

            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = cm.getActiveNetworkInfo();
            if (wifiInfo != null && wifiInfo.isConnectedOrConnecting())
            {
                User user = new User(editText.getText().toString(),picture);
                RetrofitService retrofit = new RetrofitService();
                UserApi userApi = retrofit.getRetrofit().create(UserApi.class);
//                userApi.addUser(user).enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        System.out.println("ds");
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        System.out.println("ds");
//                    }
//                });
                userApi.getLastUserId().enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        System.out.println("");
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        System.out.println("");
                    }
                });
            }
            else{
                createUser(-1);
            }

        }
        else{
//##############################Сохранение id группы#########################
            //Запрос на сервер возврат последнего id группы
            //TODO server
            long idGroup = 1;
            //Сохраняем идентификатор в SharedPreference в виде (название#id)
            String identifierGroup = settings.getString(getString(R.string.key_for_select_group_id),"XD");


//####################Сохранение фотки###############################
            byte[] picture;
            if (image.getDrawable() == null) {
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.grocery_card).getCurrent()).getBitmap());
            }
            else{
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable().getCurrent()).getBitmap());
            }
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(getFilesDir()+"/group"+String.format("{0}.png",identifierGroup));
                fos.write(picture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//############################добавление в БД#########################
            String groupPath = getFilesDir()+"/group"+String.format("{0}.png",identifierGroup);
            SignUp group = new SignUp(idGroup, editText.getText().toString());
            db.getGroupNameRepository().createGroups(group);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void createUser(long idUser){
        prefEditor.putString(getString(R.string.key_for_user_id), String.valueOf(idUser)).apply();
        prefEditor.putString(getString(R.string.key_for_user_name), editText.getText().toString()).apply();
        String identifierGroup ="-1#" + editText.getText().toString();
        prefEditor.putString(getString(R.string.key_for_select_group_id),identifierGroup).apply();
//####################Сохранение фотки###############################
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
//############################добавление в БД#########################

        user = new SignUp(idUser, editText.getText().toString());
        db.getUserNameRepository().createUser(user);
        long idGroup = -1;
        group = new SignUp(idGroup, editText.getText().toString());
        db.getGroupNameRepository().createGroups(group);
        db.getGroupRepository().createRepository(new GroupEntity(idUser,idGroup, true));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String FilePath = data.getDataString();
            image.setImageURI(Uri.parse(FilePath));
        }
    }
}


