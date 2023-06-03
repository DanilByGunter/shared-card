package com.project.shared_card.activity.main_screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;


public class MainActivity extends AppCompatActivity {
    SharedPreferences settings;
    BottomNavigationView navigationView;
    TextView nameGroup;
    ImageView imageGroup;
    Long idGroup;
    NavController navController;
    ImplDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        NavigationUI.setupWithNavController(navigationView,navController);

        db.group_name().getGroupById(idGroup).observe(this, new Observer<GroupNameEntity>() {
            @Override
            public void onChanged(GroupNameEntity entity) {
                nameGroup.setText(entity.getName());
                imageGroup.setImageBitmap(DbBitmapUtility.getImage(entity.getPhoto()));
            }
        });

    }


    void init(){
        navController =((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).getNavController();
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        nameGroup = findViewById(R.id.main_name_group);
        imageGroup = findViewById(R.id.main_image_group);
        idGroup = Long.valueOf(settings.getString(getString(R.string.key_for_select_group_id), "XD"));
        navigationView = findViewById(R.id.bottom_navigation);
        db = new ImplDB(this);
    }


}