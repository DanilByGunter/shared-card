package com.project.shared_card.activity.main_screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.activity.main_screen.group.GroupFragment;
import com.project.shared_card.activity.main_screen.statistic.StatisticsFragment;
import com.project.shared_card.activity.main_screen.story.StoryFragment;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;


public class MainActivity extends AppCompatActivity {
    SharedPreferences settings;
    BottomNavigationView navigationView;
    TextView nameGroup;
    ImageView imageGroup;
    Long idGroup;
    private StoryFragment storyFragment = new StoryFragment();
    private StatisticsFragment statisticsFragment = new StatisticsFragment();
    private GroupFragment groupFragment;
    private CheckFragment checkFragment = new CheckFragment();
    private String groupPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        groupFragment = new GroupFragment(findViewById(R.id.toolbar));
        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.show_fragment, checkFragment)
                .commit();

        navigationView.setSelectedItemId(R.id.menu_check);
        navigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImplDB db = new ImplDB(this);
        db.group_name().getGroupById(idGroup).observe(this, new Observer<GroupNameEntity>() {
            @Override
            public void onChanged(GroupNameEntity entity) {
                nameGroup.setText(entity.getName());
                imageGroup.setImageURI(Uri.parse(groupPath));
            }
        });
    }

    void init(){
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        nameGroup = findViewById(R.id.main_name_group);
        imageGroup = findViewById(R.id.main_image_group);
        idGroup = Long.valueOf(settings.getString(getString(R.string.key_for_select_group_id), "XD"));
        groupPath = getFilesDir() + "/group/" + idGroup;
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_group:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, groupFragment)
                        .commit();
                return true;
            case R.id.menu_check:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, checkFragment)
                        .commit();
                return true;
            case R.id.menu_statistic:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, statisticsFragment)
                        .commit();
                return true;
            case R.id.manu_story:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, storyFragment)
                        .commit();
                return true;
        }
        return false;
    }
}