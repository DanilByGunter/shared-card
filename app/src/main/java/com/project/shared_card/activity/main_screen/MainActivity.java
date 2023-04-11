package com.project.shared_card.activity.main_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.activity.main_screen.group.GroupFragment;
import com.project.shared_card.activity.main_screen.statistic.StatisticsFragment;
import com.project.shared_card.activity.main_screen.story.StoryFragment;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private StoryFragment storyFragment = new StoryFragment();
    private StatisticsFragment statisticsFragment = new StatisticsFragment();
    private GroupFragment groupFragment = new GroupFragment();
    private CheckFragment checkFragment = new CheckFragment();
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