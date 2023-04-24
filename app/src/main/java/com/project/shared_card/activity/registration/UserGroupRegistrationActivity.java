package com.project.shared_card.activity.registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group_registration);
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
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
            prefEditor = settings.edit();
            //TODO server
            //Запрос на сервер возврат последнего id пользователя. если интернета нет, то id = -1
            long idUser = -1;
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
            byte[] picture;
            if (image.getDrawable() == null) {
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) getDrawable(R.drawable.defaul_avatar).getCurrent()).getBitmap());
            }
            else{
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getDrawable().getCurrent()).getBitmap());
            }
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


            group = new SignUp(idUser, editText.getText().toString());
            db.getGroupNameRepository().createGroups(group);
            db.getGroupRepository().createRepository(new GroupEntity(idUser, idUser, true));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String FilePath = data.getDataString();
            image.setImageURI(Uri.parse(FilePath));
        }
    }
}


