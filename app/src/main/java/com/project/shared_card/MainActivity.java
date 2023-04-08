package com.project.shared_card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    StoryFragment storyFragment = new StoryFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    GroupFragment groupFragment = new GroupFragment();
    CheckFragment checkFragment = new CheckFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.show_fragment, checkFragment)
                .commit();
        navigationView.setSelectedItemId(R.id.menu_check);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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
        });
    }
}