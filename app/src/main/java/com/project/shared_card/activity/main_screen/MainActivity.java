package com.project.shared_card.activity.main_screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;

import java.util.List;


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

    public void settingForAnimationOfCheck(){
        List<Fragment> fragments =getSupportFragmentManager().getFragments();
        CheckFragment check = (CheckFragment) getSupportFragmentManager().getFragments();
        FloatingActionButton buttonAddProduct = check.buttonAddProduct;
        RecyclerView recyclerView = (RecyclerView) check.viewPager.getChildAt(0);
        View viewProduct = recyclerView.getChildAt(0);
        View viewTarget = recyclerView.getChildAt(1);
        RecyclerView listProduct = viewProduct.findViewById(R.id.list_product);
        RecyclerView listTarget = viewTarget.findViewById(R.id.list_target);
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    Animation.animationDownOfNavigationView(navigationView);
                    Animation.animationDownOfButton(buttonAddProduct);
                }
                if(!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1) ){
                    Animation.animationUpOfNavigationView(navigationView,500);
                    Animation.animationUpOfButton(buttonAddProduct,500);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    Animation.animationUpOfNavigationView(navigationView,500);
                    Animation.animationUpOfButton(buttonAddProduct,500);
                }
            }
        };
        listProduct.addOnScrollListener(scrollListener);
        listTarget.addOnScrollListener(scrollListener);
    }

}