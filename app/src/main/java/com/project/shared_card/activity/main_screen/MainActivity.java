package com.project.shared_card.activity.main_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.activity.main_screen.group.GroupFragment;
import com.project.shared_card.activity.main_screen.statistic.StatisticsFragment;
import com.project.shared_card.activity.main_screen.story.StoryFragment;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    SharedPreferences settings;
    BottomNavigationView navigationView;
    TextView nameGroup;
    ImageView imageGroup;
    private StoryFragment storyFragment = new StoryFragment();
    private StatisticsFragment statisticsFragment = new StatisticsFragment();
    private GroupFragment groupFragment;
    private CheckFragment checkFragment = new CheckFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        nameGroup = findViewById(R.id.main_name_group);
        imageGroup = findViewById(R.id.main_image_group);
        String idGroup = settings.getString(getString(R.string.key_for_select_group_id),"XD");
        String name = idGroup.split("#")[1];
        String id = idGroup.split("#")[0];
        nameGroup.setText(name);
        String groupPath =getFilesDir() + "/group/"+ id+ ".png";
        imageGroup.setImageURI(Uri.parse(groupPath));

        groupFragment = new GroupFragment(findViewById(R.id.toolbar));

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